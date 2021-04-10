import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TerraIndigena from './terra-indigena';
import TerraIndigenaDetail from './terra-indigena-detail';
import TerraIndigenaUpdate from './terra-indigena-update';
import TerraIndigenaDeleteDialog from './terra-indigena-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TerraIndigenaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TerraIndigenaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TerraIndigenaDetail} />
      <ErrorBoundaryRoute path={match.url} component={TerraIndigena} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TerraIndigenaDeleteDialog} />
  </>
);

export default Routes;
