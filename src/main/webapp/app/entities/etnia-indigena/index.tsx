import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EtniaIndigena from './etnia-indigena';
import EtniaIndigenaDetail from './etnia-indigena-detail';
import EtniaIndigenaUpdate from './etnia-indigena-update';
import EtniaIndigenaDeleteDialog from './etnia-indigena-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EtniaIndigenaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EtniaIndigenaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EtniaIndigenaDetail} />
      <ErrorBoundaryRoute path={match.url} component={EtniaIndigena} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EtniaIndigenaDeleteDialog} />
  </>
);

export default Routes;
