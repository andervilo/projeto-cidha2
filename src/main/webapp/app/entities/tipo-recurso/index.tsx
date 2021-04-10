import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoRecurso from './tipo-recurso';
import TipoRecursoDetail from './tipo-recurso-detail';
import TipoRecursoUpdate from './tipo-recurso-update';
import TipoRecursoDeleteDialog from './tipo-recurso-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TipoRecursoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TipoRecursoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TipoRecursoDetail} />
      <ErrorBoundaryRoute path={match.url} component={TipoRecurso} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TipoRecursoDeleteDialog} />
  </>
);

export default Routes;
