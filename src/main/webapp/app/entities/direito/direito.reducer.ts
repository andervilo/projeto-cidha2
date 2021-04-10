import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDireito, defaultValue } from 'app/shared/model/direito.model';

export const ACTION_TYPES = {
  FETCH_DIREITO_LIST: 'direito/FETCH_DIREITO_LIST',
  FETCH_DIREITO: 'direito/FETCH_DIREITO',
  CREATE_DIREITO: 'direito/CREATE_DIREITO',
  UPDATE_DIREITO: 'direito/UPDATE_DIREITO',
  PARTIAL_UPDATE_DIREITO: 'direito/PARTIAL_UPDATE_DIREITO',
  DELETE_DIREITO: 'direito/DELETE_DIREITO',
  SET_BLOB: 'direito/SET_BLOB',
  RESET: 'direito/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDireito>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type DireitoState = Readonly<typeof initialState>;

// Reducer

export default (state: DireitoState = initialState, action): DireitoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DIREITO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DIREITO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_DIREITO):
    case REQUEST(ACTION_TYPES.UPDATE_DIREITO):
    case REQUEST(ACTION_TYPES.DELETE_DIREITO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_DIREITO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_DIREITO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DIREITO):
    case FAILURE(ACTION_TYPES.CREATE_DIREITO):
    case FAILURE(ACTION_TYPES.UPDATE_DIREITO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_DIREITO):
    case FAILURE(ACTION_TYPES.DELETE_DIREITO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DIREITO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_DIREITO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_DIREITO):
    case SUCCESS(ACTION_TYPES.UPDATE_DIREITO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_DIREITO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_DIREITO):
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

const apiUrl = 'api/direitos';

// Actions

export const getEntities: ICrudGetAllAction<IDireito> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DIREITO_LIST,
    payload: axios.get<IDireito>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IDireito> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DIREITO,
    payload: axios.get<IDireito>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IDireito> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DIREITO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDireito> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DIREITO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IDireito> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_DIREITO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDireito> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DIREITO,
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
