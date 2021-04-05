import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RepresentanteLegal from './representante-legal';
import RepresentanteLegalDetail from './representante-legal-detail';
import RepresentanteLegalUpdate from './representante-legal-update';
import RepresentanteLegalDeleteDialog from './representante-legal-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RepresentanteLegalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RepresentanteLegalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RepresentanteLegalDetail} />
      <ErrorBoundaryRoute path={match.url} component={RepresentanteLegal} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RepresentanteLegalDeleteDialog} />
  </>
);

export default Routes;
