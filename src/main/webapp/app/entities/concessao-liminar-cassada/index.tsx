import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ConcessaoLiminarCassada from './concessao-liminar-cassada';
import ConcessaoLiminarCassadaDetail from './concessao-liminar-cassada-detail';
import ConcessaoLiminarCassadaUpdate from './concessao-liminar-cassada-update';
import ConcessaoLiminarCassadaDeleteDialog from './concessao-liminar-cassada-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConcessaoLiminarCassadaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConcessaoLiminarCassadaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConcessaoLiminarCassadaDetail} />
      <ErrorBoundaryRoute path={match.url} component={ConcessaoLiminarCassada} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConcessaoLiminarCassadaDeleteDialog} />
  </>
);

export default Routes;
