import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Relator from './relator';
import RelatorDetail from './relator-detail';
import RelatorUpdate from './relator-update';
import RelatorDeleteDialog from './relator-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RelatorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RelatorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RelatorDetail} />
      <ErrorBoundaryRoute path={match.url} component={Relator} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RelatorDeleteDialog} />
  </>
);

export default Routes;
