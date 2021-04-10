import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFundamentacaoLegal, defaultValue } from 'app/shared/model/fundamentacao-legal.model';

export const ACTION_TYPES = {
  FETCH_FUNDAMENTACAOLEGAL_LIST: 'fundamentacaoLegal/FETCH_FUNDAMENTACAOLEGAL_LIST',
  FETCH_FUNDAMENTACAOLEGAL: 'fundamentacaoLegal/FETCH_FUNDAMENTACAOLEGAL',
  CREATE_FUNDAMENTACAOLEGAL: 'fundamentacaoLegal/CREATE_FUNDAMENTACAOLEGAL',
  UPDATE_FUNDAMENTACAOLEGAL: 'fundamentacaoLegal/UPDATE_FUNDAMENTACAOLEGAL',
  PARTIAL_UPDATE_FUNDAMENTACAOLEGAL: 'fundamentacaoLegal/PARTIAL_UPDATE_FUNDAMENTACAOLEGAL',
  DELETE_FUNDAMENTACAOLEGAL: 'fundamentacaoLegal/DELETE_FUNDAMENTACAOLEGAL',
  SET_BLOB: 'fundamentacaoLegal/SET_BLOB',
  RESET: 'fundamentacaoLegal/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFundamentacaoLegal>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type FundamentacaoLegalState = Readonly<typeof initialState>;

// Reducer

export default (state: FundamentacaoLegalState = initialState, action): FundamentacaoLegalState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FUNDAMENTACAOLEGAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FUNDAMENTACAOLEGAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FUNDAMENTACAOLEGAL):
    case REQUEST(ACTION_TYPES.UPDATE_FUNDAMENTACAOLEGAL):
    case REQUEST(ACTION_TYPES.DELETE_FUNDAMENTACAOLEGAL):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_FUNDAMENTACAOLEGAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_FUNDAMENTACAOLEGAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FUNDAMENTACAOLEGAL):
    case FAILURE(ACTION_TYPES.CREATE_FUNDAMENTACAOLEGAL):
    case FAILURE(ACTION_TYPES.UPDATE_FUNDAMENTACAOLEGAL):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_FUNDAMENTACAOLEGAL):
    case FAILURE(ACTION_TYPES.DELETE_FUNDAMENTACAOLEGAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FUNDAMENTACAOLEGAL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_FUNDAMENTACAOLEGAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FUNDAMENTACAOLEGAL):
    case SUCCESS(ACTION_TYPES.UPDATE_FUNDAMENTACAOLEGAL):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_FUNDAMENTACAOLEGAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FUNDAMENTACAOLEGAL):
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

const apiUrl = 'api/fundamentacao-legals';

// Actions

export const getEntities: ICrudGetAllAction<IFundamentacaoLegal> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FUNDAMENTACAOLEGAL_LIST,
    payload: axios.get<IFundamentacaoLegal>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IFundamentacaoLegal> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FUNDAMENTACAOLEGAL,
    payload: axios.get<IFundamentacaoLegal>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFundamentacaoLegal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FUNDAMENTACAOLEGAL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFundamentacaoLegal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FUNDAMENTACAOLEGAL,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IFundamentacaoLegal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_FUNDAMENTACAOLEGAL,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFundamentacaoLegal> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FUNDAMENTACAOLEGAL,
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
