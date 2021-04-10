import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITipoDecisao, defaultValue } from 'app/shared/model/tipo-decisao.model';

export const ACTION_TYPES = {
  FETCH_TIPODECISAO_LIST: 'tipoDecisao/FETCH_TIPODECISAO_LIST',
  FETCH_TIPODECISAO: 'tipoDecisao/FETCH_TIPODECISAO',
  CREATE_TIPODECISAO: 'tipoDecisao/CREATE_TIPODECISAO',
  UPDATE_TIPODECISAO: 'tipoDecisao/UPDATE_TIPODECISAO',
  PARTIAL_UPDATE_TIPODECISAO: 'tipoDecisao/PARTIAL_UPDATE_TIPODECISAO',
  DELETE_TIPODECISAO: 'tipoDecisao/DELETE_TIPODECISAO',
  RESET: 'tipoDecisao/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITipoDecisao>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TipoDecisaoState = Readonly<typeof initialState>;

// Reducer

export default (state: TipoDecisaoState = initialState, action): TipoDecisaoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TIPODECISAO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TIPODECISAO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TIPODECISAO):
    case REQUEST(ACTION_TYPES.UPDATE_TIPODECISAO):
    case REQUEST(ACTION_TYPES.DELETE_TIPODECISAO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TIPODECISAO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TIPODECISAO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TIPODECISAO):
    case FAILURE(ACTION_TYPES.CREATE_TIPODECISAO):
    case FAILURE(ACTION_TYPES.UPDATE_TIPODECISAO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TIPODECISAO):
    case FAILURE(ACTION_TYPES.DELETE_TIPODECISAO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIPODECISAO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIPODECISAO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TIPODECISAO):
    case SUCCESS(ACTION_TYPES.UPDATE_TIPODECISAO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TIPODECISAO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TIPODECISAO):
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

const apiUrl = 'api/tipo-decisaos';

// Actions

export const getEntities: ICrudGetAllAction<ITipoDecisao> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TIPODECISAO_LIST,
    payload: axios.get<ITipoDecisao>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITipoDecisao> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TIPODECISAO,
    payload: axios.get<ITipoDecisao>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITipoDecisao> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TIPODECISAO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITipoDecisao> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TIPODECISAO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITipoDecisao> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TIPODECISAO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITipoDecisao> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TIPODECISAO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
