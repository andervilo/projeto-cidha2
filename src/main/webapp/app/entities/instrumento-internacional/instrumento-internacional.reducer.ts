import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IInstrumentoInternacional, defaultValue } from 'app/shared/model/instrumento-internacional.model';

export const ACTION_TYPES = {
  FETCH_INSTRUMENTOINTERNACIONAL_LIST: 'instrumentoInternacional/FETCH_INSTRUMENTOINTERNACIONAL_LIST',
  FETCH_INSTRUMENTOINTERNACIONAL: 'instrumentoInternacional/FETCH_INSTRUMENTOINTERNACIONAL',
  CREATE_INSTRUMENTOINTERNACIONAL: 'instrumentoInternacional/CREATE_INSTRUMENTOINTERNACIONAL',
  UPDATE_INSTRUMENTOINTERNACIONAL: 'instrumentoInternacional/UPDATE_INSTRUMENTOINTERNACIONAL',
  DELETE_INSTRUMENTOINTERNACIONAL: 'instrumentoInternacional/DELETE_INSTRUMENTOINTERNACIONAL',
  SET_BLOB: 'instrumentoInternacional/SET_BLOB',
  RESET: 'instrumentoInternacional/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IInstrumentoInternacional>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type InstrumentoInternacionalState = Readonly<typeof initialState>;

// Reducer

export default (state: InstrumentoInternacionalState = initialState, action): InstrumentoInternacionalState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_INSTRUMENTOINTERNACIONAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_INSTRUMENTOINTERNACIONAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_INSTRUMENTOINTERNACIONAL):
    case REQUEST(ACTION_TYPES.UPDATE_INSTRUMENTOINTERNACIONAL):
    case REQUEST(ACTION_TYPES.DELETE_INSTRUMENTOINTERNACIONAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_INSTRUMENTOINTERNACIONAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_INSTRUMENTOINTERNACIONAL):
    case FAILURE(ACTION_TYPES.CREATE_INSTRUMENTOINTERNACIONAL):
    case FAILURE(ACTION_TYPES.UPDATE_INSTRUMENTOINTERNACIONAL):
    case FAILURE(ACTION_TYPES.DELETE_INSTRUMENTOINTERNACIONAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_INSTRUMENTOINTERNACIONAL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_INSTRUMENTOINTERNACIONAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_INSTRUMENTOINTERNACIONAL):
    case SUCCESS(ACTION_TYPES.UPDATE_INSTRUMENTOINTERNACIONAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_INSTRUMENTOINTERNACIONAL):
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

const apiUrl = 'api/instrumento-internacionals';

// Actions

export const getEntities: ICrudGetAllAction<IInstrumentoInternacional> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_INSTRUMENTOINTERNACIONAL_LIST,
    payload: axios.get<IInstrumentoInternacional>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IInstrumentoInternacional> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_INSTRUMENTOINTERNACIONAL,
    payload: axios.get<IInstrumentoInternacional>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IInstrumentoInternacional> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_INSTRUMENTOINTERNACIONAL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IInstrumentoInternacional> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_INSTRUMENTOINTERNACIONAL,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IInstrumentoInternacional> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_INSTRUMENTOINTERNACIONAL,
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
