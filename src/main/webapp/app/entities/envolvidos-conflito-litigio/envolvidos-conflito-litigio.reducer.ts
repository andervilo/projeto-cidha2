import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEnvolvidosConflitoLitigio, defaultValue } from 'app/shared/model/envolvidos-conflito-litigio.model';

export const ACTION_TYPES = {
  FETCH_ENVOLVIDOSCONFLITOLITIGIO_LIST: 'envolvidosConflitoLitigio/FETCH_ENVOLVIDOSCONFLITOLITIGIO_LIST',
  FETCH_ENVOLVIDOSCONFLITOLITIGIO: 'envolvidosConflitoLitigio/FETCH_ENVOLVIDOSCONFLITOLITIGIO',
  CREATE_ENVOLVIDOSCONFLITOLITIGIO: 'envolvidosConflitoLitigio/CREATE_ENVOLVIDOSCONFLITOLITIGIO',
  UPDATE_ENVOLVIDOSCONFLITOLITIGIO: 'envolvidosConflitoLitigio/UPDATE_ENVOLVIDOSCONFLITOLITIGIO',
  PARTIAL_UPDATE_ENVOLVIDOSCONFLITOLITIGIO: 'envolvidosConflitoLitigio/PARTIAL_UPDATE_ENVOLVIDOSCONFLITOLITIGIO',
  DELETE_ENVOLVIDOSCONFLITOLITIGIO: 'envolvidosConflitoLitigio/DELETE_ENVOLVIDOSCONFLITOLITIGIO',
  SET_BLOB: 'envolvidosConflitoLitigio/SET_BLOB',
  RESET: 'envolvidosConflitoLitigio/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEnvolvidosConflitoLitigio>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EnvolvidosConflitoLitigioState = Readonly<typeof initialState>;

// Reducer

export default (state: EnvolvidosConflitoLitigioState = initialState, action): EnvolvidosConflitoLitigioState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ENVOLVIDOSCONFLITOLITIGIO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ENVOLVIDOSCONFLITOLITIGIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ENVOLVIDOSCONFLITOLITIGIO):
    case REQUEST(ACTION_TYPES.UPDATE_ENVOLVIDOSCONFLITOLITIGIO):
    case REQUEST(ACTION_TYPES.DELETE_ENVOLVIDOSCONFLITOLITIGIO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ENVOLVIDOSCONFLITOLITIGIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ENVOLVIDOSCONFLITOLITIGIO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ENVOLVIDOSCONFLITOLITIGIO):
    case FAILURE(ACTION_TYPES.CREATE_ENVOLVIDOSCONFLITOLITIGIO):
    case FAILURE(ACTION_TYPES.UPDATE_ENVOLVIDOSCONFLITOLITIGIO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ENVOLVIDOSCONFLITOLITIGIO):
    case FAILURE(ACTION_TYPES.DELETE_ENVOLVIDOSCONFLITOLITIGIO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENVOLVIDOSCONFLITOLITIGIO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENVOLVIDOSCONFLITOLITIGIO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ENVOLVIDOSCONFLITOLITIGIO):
    case SUCCESS(ACTION_TYPES.UPDATE_ENVOLVIDOSCONFLITOLITIGIO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ENVOLVIDOSCONFLITOLITIGIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ENVOLVIDOSCONFLITOLITIGIO):
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

const apiUrl = 'api/envolvidos-conflito-litigios';

// Actions

export const getEntities: ICrudGetAllAction<IEnvolvidosConflitoLitigio> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ENVOLVIDOSCONFLITOLITIGIO_LIST,
    payload: axios.get<IEnvolvidosConflitoLitigio>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEnvolvidosConflitoLitigio> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ENVOLVIDOSCONFLITOLITIGIO,
    payload: axios.get<IEnvolvidosConflitoLitigio>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEnvolvidosConflitoLitigio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ENVOLVIDOSCONFLITOLITIGIO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEnvolvidosConflitoLitigio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ENVOLVIDOSCONFLITOLITIGIO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IEnvolvidosConflitoLitigio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ENVOLVIDOSCONFLITOLITIGIO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEnvolvidosConflitoLitigio> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ENVOLVIDOSCONFLITOLITIGIO,
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
