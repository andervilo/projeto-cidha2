import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProcesso, defaultValue } from 'app/shared/model/processo.model';

export const ACTION_TYPES = {
  FETCH_PROCESSO_LIST: 'processo/FETCH_PROCESSO_LIST',
  FETCH_PROCESSO: 'processo/FETCH_PROCESSO',
  CREATE_PROCESSO: 'processo/CREATE_PROCESSO',
  UPDATE_PROCESSO: 'processo/UPDATE_PROCESSO',
  DELETE_PROCESSO: 'processo/DELETE_PROCESSO',
  SET_BLOB: 'processo/SET_BLOB',
  RESET: 'processo/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProcesso>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ProcessoState = Readonly<typeof initialState>;

// Reducer

export default (state: ProcessoState = initialState, action): ProcessoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROCESSO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROCESSO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PROCESSO):
    case REQUEST(ACTION_TYPES.UPDATE_PROCESSO):
    case REQUEST(ACTION_TYPES.DELETE_PROCESSO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PROCESSO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROCESSO):
    case FAILURE(ACTION_TYPES.CREATE_PROCESSO):
    case FAILURE(ACTION_TYPES.UPDATE_PROCESSO):
    case FAILURE(ACTION_TYPES.DELETE_PROCESSO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROCESSO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROCESSO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROCESSO):
    case SUCCESS(ACTION_TYPES.UPDATE_PROCESSO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROCESSO):
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

const apiUrl = 'api/processos';

// Actions

export const getEntities: ICrudGetAllAction<IProcesso> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PROCESSO_LIST,
    payload: axios.get<IProcesso>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IProcesso> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROCESSO,
    payload: axios.get<IProcesso>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProcesso> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROCESSO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProcesso> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROCESSO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProcesso> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROCESSO,
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
