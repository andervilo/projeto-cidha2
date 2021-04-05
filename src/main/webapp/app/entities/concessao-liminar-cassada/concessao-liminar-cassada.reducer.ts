import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConcessaoLiminarCassada, defaultValue } from 'app/shared/model/concessao-liminar-cassada.model';

export const ACTION_TYPES = {
  FETCH_CONCESSAOLIMINARCASSADA_LIST: 'concessaoLiminarCassada/FETCH_CONCESSAOLIMINARCASSADA_LIST',
  FETCH_CONCESSAOLIMINARCASSADA: 'concessaoLiminarCassada/FETCH_CONCESSAOLIMINARCASSADA',
  CREATE_CONCESSAOLIMINARCASSADA: 'concessaoLiminarCassada/CREATE_CONCESSAOLIMINARCASSADA',
  UPDATE_CONCESSAOLIMINARCASSADA: 'concessaoLiminarCassada/UPDATE_CONCESSAOLIMINARCASSADA',
  DELETE_CONCESSAOLIMINARCASSADA: 'concessaoLiminarCassada/DELETE_CONCESSAOLIMINARCASSADA',
  RESET: 'concessaoLiminarCassada/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConcessaoLiminarCassada>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ConcessaoLiminarCassadaState = Readonly<typeof initialState>;

// Reducer

export default (state: ConcessaoLiminarCassadaState = initialState, action): ConcessaoLiminarCassadaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONCESSAOLIMINARCASSADA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONCESSAOLIMINARCASSADA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CONCESSAOLIMINARCASSADA):
    case REQUEST(ACTION_TYPES.UPDATE_CONCESSAOLIMINARCASSADA):
    case REQUEST(ACTION_TYPES.DELETE_CONCESSAOLIMINARCASSADA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CONCESSAOLIMINARCASSADA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONCESSAOLIMINARCASSADA):
    case FAILURE(ACTION_TYPES.CREATE_CONCESSAOLIMINARCASSADA):
    case FAILURE(ACTION_TYPES.UPDATE_CONCESSAOLIMINARCASSADA):
    case FAILURE(ACTION_TYPES.DELETE_CONCESSAOLIMINARCASSADA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONCESSAOLIMINARCASSADA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONCESSAOLIMINARCASSADA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONCESSAOLIMINARCASSADA):
    case SUCCESS(ACTION_TYPES.UPDATE_CONCESSAOLIMINARCASSADA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONCESSAOLIMINARCASSADA):
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

const apiUrl = 'api/concessao-liminar-cassadas';

// Actions

export const getEntities: ICrudGetAllAction<IConcessaoLiminarCassada> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CONCESSAOLIMINARCASSADA_LIST,
    payload: axios.get<IConcessaoLiminarCassada>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IConcessaoLiminarCassada> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONCESSAOLIMINARCASSADA,
    payload: axios.get<IConcessaoLiminarCassada>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IConcessaoLiminarCassada> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONCESSAOLIMINARCASSADA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConcessaoLiminarCassada> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONCESSAOLIMINARCASSADA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConcessaoLiminarCassada> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONCESSAOLIMINARCASSADA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
