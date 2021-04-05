import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProblemaJuridico, defaultValue } from 'app/shared/model/problema-juridico.model';

export const ACTION_TYPES = {
  FETCH_PROBLEMAJURIDICO_LIST: 'problemaJuridico/FETCH_PROBLEMAJURIDICO_LIST',
  FETCH_PROBLEMAJURIDICO: 'problemaJuridico/FETCH_PROBLEMAJURIDICO',
  CREATE_PROBLEMAJURIDICO: 'problemaJuridico/CREATE_PROBLEMAJURIDICO',
  UPDATE_PROBLEMAJURIDICO: 'problemaJuridico/UPDATE_PROBLEMAJURIDICO',
  DELETE_PROBLEMAJURIDICO: 'problemaJuridico/DELETE_PROBLEMAJURIDICO',
  SET_BLOB: 'problemaJuridico/SET_BLOB',
  RESET: 'problemaJuridico/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProblemaJuridico>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ProblemaJuridicoState = Readonly<typeof initialState>;

// Reducer

export default (state: ProblemaJuridicoState = initialState, action): ProblemaJuridicoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROBLEMAJURIDICO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROBLEMAJURIDICO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PROBLEMAJURIDICO):
    case REQUEST(ACTION_TYPES.UPDATE_PROBLEMAJURIDICO):
    case REQUEST(ACTION_TYPES.DELETE_PROBLEMAJURIDICO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PROBLEMAJURIDICO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROBLEMAJURIDICO):
    case FAILURE(ACTION_TYPES.CREATE_PROBLEMAJURIDICO):
    case FAILURE(ACTION_TYPES.UPDATE_PROBLEMAJURIDICO):
    case FAILURE(ACTION_TYPES.DELETE_PROBLEMAJURIDICO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROBLEMAJURIDICO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROBLEMAJURIDICO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROBLEMAJURIDICO):
    case SUCCESS(ACTION_TYPES.UPDATE_PROBLEMAJURIDICO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROBLEMAJURIDICO):
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

const apiUrl = 'api/problema-juridicos';

// Actions

export const getEntities: ICrudGetAllAction<IProblemaJuridico> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PROBLEMAJURIDICO_LIST,
    payload: axios.get<IProblemaJuridico>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IProblemaJuridico> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROBLEMAJURIDICO,
    payload: axios.get<IProblemaJuridico>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProblemaJuridico> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROBLEMAJURIDICO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProblemaJuridico> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROBLEMAJURIDICO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProblemaJuridico> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROBLEMAJURIDICO,
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
