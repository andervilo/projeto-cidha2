import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmbargoDeclaracaoAgravo from './embargo-declaracao-agravo';
import EmbargoDeclaracaoAgravoDetail from './embargo-declaracao-agravo-detail';
import EmbargoDeclaracaoAgravoUpdate from './embargo-declaracao-agravo-update';
import EmbargoDeclaracaoAgravoDeleteDialog from './embargo-declaracao-agravo-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmbargoDeclaracaoAgravoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmbargoDeclaracaoAgravoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmbargoDeclaracaoAgravoDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmbargoDeclaracaoAgravo} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmbargoDeclaracaoAgravoDeleteDialog} />
  </>
);

export default Routes;
