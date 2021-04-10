import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Territorio from './territorio';
import TerritorioDetail from './territorio-detail';
import TerritorioUpdate from './territorio-update';
import TerritorioDeleteDialog from './territorio-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TerritorioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TerritorioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TerritorioDetail} />
      <ErrorBoundaryRoute path={match.url} component={Territorio} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TerritorioDeleteDialog} />
  </>
);

export default Routes;
