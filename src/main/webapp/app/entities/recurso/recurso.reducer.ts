import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRecurso, defaultValue } from 'app/shared/model/recurso.model';

export const ACTION_TYPES = {
  FETCH_RECURSO_LIST: 'recurso/FETCH_RECURSO_LIST',
  FETCH_RECURSO: 'recurso/FETCH_RECURSO',
  CREATE_RECURSO: 'recurso/CREATE_RECURSO',
  UPDATE_RECURSO: 'recurso/UPDATE_RECURSO',
  DELETE_RECURSO: 'recurso/DELETE_RECURSO',
  SET_BLOB: 'recurso/SET_BLOB',
  RESET: 'recurso/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRecurso>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type RecursoState = Readonly<typeof initialState>;

// Reducer

export default (state: RecursoState = initialState, action): RecursoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RECURSO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RECURSO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RECURSO):
    case REQUEST(ACTION_TYPES.UPDATE_RECURSO):
    case REQUEST(ACTION_TYPES.DELETE_RECURSO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RECURSO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RECURSO):
    case FAILURE(ACTION_TYPES.CREATE_RECURSO):
    case FAILURE(ACTION_TYPES.UPDATE_RECURSO):
    case FAILURE(ACTION_TYPES.DELETE_RECURSO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RECURSO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_RECURSO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RECURSO):
    case SUCCESS(ACTION_TYPES.UPDATE_RECURSO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RECURSO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/recursos';

// Actions

export const getEntities: ICrudGetAllAction<IRecurso> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_RECURSO_LIST,
    payload: axios.get<IRecurso>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IRecurso> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RECURSO,
    payload: axios.get<IRecurso>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IRecurso> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RECURSO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRecurso> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RECURSO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRecurso> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RECURSO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
