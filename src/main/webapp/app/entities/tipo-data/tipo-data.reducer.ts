import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITipoData, defaultValue } from 'app/shared/model/tipo-data.model';

export const ACTION_TYPES = {
  FETCH_TIPODATA_LIST: 'tipoData/FETCH_TIPODATA_LIST',
  FETCH_TIPODATA: 'tipoData/FETCH_TIPODATA',
  CREATE_TIPODATA: 'tipoData/CREATE_TIPODATA',
  UPDATE_TIPODATA: 'tipoData/UPDATE_TIPODATA',
  DELETE_TIPODATA: 'tipoData/DELETE_TIPODATA',
  RESET: 'tipoData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITipoData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TipoDataState = Readonly<typeof initialState>;

// Reducer

export default (state: TipoDataState = initialState, action): TipoDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TIPODATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TIPODATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TIPODATA):
    case REQUEST(ACTION_TYPES.UPDATE_TIPODATA):
    case REQUEST(ACTION_TYPES.DELETE_TIPODATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TIPODATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TIPODATA):
    case FAILURE(ACTION_TYPES.CREATE_TIPODATA):
    case FAILURE(ACTION_TYPES.UPDATE_TIPODATA):
    case FAILURE(ACTION_TYPES.DELETE_TIPODATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIPODATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIPODATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TIPODATA):
    case SUCCESS(ACTION_TYPES.UPDATE_TIPODATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TIPODATA):
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

const apiUrl = 'api/tipo-data';

// Actions

export const getEntities: ICrudGetAllAction<ITipoData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TIPODATA_LIST,
    payload: axios.get<ITipoData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITipoData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TIPODATA,
    payload: axios.get<ITipoData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITipoData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TIPODATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITipoData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TIPODATA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITipoData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TIPODATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
