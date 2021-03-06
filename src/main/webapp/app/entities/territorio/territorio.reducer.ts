import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITerritorio, defaultValue } from 'app/shared/model/territorio.model';

export const ACTION_TYPES = {
  FETCH_TERRITORIO_LIST: 'territorio/FETCH_TERRITORIO_LIST',
  FETCH_TERRITORIO: 'territorio/FETCH_TERRITORIO',
  CREATE_TERRITORIO: 'territorio/CREATE_TERRITORIO',
  UPDATE_TERRITORIO: 'territorio/UPDATE_TERRITORIO',
  PARTIAL_UPDATE_TERRITORIO: 'territorio/PARTIAL_UPDATE_TERRITORIO',
  DELETE_TERRITORIO: 'territorio/DELETE_TERRITORIO',
  RESET: 'territorio/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITerritorio>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TerritorioState = Readonly<typeof initialState>;

// Reducer

export default (state: TerritorioState = initialState, action): TerritorioState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TERRITORIO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TERRITORIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TERRITORIO):
    case REQUEST(ACTION_TYPES.UPDATE_TERRITORIO):
    case REQUEST(ACTION_TYPES.DELETE_TERRITORIO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TERRITORIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TERRITORIO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TERRITORIO):
    case FAILURE(ACTION_TYPES.CREATE_TERRITORIO):
    case FAILURE(ACTION_TYPES.UPDATE_TERRITORIO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TERRITORIO):
    case FAILURE(ACTION_TYPES.DELETE_TERRITORIO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TERRITORIO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TERRITORIO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TERRITORIO):
    case SUCCESS(ACTION_TYPES.UPDATE_TERRITORIO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TERRITORIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TERRITORIO):
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

const apiUrl = 'api/territorios';

// Actions

export const getEntities: ICrudGetAllAction<ITerritorio> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TERRITORIO_LIST,
    payload: axios.get<ITerritorio>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITerritorio> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TERRITORIO,
    payload: axios.get<ITerritorio>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITerritorio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TERRITORIO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITerritorio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TERRITORIO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITerritorio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TERRITORIO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITerritorio> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TERRITORIO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
