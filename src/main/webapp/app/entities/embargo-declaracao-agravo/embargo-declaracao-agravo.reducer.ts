import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmbargoDeclaracaoAgravo, defaultValue } from 'app/shared/model/embargo-declaracao-agravo.model';

export const ACTION_TYPES = {
  FETCH_EMBARGODECLARACAOAGRAVO_LIST: 'embargoDeclaracaoAgravo/FETCH_EMBARGODECLARACAOAGRAVO_LIST',
  FETCH_EMBARGODECLARACAOAGRAVO: 'embargoDeclaracaoAgravo/FETCH_EMBARGODECLARACAOAGRAVO',
  CREATE_EMBARGODECLARACAOAGRAVO: 'embargoDeclaracaoAgravo/CREATE_EMBARGODECLARACAOAGRAVO',
  UPDATE_EMBARGODECLARACAOAGRAVO: 'embargoDeclaracaoAgravo/UPDATE_EMBARGODECLARACAOAGRAVO',
  DELETE_EMBARGODECLARACAOAGRAVO: 'embargoDeclaracaoAgravo/DELETE_EMBARGODECLARACAOAGRAVO',
  RESET: 'embargoDeclaracaoAgravo/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmbargoDeclaracaoAgravo>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EmbargoDeclaracaoAgravoState = Readonly<typeof initialState>;

// Reducer

export default (state: EmbargoDeclaracaoAgravoState = initialState, action): EmbargoDeclaracaoAgravoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMBARGODECLARACAOAGRAVO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMBARGODECLARACAOAGRAVO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMBARGODECLARACAOAGRAVO):
    case REQUEST(ACTION_TYPES.UPDATE_EMBARGODECLARACAOAGRAVO):
    case REQUEST(ACTION_TYPES.DELETE_EMBARGODECLARACAOAGRAVO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMBARGODECLARACAOAGRAVO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMBARGODECLARACAOAGRAVO):
    case FAILURE(ACTION_TYPES.CREATE_EMBARGODECLARACAOAGRAVO):
    case FAILURE(ACTION_TYPES.UPDATE_EMBARGODECLARACAOAGRAVO):
    case FAILURE(ACTION_TYPES.DELETE_EMBARGODECLARACAOAGRAVO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMBARGODECLARACAOAGRAVO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMBARGODECLARACAOAGRAVO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMBARGODECLARACAOAGRAVO):
    case SUCCESS(ACTION_TYPES.UPDATE_EMBARGODECLARACAOAGRAVO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMBARGODECLARACAOAGRAVO):
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

const apiUrl = 'api/embargo-declaracao-agravos';

// Actions

export const getEntities: ICrudGetAllAction<IEmbargoDeclaracaoAgravo> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMBARGODECLARACAOAGRAVO_LIST,
    payload: axios.get<IEmbargoDeclaracaoAgravo>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEmbargoDeclaracaoAgravo> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMBARGODECLARACAOAGRAVO,
    payload: axios.get<IEmbargoDeclaracaoAgravo>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmbargoDeclaracaoAgravo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMBARGODECLARACAOAGRAVO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmbargoDeclaracaoAgravo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMBARGODECLARACAOAGRAVO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmbargoDeclaracaoAgravo> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMBARGODECLARACAOAGRAVO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
