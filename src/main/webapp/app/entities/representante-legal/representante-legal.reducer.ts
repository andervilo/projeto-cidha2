import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRepresentanteLegal, defaultValue } from 'app/shared/model/representante-legal.model';

export const ACTION_TYPES = {
  FETCH_REPRESENTANTELEGAL_LIST: 'representanteLegal/FETCH_REPRESENTANTELEGAL_LIST',
  FETCH_REPRESENTANTELEGAL: 'representanteLegal/FETCH_REPRESENTANTELEGAL',
  CREATE_REPRESENTANTELEGAL: 'representanteLegal/CREATE_REPRESENTANTELEGAL',
  UPDATE_REPRESENTANTELEGAL: 'representanteLegal/UPDATE_REPRESENTANTELEGAL',
  DELETE_REPRESENTANTELEGAL: 'representanteLegal/DELETE_REPRESENTANTELEGAL',
  RESET: 'representanteLegal/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRepresentanteLegal>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type RepresentanteLegalState = Readonly<typeof initialState>;

// Reducer

export default (state: RepresentanteLegalState = initialState, action): RepresentanteLegalState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_REPRESENTANTELEGAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_REPRESENTANTELEGAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_REPRESENTANTELEGAL):
    case REQUEST(ACTION_TYPES.UPDATE_REPRESENTANTELEGAL):
    case REQUEST(ACTION_TYPES.DELETE_REPRESENTANTELEGAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_REPRESENTANTELEGAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_REPRESENTANTELEGAL):
    case FAILURE(ACTION_TYPES.CREATE_REPRESENTANTELEGAL):
    case FAILURE(ACTION_TYPES.UPDATE_REPRESENTANTELEGAL):
    case FAILURE(ACTION_TYPES.DELETE_REPRESENTANTELEGAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_REPRESENTANTELEGAL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_REPRESENTANTELEGAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_REPRESENTANTELEGAL):
    case SUCCESS(ACTION_TYPES.UPDATE_REPRESENTANTELEGAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_REPRESENTANTELEGAL):
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

const apiUrl = 'api/representante-legals';

// Actions

export const getEntities: ICrudGetAllAction<IRepresentanteLegal> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_REPRESENTANTELEGAL_LIST,
    payload: axios.get<IRepresentanteLegal>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IRepresentanteLegal> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_REPRESENTANTELEGAL,
    payload: axios.get<IRepresentanteLegal>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IRepresentanteLegal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_REPRESENTANTELEGAL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRepresentanteLegal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_REPRESENTANTELEGAL,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRepresentanteLegal> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_REPRESENTANTELEGAL,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
