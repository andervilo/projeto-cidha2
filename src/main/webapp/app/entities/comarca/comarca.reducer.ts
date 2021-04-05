import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IComarca, defaultValue } from 'app/shared/model/comarca.model';

export const ACTION_TYPES = {
  FETCH_COMARCA_LIST: 'comarca/FETCH_COMARCA_LIST',
  FETCH_COMARCA: 'comarca/FETCH_COMARCA',
  CREATE_COMARCA: 'comarca/CREATE_COMARCA',
  UPDATE_COMARCA: 'comarca/UPDATE_COMARCA',
  DELETE_COMARCA: 'comarca/DELETE_COMARCA',
  RESET: 'comarca/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IComarca>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ComarcaState = Readonly<typeof initialState>;

// Reducer

export default (state: ComarcaState = initialState, action): ComarcaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COMARCA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMARCA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COMARCA):
    case REQUEST(ACTION_TYPES.UPDATE_COMARCA):
    case REQUEST(ACTION_TYPES.DELETE_COMARCA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COMARCA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMARCA):
    case FAILURE(ACTION_TYPES.CREATE_COMARCA):
    case FAILURE(ACTION_TYPES.UPDATE_COMARCA):
    case FAILURE(ACTION_TYPES.DELETE_COMARCA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMARCA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMARCA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMARCA):
    case SUCCESS(ACTION_TYPES.UPDATE_COMARCA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMARCA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/comarcas';

// Actions

export const getEntities: ICrudGetAllAction<IComarca> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COMARCA_LIST,
    payload: axios.get<IComarca>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IComarca> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMARCA,
    payload: axios.get<IComarca>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IComarca> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMARCA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IComarca> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMARCA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IComarca> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMARCA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
