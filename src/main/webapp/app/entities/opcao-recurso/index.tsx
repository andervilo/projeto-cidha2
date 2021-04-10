import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OpcaoRecurso from './opcao-recurso';
import OpcaoRecursoDetail from './opcao-recurso-detail';
import OpcaoRecursoUpdate from './opcao-recurso-update';
import OpcaoRecursoDeleteDialog from './opcao-recurso-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OpcaoRecursoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OpcaoRecursoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OpcaoRecursoDetail} />
      <ErrorBoundaryRoute path={match.url} component={OpcaoRecurso} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OpcaoRecursoDeleteDialog} />
  </>
);

export default Routes;
