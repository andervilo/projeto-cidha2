import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITipoRecurso, defaultValue } from 'app/shared/model/tipo-recurso.model';

export const ACTION_TYPES = {
  FETCH_TIPORECURSO_LIST: 'tipoRecurso/FETCH_TIPORECURSO_LIST',
  FETCH_TIPORECURSO: 'tipoRecurso/FETCH_TIPORECURSO',
  CREATE_TIPORECURSO: 'tipoRecurso/CREATE_TIPORECURSO',
  UPDATE_TIPORECURSO: 'tipoRecurso/UPDATE_TIPORECURSO',
  PARTIAL_UPDATE_TIPORECURSO: 'tipoRecurso/PARTIAL_UPDATE_TIPORECURSO',
  DELETE_TIPORECURSO: 'tipoRecurso/DELETE_TIPORECURSO',
  RESET: 'tipoRecurso/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITipoRecurso>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TipoRecursoState = Readonly<typeof initialState>;

// Reducer

export default (state: TipoRecursoState = initialState, action): TipoRecursoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TIPORECURSO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TIPORECURSO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TIPORECURSO):
    case REQUEST(ACTION_TYPES.UPDATE_TIPORECURSO):
    case REQUEST(ACTION_TYPES.DELETE_TIPORECURSO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TIPORECURSO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TIPORECURSO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TIPORECURSO):
    case FAILURE(ACTION_TYPES.CREATE_TIPORECURSO):
    case FAILURE(ACTION_TYPES.UPDATE_TIPORECURSO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TIPORECURSO):
    case FAILURE(ACTION_TYPES.DELETE_TIPORECURSO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIPORECURSO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIPORECURSO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TIPORECURSO):
    case SUCCESS(ACTION_TYPES.UPDATE_TIPORECURSO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TIPORECURSO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TIPORECURSO):
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

const apiUrl = 'api/tipo-recursos';

// Actions

export const getEntities: ICrudGetAllAction<ITipoRecurso> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TIPORECURSO_LIST,
    payload: axios.get<ITipoRecurso>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITipoRecurso> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TIPORECURSO,
    payload: axios.get<ITipoRecurso>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITipoRecurso> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TIPORECURSO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITipoRecurso> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TIPORECURSO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITipoRecurso> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TIPORECURSO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITipoRecurso> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TIPORECURSO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
