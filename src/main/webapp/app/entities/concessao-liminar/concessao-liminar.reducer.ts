import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConcessaoLiminar, defaultValue } from 'app/shared/model/concessao-liminar.model';

export const ACTION_TYPES = {
  FETCH_CONCESSAOLIMINAR_LIST: 'concessaoLiminar/FETCH_CONCESSAOLIMINAR_LIST',
  FETCH_CONCESSAOLIMINAR: 'concessaoLiminar/FETCH_CONCESSAOLIMINAR',
  CREATE_CONCESSAOLIMINAR: 'concessaoLiminar/CREATE_CONCESSAOLIMINAR',
  UPDATE_CONCESSAOLIMINAR: 'concessaoLiminar/UPDATE_CONCESSAOLIMINAR',
  DELETE_CONCESSAOLIMINAR: 'concessaoLiminar/DELETE_CONCESSAOLIMINAR',
  RESET: 'concessaoLiminar/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConcessaoLiminar>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ConcessaoLiminarState = Readonly<typeof initialState>;

// Reducer

export default (state: ConcessaoLiminarState = initialState, action): ConcessaoLiminarState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONCESSAOLIMINAR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONCESSAOLIMINAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CONCESSAOLIMINAR):
    case REQUEST(ACTION_TYPES.UPDATE_CONCESSAOLIMINAR):
    case REQUEST(ACTION_TYPES.DELETE_CONCESSAOLIMINAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CONCESSAOLIMINAR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONCESSAOLIMINAR):
    case FAILURE(ACTION_TYPES.CREATE_CONCESSAOLIMINAR):
    case FAILURE(ACTION_TYPES.UPDATE_CONCESSAOLIMINAR):
    case FAILURE(ACTION_TYPES.DELETE_CONCESSAOLIMINAR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONCESSAOLIMINAR_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONCESSAOLIMINAR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONCESSAOLIMINAR):
    case SUCCESS(ACTION_TYPES.UPDATE_CONCESSAOLIMINAR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONCESSAOLIMINAR):
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

const apiUrl = 'api/concessao-liminars';

// Actions

export const getEntities: ICrudGetAllAction<IConcessaoLiminar> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CONCESSAOLIMINAR_LIST,
    payload: axios.get<IConcessaoLiminar>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IConcessaoLiminar> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONCESSAOLIMINAR,
    payload: axios.get<IConcessaoLiminar>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IConcessaoLiminar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONCESSAOLIMINAR,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConcessaoLiminar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONCESSAOLIMINAR,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConcessaoLiminar> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONCESSAOLIMINAR,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
