import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParteInteresssada from './parte-interesssada';
import ParteInteresssadaDetail from './parte-interesssada-detail';
import ParteInteresssadaUpdate from './parte-interesssada-update';
import ParteInteresssadaDeleteDialog from './parte-interesssada-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParteInteresssadaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParteInteresssadaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParteInteresssadaDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParteInteresssada} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ParteInteresssadaDeleteDialog} />
  </>
);

export default Routes;
