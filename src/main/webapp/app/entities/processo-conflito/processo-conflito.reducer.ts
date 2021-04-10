import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProcessoConflito, defaultValue } from 'app/shared/model/processo-conflito.model';

export const ACTION_TYPES = {
  FETCH_PROCESSOCONFLITO_LIST: 'processoConflito/FETCH_PROCESSOCONFLITO_LIST',
  FETCH_PROCESSOCONFLITO: 'processoConflito/FETCH_PROCESSOCONFLITO',
  CREATE_PROCESSOCONFLITO: 'processoConflito/CREATE_PROCESSOCONFLITO',
  UPDATE_PROCESSOCONFLITO: 'processoConflito/UPDATE_PROCESSOCONFLITO',
  PARTIAL_UPDATE_PROCESSOCONFLITO: 'processoConflito/PARTIAL_UPDATE_PROCESSOCONFLITO',
  DELETE_PROCESSOCONFLITO: 'processoConflito/DELETE_PROCESSOCONFLITO',
  SET_BLOB: 'processoConflito/SET_BLOB',
  RESET: 'processoConflito/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProcessoConflito>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ProcessoConflitoState = Readonly<typeof initialState>;

// Reducer

export default (state: ProcessoConflitoState = initialState, action): ProcessoConflitoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROCESSOCONFLITO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROCESSOCONFLITO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PROCESSOCONFLITO):
    case REQUEST(ACTION_TYPES.UPDATE_PROCESSOCONFLITO):
    case REQUEST(ACTION_TYPES.DELETE_PROCESSOCONFLITO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PROCESSOCONFLITO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PROCESSOCONFLITO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROCESSOCONFLITO):
    case FAILURE(ACTION_TYPES.CREATE_PROCESSOCONFLITO):
    case FAILURE(ACTION_TYPES.UPDATE_PROCESSOCONFLITO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PROCESSOCONFLITO):
    case FAILURE(ACTION_TYPES.DELETE_PROCESSOCONFLITO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROCESSOCONFLITO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROCESSOCONFLITO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROCESSOCONFLITO):
    case SUCCESS(ACTION_TYPES.UPDATE_PROCESSOCONFLITO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PROCESSOCONFLITO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROCESSOCONFLITO):
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

const apiUrl = 'api/processo-conflitos';

// Actions

export const getEntities: ICrudGetAllAction<IProcessoConflito> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PROCESSOCONFLITO_LIST,
    payload: axios.get<IProcessoConflito>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IProcessoConflito> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROCESSOCONFLITO,
    payload: axios.get<IProcessoConflito>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProcessoConflito> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROCESSOCONFLITO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProcessoConflito> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROCESSOCONFLITO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IProcessoConflito> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PROCESSOCONFLITO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProcessoConflito> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROCESSOCONFLITO,
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
