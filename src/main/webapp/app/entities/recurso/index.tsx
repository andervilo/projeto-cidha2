import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Recurso from './recurso';
import RecursoDetail from './recurso-detail';
import RecursoUpdate from './recurso-update';
import RecursoDeleteDialog from './recurso-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RecursoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RecursoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RecursoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Recurso} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RecursoDeleteDialog} />
  </>
);

export default Routes;
