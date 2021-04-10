import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmbargoDeclaracao, defaultValue } from 'app/shared/model/embargo-declaracao.model';

export const ACTION_TYPES = {
  FETCH_EMBARGODECLARACAO_LIST: 'embargoDeclaracao/FETCH_EMBARGODECLARACAO_LIST',
  FETCH_EMBARGODECLARACAO: 'embargoDeclaracao/FETCH_EMBARGODECLARACAO',
  CREATE_EMBARGODECLARACAO: 'embargoDeclaracao/CREATE_EMBARGODECLARACAO',
  UPDATE_EMBARGODECLARACAO: 'embargoDeclaracao/UPDATE_EMBARGODECLARACAO',
  PARTIAL_UPDATE_EMBARGODECLARACAO: 'embargoDeclaracao/PARTIAL_UPDATE_EMBARGODECLARACAO',
  DELETE_EMBARGODECLARACAO: 'embargoDeclaracao/DELETE_EMBARGODECLARACAO',
  RESET: 'embargoDeclaracao/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmbargoDeclaracao>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EmbargoDeclaracaoState = Readonly<typeof initialState>;

// Reducer

export default (state: EmbargoDeclaracaoState = initialState, action): EmbargoDeclaracaoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMBARGODECLARACAO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMBARGODECLARACAO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMBARGODECLARACAO):
    case REQUEST(ACTION_TYPES.UPDATE_EMBARGODECLARACAO):
    case REQUEST(ACTION_TYPES.DELETE_EMBARGODECLARACAO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_EMBARGODECLARACAO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMBARGODECLARACAO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMBARGODECLARACAO):
    case FAILURE(ACTION_TYPES.CREATE_EMBARGODECLARACAO):
    case FAILURE(ACTION_TYPES.UPDATE_EMBARGODECLARACAO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_EMBARGODECLARACAO):
    case FAILURE(ACTION_TYPES.DELETE_EMBARGODECLARACAO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMBARGODECLARACAO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMBARGODECLARACAO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMBARGODECLARACAO):
    case SUCCESS(ACTION_TYPES.UPDATE_EMBARGODECLARACAO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_EMBARGODECLARACAO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMBARGODECLARACAO):
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

const apiUrl = 'api/embargo-declaracaos';

// Actions

export const getEntities: ICrudGetAllAction<IEmbargoDeclaracao> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMBARGODECLARACAO_LIST,
    payload: axios.get<IEmbargoDeclaracao>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEmbargoDeclaracao> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMBARGODECLARACAO,
    payload: axios.get<IEmbargoDeclaracao>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmbargoDeclaracao> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMBARGODECLARACAO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmbargoDeclaracao> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMBARGODECLARACAO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IEmbargoDeclaracao> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_EMBARGODECLARACAO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmbargoDeclaracao> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMBARGODECLARACAO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
