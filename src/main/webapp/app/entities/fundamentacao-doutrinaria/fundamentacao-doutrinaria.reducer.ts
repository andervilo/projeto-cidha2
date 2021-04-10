import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFundamentacaoDoutrinaria, defaultValue } from 'app/shared/model/fundamentacao-doutrinaria.model';

export const ACTION_TYPES = {
  FETCH_FUNDAMENTACAODOUTRINARIA_LIST: 'fundamentacaoDoutrinaria/FETCH_FUNDAMENTACAODOUTRINARIA_LIST',
  FETCH_FUNDAMENTACAODOUTRINARIA: 'fundamentacaoDoutrinaria/FETCH_FUNDAMENTACAODOUTRINARIA',
  CREATE_FUNDAMENTACAODOUTRINARIA: 'fundamentacaoDoutrinaria/CREATE_FUNDAMENTACAODOUTRINARIA',
  UPDATE_FUNDAMENTACAODOUTRINARIA: 'fundamentacaoDoutrinaria/UPDATE_FUNDAMENTACAODOUTRINARIA',
  PARTIAL_UPDATE_FUNDAMENTACAODOUTRINARIA: 'fundamentacaoDoutrinaria/PARTIAL_UPDATE_FUNDAMENTACAODOUTRINARIA',
  DELETE_FUNDAMENTACAODOUTRINARIA: 'fundamentacaoDoutrinaria/DELETE_FUNDAMENTACAODOUTRINARIA',
  SET_BLOB: 'fundamentacaoDoutrinaria/SET_BLOB',
  RESET: 'fundamentacaoDoutrinaria/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFundamentacaoDoutrinaria>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type FundamentacaoDoutrinariaState = Readonly<typeof initialState>;

// Reducer

export default (state: FundamentacaoDoutrinariaState = initialState, action): FundamentacaoDoutrinariaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FUNDAMENTACAODOUTRINARIA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FUNDAMENTACAODOUTRINARIA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FUNDAMENTACAODOUTRINARIA):
    case REQUEST(ACTION_TYPES.UPDATE_FUNDAMENTACAODOUTRINARIA):
    case REQUEST(ACTION_TYPES.DELETE_FUNDAMENTACAODOUTRINARIA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_FUNDAMENTACAODOUTRINARIA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_FUNDAMENTACAODOUTRINARIA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FUNDAMENTACAODOUTRINARIA):
    case FAILURE(ACTION_TYPES.CREATE_FUNDAMENTACAODOUTRINARIA):
    case FAILURE(ACTION_TYPES.UPDATE_FUNDAMENTACAODOUTRINARIA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_FUNDAMENTACAODOUTRINARIA):
    case FAILURE(ACTION_TYPES.DELETE_FUNDAMENTACAODOUTRINARIA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FUNDAMENTACAODOUTRINARIA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_FUNDAMENTACAODOUTRINARIA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FUNDAMENTACAODOUTRINARIA):
    case SUCCESS(ACTION_TYPES.UPDATE_FUNDAMENTACAODOUTRINARIA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_FUNDAMENTACAODOUTRINARIA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FUNDAMENTACAODOUTRINARIA):
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

const apiUrl = 'api/fundamentacao-doutrinarias';

// Actions

export const getEntities: ICrudGetAllAction<IFundamentacaoDoutrinaria> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FUNDAMENTACAODOUTRINARIA_LIST,
    payload: axios.get<IFundamentacaoDoutrinaria>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IFundamentacaoDoutrinaria> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FUNDAMENTACAODOUTRINARIA,
    payload: axios.get<IFundamentacaoDoutrinaria>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFundamentacaoDoutrinaria> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FUNDAMENTACAODOUTRINARIA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFundamentacaoDoutrinaria> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FUNDAMENTACAODOUTRINARIA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IFundamentacaoDoutrinaria> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_FUNDAMENTACAODOUTRINARIA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFundamentacaoDoutrinaria> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FUNDAMENTACAODOUTRINARIA,
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
