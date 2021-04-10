import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEtniaIndigena, defaultValue } from 'app/shared/model/etnia-indigena.model';

export const ACTION_TYPES = {
  FETCH_ETNIAINDIGENA_LIST: 'etniaIndigena/FETCH_ETNIAINDIGENA_LIST',
  FETCH_ETNIAINDIGENA: 'etniaIndigena/FETCH_ETNIAINDIGENA',
  CREATE_ETNIAINDIGENA: 'etniaIndigena/CREATE_ETNIAINDIGENA',
  UPDATE_ETNIAINDIGENA: 'etniaIndigena/UPDATE_ETNIAINDIGENA',
  PARTIAL_UPDATE_ETNIAINDIGENA: 'etniaIndigena/PARTIAL_UPDATE_ETNIAINDIGENA',
  DELETE_ETNIAINDIGENA: 'etniaIndigena/DELETE_ETNIAINDIGENA',
  RESET: 'etniaIndigena/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEtniaIndigena>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EtniaIndigenaState = Readonly<typeof initialState>;

// Reducer

export default (state: EtniaIndigenaState = initialState, action): EtniaIndigenaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ETNIAINDIGENA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ETNIAINDIGENA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ETNIAINDIGENA):
    case REQUEST(ACTION_TYPES.UPDATE_ETNIAINDIGENA):
    case REQUEST(ACTION_TYPES.DELETE_ETNIAINDIGENA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ETNIAINDIGENA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ETNIAINDIGENA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ETNIAINDIGENA):
    case FAILURE(ACTION_TYPES.CREATE_ETNIAINDIGENA):
    case FAILURE(ACTION_TYPES.UPDATE_ETNIAINDIGENA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ETNIAINDIGENA):
    case FAILURE(ACTION_TYPES.DELETE_ETNIAINDIGENA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ETNIAINDIGENA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ETNIAINDIGENA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ETNIAINDIGENA):
    case SUCCESS(ACTION_TYPES.UPDATE_ETNIAINDIGENA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ETNIAINDIGENA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ETNIAINDIGENA):
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

const apiUrl = 'api/etnia-indigenas';

// Actions

export const getEntities: ICrudGetAllAction<IEtniaIndigena> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ETNIAINDIGENA_LIST,
    payload: axios.get<IEtniaIndigena>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEtniaIndigena> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ETNIAINDIGENA,
    payload: axios.get<IEtniaIndigena>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEtniaIndigena> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ETNIAINDIGENA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEtniaIndigena> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ETNIAINDIGENA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IEtniaIndigena> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ETNIAINDIGENA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEtniaIndigena> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ETNIAINDIGENA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
