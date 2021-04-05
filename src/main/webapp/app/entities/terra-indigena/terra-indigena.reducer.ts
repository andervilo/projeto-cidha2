import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITerraIndigena, defaultValue } from 'app/shared/model/terra-indigena.model';

export const ACTION_TYPES = {
  FETCH_TERRAINDIGENA_LIST: 'terraIndigena/FETCH_TERRAINDIGENA_LIST',
  FETCH_TERRAINDIGENA: 'terraIndigena/FETCH_TERRAINDIGENA',
  CREATE_TERRAINDIGENA: 'terraIndigena/CREATE_TERRAINDIGENA',
  UPDATE_TERRAINDIGENA: 'terraIndigena/UPDATE_TERRAINDIGENA',
  DELETE_TERRAINDIGENA: 'terraIndigena/DELETE_TERRAINDIGENA',
  SET_BLOB: 'terraIndigena/SET_BLOB',
  RESET: 'terraIndigena/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITerraIndigena>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TerraIndigenaState = Readonly<typeof initialState>;

// Reducer

export default (state: TerraIndigenaState = initialState, action): TerraIndigenaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TERRAINDIGENA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TERRAINDIGENA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TERRAINDIGENA):
    case REQUEST(ACTION_TYPES.UPDATE_TERRAINDIGENA):
    case REQUEST(ACTION_TYPES.DELETE_TERRAINDIGENA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TERRAINDIGENA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TERRAINDIGENA):
    case FAILURE(ACTION_TYPES.CREATE_TERRAINDIGENA):
    case FAILURE(ACTION_TYPES.UPDATE_TERRAINDIGENA):
    case FAILURE(ACTION_TYPES.DELETE_TERRAINDIGENA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TERRAINDIGENA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TERRAINDIGENA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TERRAINDIGENA):
    case SUCCESS(ACTION_TYPES.UPDATE_TERRAINDIGENA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TERRAINDIGENA):
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

const apiUrl = 'api/terra-indigenas';

// Actions

export const getEntities: ICrudGetAllAction<ITerraIndigena> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TERRAINDIGENA_LIST,
    payload: axios.get<ITerraIndigena>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITerraIndigena> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TERRAINDIGENA,
    payload: axios.get<ITerraIndigena>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITerraIndigena> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TERRAINDIGENA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITerraIndigena> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TERRAINDIGENA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITerraIndigena> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TERRAINDIGENA,
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
