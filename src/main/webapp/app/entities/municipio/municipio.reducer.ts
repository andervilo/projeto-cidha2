import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMunicipio, defaultValue } from 'app/shared/model/municipio.model';

export const ACTION_TYPES = {
  FETCH_MUNICIPIO_LIST: 'municipio/FETCH_MUNICIPIO_LIST',
  FETCH_MUNICIPIO: 'municipio/FETCH_MUNICIPIO',
  CREATE_MUNICIPIO: 'municipio/CREATE_MUNICIPIO',
  UPDATE_MUNICIPIO: 'municipio/UPDATE_MUNICIPIO',
  PARTIAL_UPDATE_MUNICIPIO: 'municipio/PARTIAL_UPDATE_MUNICIPIO',
  DELETE_MUNICIPIO: 'municipio/DELETE_MUNICIPIO',
  RESET: 'municipio/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMunicipio>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type MunicipioState = Readonly<typeof initialState>;

// Reducer

export default (state: MunicipioState = initialState, action): MunicipioState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MUNICIPIO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MUNICIPIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MUNICIPIO):
    case REQUEST(ACTION_TYPES.UPDATE_MUNICIPIO):
    case REQUEST(ACTION_TYPES.DELETE_MUNICIPIO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_MUNICIPIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MUNICIPIO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MUNICIPIO):
    case FAILURE(ACTION_TYPES.CREATE_MUNICIPIO):
    case FAILURE(ACTION_TYPES.UPDATE_MUNICIPIO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_MUNICIPIO):
    case FAILURE(ACTION_TYPES.DELETE_MUNICIPIO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MUNICIPIO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_MUNICIPIO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MUNICIPIO):
    case SUCCESS(ACTION_TYPES.UPDATE_MUNICIPIO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_MUNICIPIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MUNICIPIO):
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

const apiUrl = 'api/municipios';

// Actions

export const getEntities: ICrudGetAllAction<IMunicipio> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MUNICIPIO_LIST,
    payload: axios.get<IMunicipio>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IMunicipio> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MUNICIPIO,
    payload: axios.get<IMunicipio>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMunicipio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MUNICIPIO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMunicipio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MUNICIPIO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IMunicipio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_MUNICIPIO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMunicipio> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MUNICIPIO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
