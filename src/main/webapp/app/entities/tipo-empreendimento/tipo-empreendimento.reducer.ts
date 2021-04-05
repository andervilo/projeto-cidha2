import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITipoEmpreendimento, defaultValue } from 'app/shared/model/tipo-empreendimento.model';

export const ACTION_TYPES = {
  FETCH_TIPOEMPREENDIMENTO_LIST: 'tipoEmpreendimento/FETCH_TIPOEMPREENDIMENTO_LIST',
  FETCH_TIPOEMPREENDIMENTO: 'tipoEmpreendimento/FETCH_TIPOEMPREENDIMENTO',
  CREATE_TIPOEMPREENDIMENTO: 'tipoEmpreendimento/CREATE_TIPOEMPREENDIMENTO',
  UPDATE_TIPOEMPREENDIMENTO: 'tipoEmpreendimento/UPDATE_TIPOEMPREENDIMENTO',
  DELETE_TIPOEMPREENDIMENTO: 'tipoEmpreendimento/DELETE_TIPOEMPREENDIMENTO',
  RESET: 'tipoEmpreendimento/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITipoEmpreendimento>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TipoEmpreendimentoState = Readonly<typeof initialState>;

// Reducer

export default (state: TipoEmpreendimentoState = initialState, action): TipoEmpreendimentoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TIPOEMPREENDIMENTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TIPOEMPREENDIMENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TIPOEMPREENDIMENTO):
    case REQUEST(ACTION_TYPES.UPDATE_TIPOEMPREENDIMENTO):
    case REQUEST(ACTION_TYPES.DELETE_TIPOEMPREENDIMENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TIPOEMPREENDIMENTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TIPOEMPREENDIMENTO):
    case FAILURE(ACTION_TYPES.CREATE_TIPOEMPREENDIMENTO):
    case FAILURE(ACTION_TYPES.UPDATE_TIPOEMPREENDIMENTO):
    case FAILURE(ACTION_TYPES.DELETE_TIPOEMPREENDIMENTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIPOEMPREENDIMENTO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIPOEMPREENDIMENTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TIPOEMPREENDIMENTO):
    case SUCCESS(ACTION_TYPES.UPDATE_TIPOEMPREENDIMENTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TIPOEMPREENDIMENTO):
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

const apiUrl = 'api/tipo-empreendimentos';

// Actions

export const getEntities: ICrudGetAllAction<ITipoEmpreendimento> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TIPOEMPREENDIMENTO_LIST,
    payload: axios.get<ITipoEmpreendimento>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITipoEmpreendimento> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TIPOEMPREENDIMENTO,
    payload: axios.get<ITipoEmpreendimento>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITipoEmpreendimento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TIPOEMPREENDIMENTO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITipoEmpreendimento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TIPOEMPREENDIMENTO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITipoEmpreendimento> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TIPOEMPREENDIMENTO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
