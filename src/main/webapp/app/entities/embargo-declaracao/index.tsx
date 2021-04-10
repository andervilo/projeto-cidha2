import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmbargoDeclaracao from './embargo-declaracao';
import EmbargoDeclaracaoDetail from './embargo-declaracao-detail';
import EmbargoDeclaracaoUpdate from './embargo-declaracao-update';
import EmbargoDeclaracaoDeleteDialog from './embargo-declaracao-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmbargoDeclaracaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmbargoDeclaracaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmbargoDeclaracaoDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmbargoDeclaracao} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmbargoDeclaracaoDeleteDialog} />
  </>
);

export default Routes;
