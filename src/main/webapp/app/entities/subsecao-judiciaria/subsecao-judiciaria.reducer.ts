import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISubsecaoJudiciaria, defaultValue } from 'app/shared/model/subsecao-judiciaria.model';

export const ACTION_TYPES = {
  FETCH_SUBSECAOJUDICIARIA_LIST: 'subsecaoJudiciaria/FETCH_SUBSECAOJUDICIARIA_LIST',
  FETCH_SUBSECAOJUDICIARIA: 'subsecaoJudiciaria/FETCH_SUBSECAOJUDICIARIA',
  CREATE_SUBSECAOJUDICIARIA: 'subsecaoJudiciaria/CREATE_SUBSECAOJUDICIARIA',
  UPDATE_SUBSECAOJUDICIARIA: 'subsecaoJudiciaria/UPDATE_SUBSECAOJUDICIARIA',
  PARTIAL_UPDATE_SUBSECAOJUDICIARIA: 'subsecaoJudiciaria/PARTIAL_UPDATE_SUBSECAOJUDICIARIA',
  DELETE_SUBSECAOJUDICIARIA: 'subsecaoJudiciaria/DELETE_SUBSECAOJUDICIARIA',
  RESET: 'subsecaoJudiciaria/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISubsecaoJudiciaria>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type SubsecaoJudiciariaState = Readonly<typeof initialState>;

// Reducer

export default (state: SubsecaoJudiciariaState = initialState, action): SubsecaoJudiciariaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SUBSECAOJUDICIARIA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SUBSECAOJUDICIARIA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SUBSECAOJUDICIARIA):
    case REQUEST(ACTION_TYPES.UPDATE_SUBSECAOJUDICIARIA):
    case REQUEST(ACTION_TYPES.DELETE_SUBSECAOJUDICIARIA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_SUBSECAOJUDICIARIA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SUBSECAOJUDICIARIA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SUBSECAOJUDICIARIA):
    case FAILURE(ACTION_TYPES.CREATE_SUBSECAOJUDICIARIA):
    case FAILURE(ACTION_TYPES.UPDATE_SUBSECAOJUDICIARIA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_SUBSECAOJUDICIARIA):
    case FAILURE(ACTION_TYPES.DELETE_SUBSECAOJUDICIARIA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUBSECAOJUDICIARIA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUBSECAOJUDICIARIA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SUBSECAOJUDICIARIA):
    case SUCCESS(ACTION_TYPES.UPDATE_SUBSECAOJUDICIARIA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_SUBSECAOJUDICIARIA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SUBSECAOJUDICIARIA):
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

const apiUrl = 'api/subsecao-judiciarias';

// Actions

export const getEntities: ICrudGetAllAction<ISubsecaoJudiciaria> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SUBSECAOJUDICIARIA_LIST,
  payload: axios.get<ISubsecaoJudiciaria>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ISubsecaoJudiciaria> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SUBSECAOJUDICIARIA,
    payload: axios.get<ISubsecaoJudiciaria>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISubsecaoJudiciaria> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SUBSECAOJUDICIARIA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISubsecaoJudiciaria> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SUBSECAOJUDICIARIA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ISubsecaoJudiciaria> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_SUBSECAOJUDICIARIA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISubsecaoJudiciaria> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SUBSECAOJUDICIARIA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
