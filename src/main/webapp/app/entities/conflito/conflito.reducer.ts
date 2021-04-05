import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConflito, defaultValue } from 'app/shared/model/conflito.model';

export const ACTION_TYPES = {
  FETCH_CONFLITO_LIST: 'conflito/FETCH_CONFLITO_LIST',
  FETCH_CONFLITO: 'conflito/FETCH_CONFLITO',
  CREATE_CONFLITO: 'conflito/CREATE_CONFLITO',
  UPDATE_CONFLITO: 'conflito/UPDATE_CONFLITO',
  DELETE_CONFLITO: 'conflito/DELETE_CONFLITO',
  SET_BLOB: 'conflito/SET_BLOB',
  RESET: 'conflito/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConflito>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ConflitoState = Readonly<typeof initialState>;

// Reducer

export default (state: ConflitoState = initialState, action): ConflitoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONFLITO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONFLITO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CONFLITO):
    case REQUEST(ACTION_TYPES.UPDATE_CONFLITO):
    case REQUEST(ACTION_TYPES.DELETE_CONFLITO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CONFLITO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONFLITO):
    case FAILURE(ACTION_TYPES.CREATE_CONFLITO):
    case FAILURE(ACTION_TYPES.UPDATE_CONFLITO):
    case FAILURE(ACTION_TYPES.DELETE_CONFLITO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONFLITO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONFLITO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONFLITO):
    case SUCCESS(ACTION_TYPES.UPDATE_CONFLITO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONFLITO):
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

const apiUrl = 'api/conflitos';

// Actions

export const getEntities: ICrudGetAllAction<IConflito> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CONFLITO_LIST,
    payload: axios.get<IConflito>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IConflito> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONFLITO,
    payload: axios.get<IConflito>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IConflito> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONFLITO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConflito> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONFLITO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConflito> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONFLITO,
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
