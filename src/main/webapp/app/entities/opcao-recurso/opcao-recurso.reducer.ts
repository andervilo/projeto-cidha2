import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOpcaoRecurso, defaultValue } from 'app/shared/model/opcao-recurso.model';

export const ACTION_TYPES = {
  FETCH_OPCAORECURSO_LIST: 'opcaoRecurso/FETCH_OPCAORECURSO_LIST',
  FETCH_OPCAORECURSO: 'opcaoRecurso/FETCH_OPCAORECURSO',
  CREATE_OPCAORECURSO: 'opcaoRecurso/CREATE_OPCAORECURSO',
  UPDATE_OPCAORECURSO: 'opcaoRecurso/UPDATE_OPCAORECURSO',
  DELETE_OPCAORECURSO: 'opcaoRecurso/DELETE_OPCAORECURSO',
  RESET: 'opcaoRecurso/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOpcaoRecurso>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type OpcaoRecursoState = Readonly<typeof initialState>;

// Reducer

export default (state: OpcaoRecursoState = initialState, action): OpcaoRecursoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_OPCAORECURSO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_OPCAORECURSO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_OPCAORECURSO):
    case REQUEST(ACTION_TYPES.UPDATE_OPCAORECURSO):
    case REQUEST(ACTION_TYPES.DELETE_OPCAORECURSO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_OPCAORECURSO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_OPCAORECURSO):
    case FAILURE(ACTION_TYPES.CREATE_OPCAORECURSO):
    case FAILURE(ACTION_TYPES.UPDATE_OPCAORECURSO):
    case FAILURE(ACTION_TYPES.DELETE_OPCAORECURSO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_OPCAORECURSO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_OPCAORECURSO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_OPCAORECURSO):
    case SUCCESS(ACTION_TYPES.UPDATE_OPCAORECURSO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_OPCAORECURSO):
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

const apiUrl = 'api/opcao-recursos';

// Actions

export const getEntities: ICrudGetAllAction<IOpcaoRecurso> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_OPCAORECURSO_LIST,
    payload: axios.get<IOpcaoRecurso>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IOpcaoRecurso> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_OPCAORECURSO,
    payload: axios.get<IOpcaoRecurso>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IOpcaoRecurso> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_OPCAORECURSO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOpcaoRecurso> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_OPCAORECURSO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOpcaoRecurso> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_OPCAORECURSO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
