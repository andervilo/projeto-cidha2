import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAtividadeExploracaoIlegal, defaultValue } from 'app/shared/model/atividade-exploracao-ilegal.model';

export const ACTION_TYPES = {
  FETCH_ATIVIDADEEXPLORACAOILEGAL_LIST: 'atividadeExploracaoIlegal/FETCH_ATIVIDADEEXPLORACAOILEGAL_LIST',
  FETCH_ATIVIDADEEXPLORACAOILEGAL: 'atividadeExploracaoIlegal/FETCH_ATIVIDADEEXPLORACAOILEGAL',
  CREATE_ATIVIDADEEXPLORACAOILEGAL: 'atividadeExploracaoIlegal/CREATE_ATIVIDADEEXPLORACAOILEGAL',
  UPDATE_ATIVIDADEEXPLORACAOILEGAL: 'atividadeExploracaoIlegal/UPDATE_ATIVIDADEEXPLORACAOILEGAL',
  DELETE_ATIVIDADEEXPLORACAOILEGAL: 'atividadeExploracaoIlegal/DELETE_ATIVIDADEEXPLORACAOILEGAL',
  RESET: 'atividadeExploracaoIlegal/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAtividadeExploracaoIlegal>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type AtividadeExploracaoIlegalState = Readonly<typeof initialState>;

// Reducer

export default (state: AtividadeExploracaoIlegalState = initialState, action): AtividadeExploracaoIlegalState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ATIVIDADEEXPLORACAOILEGAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ATIVIDADEEXPLORACAOILEGAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ATIVIDADEEXPLORACAOILEGAL):
    case REQUEST(ACTION_TYPES.UPDATE_ATIVIDADEEXPLORACAOILEGAL):
    case REQUEST(ACTION_TYPES.DELETE_ATIVIDADEEXPLORACAOILEGAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ATIVIDADEEXPLORACAOILEGAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ATIVIDADEEXPLORACAOILEGAL):
    case FAILURE(ACTION_TYPES.CREATE_ATIVIDADEEXPLORACAOILEGAL):
    case FAILURE(ACTION_TYPES.UPDATE_ATIVIDADEEXPLORACAOILEGAL):
    case FAILURE(ACTION_TYPES.DELETE_ATIVIDADEEXPLORACAOILEGAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ATIVIDADEEXPLORACAOILEGAL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ATIVIDADEEXPLORACAOILEGAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ATIVIDADEEXPLORACAOILEGAL):
    case SUCCESS(ACTION_TYPES.UPDATE_ATIVIDADEEXPLORACAOILEGAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ATIVIDADEEXPLORACAOILEGAL):
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

const apiUrl = 'api/atividade-exploracao-ilegals';

// Actions

export const getEntities: ICrudGetAllAction<IAtividadeExploracaoIlegal> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ATIVIDADEEXPLORACAOILEGAL_LIST,
    payload: axios.get<IAtividadeExploracaoIlegal>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IAtividadeExploracaoIlegal> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ATIVIDADEEXPLORACAOILEGAL,
    payload: axios.get<IAtividadeExploracaoIlegal>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAtividadeExploracaoIlegal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ATIVIDADEEXPLORACAOILEGAL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAtividadeExploracaoIlegal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ATIVIDADEEXPLORACAOILEGAL,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAtividadeExploracaoIlegal> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ATIVIDADEEXPLORACAOILEGAL,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
