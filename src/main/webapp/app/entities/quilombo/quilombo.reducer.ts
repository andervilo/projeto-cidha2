import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IQuilombo, defaultValue } from 'app/shared/model/quilombo.model';

export const ACTION_TYPES = {
  FETCH_QUILOMBO_LIST: 'quilombo/FETCH_QUILOMBO_LIST',
  FETCH_QUILOMBO: 'quilombo/FETCH_QUILOMBO',
  CREATE_QUILOMBO: 'quilombo/CREATE_QUILOMBO',
  UPDATE_QUILOMBO: 'quilombo/UPDATE_QUILOMBO',
  DELETE_QUILOMBO: 'quilombo/DELETE_QUILOMBO',
  RESET: 'quilombo/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IQuilombo>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type QuilomboState = Readonly<typeof initialState>;

// Reducer

export default (state: QuilomboState = initialState, action): QuilomboState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_QUILOMBO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_QUILOMBO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_QUILOMBO):
    case REQUEST(ACTION_TYPES.UPDATE_QUILOMBO):
    case REQUEST(ACTION_TYPES.DELETE_QUILOMBO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_QUILOMBO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_QUILOMBO):
    case FAILURE(ACTION_TYPES.CREATE_QUILOMBO):
    case FAILURE(ACTION_TYPES.UPDATE_QUILOMBO):
    case FAILURE(ACTION_TYPES.DELETE_QUILOMBO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_QUILOMBO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_QUILOMBO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_QUILOMBO):
    case SUCCESS(ACTION_TYPES.UPDATE_QUILOMBO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_QUILOMBO):
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

const apiUrl = 'api/quilombos';

// Actions

export const getEntities: ICrudGetAllAction<IQuilombo> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_QUILOMBO_LIST,
    payload: axios.get<IQuilombo>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IQuilombo> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_QUILOMBO,
    payload: axios.get<IQuilombo>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IQuilombo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_QUILOMBO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IQuilombo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_QUILOMBO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IQuilombo> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_QUILOMBO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
