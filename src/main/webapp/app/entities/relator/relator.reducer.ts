import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRelator, defaultValue } from 'app/shared/model/relator.model';

export const ACTION_TYPES = {
  FETCH_RELATOR_LIST: 'relator/FETCH_RELATOR_LIST',
  FETCH_RELATOR: 'relator/FETCH_RELATOR',
  CREATE_RELATOR: 'relator/CREATE_RELATOR',
  UPDATE_RELATOR: 'relator/UPDATE_RELATOR',
  PARTIAL_UPDATE_RELATOR: 'relator/PARTIAL_UPDATE_RELATOR',
  DELETE_RELATOR: 'relator/DELETE_RELATOR',
  RESET: 'relator/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRelator>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type RelatorState = Readonly<typeof initialState>;

// Reducer

export default (state: RelatorState = initialState, action): RelatorState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RELATOR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RELATOR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RELATOR):
    case REQUEST(ACTION_TYPES.UPDATE_RELATOR):
    case REQUEST(ACTION_TYPES.DELETE_RELATOR):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_RELATOR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RELATOR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RELATOR):
    case FAILURE(ACTION_TYPES.CREATE_RELATOR):
    case FAILURE(ACTION_TYPES.UPDATE_RELATOR):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_RELATOR):
    case FAILURE(ACTION_TYPES.DELETE_RELATOR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RELATOR_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_RELATOR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RELATOR):
    case SUCCESS(ACTION_TYPES.UPDATE_RELATOR):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_RELATOR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RELATOR):
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

const apiUrl = 'api/relators';

// Actions

export const getEntities: ICrudGetAllAction<IRelator> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_RELATOR_LIST,
    payload: axios.get<IRelator>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IRelator> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RELATOR,
    payload: axios.get<IRelator>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IRelator> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RELATOR,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRelator> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RELATOR,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IRelator> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_RELATOR,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRelator> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RELATOR,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
