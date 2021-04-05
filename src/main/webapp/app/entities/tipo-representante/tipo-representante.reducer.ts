import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITipoRepresentante, defaultValue } from 'app/shared/model/tipo-representante.model';

export const ACTION_TYPES = {
  FETCH_TIPOREPRESENTANTE_LIST: 'tipoRepresentante/FETCH_TIPOREPRESENTANTE_LIST',
  FETCH_TIPOREPRESENTANTE: 'tipoRepresentante/FETCH_TIPOREPRESENTANTE',
  CREATE_TIPOREPRESENTANTE: 'tipoRepresentante/CREATE_TIPOREPRESENTANTE',
  UPDATE_TIPOREPRESENTANTE: 'tipoRepresentante/UPDATE_TIPOREPRESENTANTE',
  DELETE_TIPOREPRESENTANTE: 'tipoRepresentante/DELETE_TIPOREPRESENTANTE',
  RESET: 'tipoRepresentante/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITipoRepresentante>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TipoRepresentanteState = Readonly<typeof initialState>;

// Reducer

export default (state: TipoRepresentanteState = initialState, action): TipoRepresentanteState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TIPOREPRESENTANTE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TIPOREPRESENTANTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TIPOREPRESENTANTE):
    case REQUEST(ACTION_TYPES.UPDATE_TIPOREPRESENTANTE):
    case REQUEST(ACTION_TYPES.DELETE_TIPOREPRESENTANTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TIPOREPRESENTANTE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TIPOREPRESENTANTE):
    case FAILURE(ACTION_TYPES.CREATE_TIPOREPRESENTANTE):
    case FAILURE(ACTION_TYPES.UPDATE_TIPOREPRESENTANTE):
    case FAILURE(ACTION_TYPES.DELETE_TIPOREPRESENTANTE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIPOREPRESENTANTE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIPOREPRESENTANTE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TIPOREPRESENTANTE):
    case SUCCESS(ACTION_TYPES.UPDATE_TIPOREPRESENTANTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TIPOREPRESENTANTE):
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

const apiUrl = 'api/tipo-representantes';

// Actions

export const getEntities: ICrudGetAllAction<ITipoRepresentante> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TIPOREPRESENTANTE_LIST,
    payload: axios.get<ITipoRepresentante>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITipoRepresentante> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TIPOREPRESENTANTE,
    payload: axios.get<ITipoRepresentante>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITipoRepresentante> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TIPOREPRESENTANTE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITipoRepresentante> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TIPOREPRESENTANTE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITipoRepresentante> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TIPOREPRESENTANTE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
