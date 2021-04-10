import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IParteInteresssada, defaultValue } from 'app/shared/model/parte-interesssada.model';

export const ACTION_TYPES = {
  FETCH_PARTEINTERESSSADA_LIST: 'parteInteresssada/FETCH_PARTEINTERESSSADA_LIST',
  FETCH_PARTEINTERESSSADA: 'parteInteresssada/FETCH_PARTEINTERESSSADA',
  CREATE_PARTEINTERESSSADA: 'parteInteresssada/CREATE_PARTEINTERESSSADA',
  UPDATE_PARTEINTERESSSADA: 'parteInteresssada/UPDATE_PARTEINTERESSSADA',
  DELETE_PARTEINTERESSSADA: 'parteInteresssada/DELETE_PARTEINTERESSSADA',
  RESET: 'parteInteresssada/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IParteInteresssada>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ParteInteresssadaState = Readonly<typeof initialState>;

// Reducer

export default (state: ParteInteresssadaState = initialState, action): ParteInteresssadaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PARTEINTERESSSADA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARTEINTERESSSADA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PARTEINTERESSSADA):
    case REQUEST(ACTION_TYPES.UPDATE_PARTEINTERESSSADA):
    case REQUEST(ACTION_TYPES.DELETE_PARTEINTERESSSADA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PARTEINTERESSSADA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARTEINTERESSSADA):
    case FAILURE(ACTION_TYPES.CREATE_PARTEINTERESSSADA):
    case FAILURE(ACTION_TYPES.UPDATE_PARTEINTERESSSADA):
    case FAILURE(ACTION_TYPES.DELETE_PARTEINTERESSSADA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARTEINTERESSSADA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARTEINTERESSSADA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARTEINTERESSSADA):
    case SUCCESS(ACTION_TYPES.UPDATE_PARTEINTERESSSADA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARTEINTERESSSADA):
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

const apiUrl = 'api/parte-interesssadas';

// Actions

export const getEntities: ICrudGetAllAction<IParteInteresssada> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PARTEINTERESSSADA_LIST,
    payload: axios.get<IParteInteresssada>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IParteInteresssada> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARTEINTERESSSADA,
    payload: axios.get<IParteInteresssada>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IParteInteresssada> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARTEINTERESSSADA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IParteInteresssada> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARTEINTERESSSADA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IParteInteresssada> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARTEINTERESSSADA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
