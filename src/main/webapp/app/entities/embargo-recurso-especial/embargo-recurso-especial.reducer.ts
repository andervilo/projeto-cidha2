import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmbargoRecursoEspecial, defaultValue } from 'app/shared/model/embargo-recurso-especial.model';

export const ACTION_TYPES = {
  FETCH_EMBARGORECURSOESPECIAL_LIST: 'embargoRecursoEspecial/FETCH_EMBARGORECURSOESPECIAL_LIST',
  FETCH_EMBARGORECURSOESPECIAL: 'embargoRecursoEspecial/FETCH_EMBARGORECURSOESPECIAL',
  CREATE_EMBARGORECURSOESPECIAL: 'embargoRecursoEspecial/CREATE_EMBARGORECURSOESPECIAL',
  UPDATE_EMBARGORECURSOESPECIAL: 'embargoRecursoEspecial/UPDATE_EMBARGORECURSOESPECIAL',
  PARTIAL_UPDATE_EMBARGORECURSOESPECIAL: 'embargoRecursoEspecial/PARTIAL_UPDATE_EMBARGORECURSOESPECIAL',
  DELETE_EMBARGORECURSOESPECIAL: 'embargoRecursoEspecial/DELETE_EMBARGORECURSOESPECIAL',
  RESET: 'embargoRecursoEspecial/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmbargoRecursoEspecial>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EmbargoRecursoEspecialState = Readonly<typeof initialState>;

// Reducer

export default (state: EmbargoRecursoEspecialState = initialState, action): EmbargoRecursoEspecialState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMBARGORECURSOESPECIAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMBARGORECURSOESPECIAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMBARGORECURSOESPECIAL):
    case REQUEST(ACTION_TYPES.UPDATE_EMBARGORECURSOESPECIAL):
    case REQUEST(ACTION_TYPES.DELETE_EMBARGORECURSOESPECIAL):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_EMBARGORECURSOESPECIAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMBARGORECURSOESPECIAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMBARGORECURSOESPECIAL):
    case FAILURE(ACTION_TYPES.CREATE_EMBARGORECURSOESPECIAL):
    case FAILURE(ACTION_TYPES.UPDATE_EMBARGORECURSOESPECIAL):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_EMBARGORECURSOESPECIAL):
    case FAILURE(ACTION_TYPES.DELETE_EMBARGORECURSOESPECIAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMBARGORECURSOESPECIAL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMBARGORECURSOESPECIAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMBARGORECURSOESPECIAL):
    case SUCCESS(ACTION_TYPES.UPDATE_EMBARGORECURSOESPECIAL):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_EMBARGORECURSOESPECIAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMBARGORECURSOESPECIAL):
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

const apiUrl = 'api/embargo-recurso-especials';

// Actions

export const getEntities: ICrudGetAllAction<IEmbargoRecursoEspecial> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMBARGORECURSOESPECIAL_LIST,
    payload: axios.get<IEmbargoRecursoEspecial>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEmbargoRecursoEspecial> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMBARGORECURSOESPECIAL,
    payload: axios.get<IEmbargoRecursoEspecial>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmbargoRecursoEspecial> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMBARGORECURSOESPECIAL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmbargoRecursoEspecial> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMBARGORECURSOESPECIAL,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IEmbargoRecursoEspecial> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_EMBARGORECURSOESPECIAL,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmbargoRecursoEspecial> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMBARGORECURSOESPECIAL,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
