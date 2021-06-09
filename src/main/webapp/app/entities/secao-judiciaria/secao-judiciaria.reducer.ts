import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISecaoJudiciaria, defaultValue } from 'app/shared/model/secao-judiciaria.model';

export const ACTION_TYPES = {
  FETCH_SECAOJUDICIARIA_LIST: 'secaoJudiciaria/FETCH_SECAOJUDICIARIA_LIST',
  FETCH_SECAOJUDICIARIA: 'secaoJudiciaria/FETCH_SECAOJUDICIARIA',
  CREATE_SECAOJUDICIARIA: 'secaoJudiciaria/CREATE_SECAOJUDICIARIA',
  UPDATE_SECAOJUDICIARIA: 'secaoJudiciaria/UPDATE_SECAOJUDICIARIA',
  PARTIAL_UPDATE_SECAOJUDICIARIA: 'secaoJudiciaria/PARTIAL_UPDATE_SECAOJUDICIARIA',
  DELETE_SECAOJUDICIARIA: 'secaoJudiciaria/DELETE_SECAOJUDICIARIA',
  RESET: 'secaoJudiciaria/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISecaoJudiciaria>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type SecaoJudiciariaState = Readonly<typeof initialState>;

// Reducer

export default (state: SecaoJudiciariaState = initialState, action): SecaoJudiciariaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SECAOJUDICIARIA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SECAOJUDICIARIA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SECAOJUDICIARIA):
    case REQUEST(ACTION_TYPES.UPDATE_SECAOJUDICIARIA):
    case REQUEST(ACTION_TYPES.DELETE_SECAOJUDICIARIA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_SECAOJUDICIARIA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SECAOJUDICIARIA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SECAOJUDICIARIA):
    case FAILURE(ACTION_TYPES.CREATE_SECAOJUDICIARIA):
    case FAILURE(ACTION_TYPES.UPDATE_SECAOJUDICIARIA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_SECAOJUDICIARIA):
    case FAILURE(ACTION_TYPES.DELETE_SECAOJUDICIARIA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SECAOJUDICIARIA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SECAOJUDICIARIA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SECAOJUDICIARIA):
    case SUCCESS(ACTION_TYPES.UPDATE_SECAOJUDICIARIA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_SECAOJUDICIARIA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SECAOJUDICIARIA):
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

const apiUrl = 'api/secao-judiciarias';

// Actions

export const getEntities: ICrudGetAllAction<ISecaoJudiciaria> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SECAOJUDICIARIA_LIST,
  payload: axios.get<ISecaoJudiciaria>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ISecaoJudiciaria> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SECAOJUDICIARIA,
    payload: axios.get<ISecaoJudiciaria>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISecaoJudiciaria> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SECAOJUDICIARIA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISecaoJudiciaria> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SECAOJUDICIARIA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ISecaoJudiciaria> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_SECAOJUDICIARIA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISecaoJudiciaria> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SECAOJUDICIARIA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
