import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmbargoRespRe, defaultValue } from 'app/shared/model/embargo-resp-re.model';

export const ACTION_TYPES = {
  FETCH_EMBARGORESPRE_LIST: 'embargoRespRe/FETCH_EMBARGORESPRE_LIST',
  FETCH_EMBARGORESPRE: 'embargoRespRe/FETCH_EMBARGORESPRE',
  CREATE_EMBARGORESPRE: 'embargoRespRe/CREATE_EMBARGORESPRE',
  UPDATE_EMBARGORESPRE: 'embargoRespRe/UPDATE_EMBARGORESPRE',
  PARTIAL_UPDATE_EMBARGORESPRE: 'embargoRespRe/PARTIAL_UPDATE_EMBARGORESPRE',
  DELETE_EMBARGORESPRE: 'embargoRespRe/DELETE_EMBARGORESPRE',
  RESET: 'embargoRespRe/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmbargoRespRe>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EmbargoRespReState = Readonly<typeof initialState>;

// Reducer

export default (state: EmbargoRespReState = initialState, action): EmbargoRespReState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMBARGORESPRE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMBARGORESPRE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMBARGORESPRE):
    case REQUEST(ACTION_TYPES.UPDATE_EMBARGORESPRE):
    case REQUEST(ACTION_TYPES.DELETE_EMBARGORESPRE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_EMBARGORESPRE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMBARGORESPRE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMBARGORESPRE):
    case FAILURE(ACTION_TYPES.CREATE_EMBARGORESPRE):
    case FAILURE(ACTION_TYPES.UPDATE_EMBARGORESPRE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_EMBARGORESPRE):
    case FAILURE(ACTION_TYPES.DELETE_EMBARGORESPRE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMBARGORESPRE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMBARGORESPRE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMBARGORESPRE):
    case SUCCESS(ACTION_TYPES.UPDATE_EMBARGORESPRE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_EMBARGORESPRE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMBARGORESPRE):
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

const apiUrl = 'api/embargo-resp-res';

// Actions

export const getEntities: ICrudGetAllAction<IEmbargoRespRe> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMBARGORESPRE_LIST,
    payload: axios.get<IEmbargoRespRe>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEmbargoRespRe> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMBARGORESPRE,
    payload: axios.get<IEmbargoRespRe>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmbargoRespRe> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMBARGORESPRE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmbargoRespRe> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMBARGORESPRE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IEmbargoRespRe> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_EMBARGORESPRE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmbargoRespRe> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMBARGORESPRE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
