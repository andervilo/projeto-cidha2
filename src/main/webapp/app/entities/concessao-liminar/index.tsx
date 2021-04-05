import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ConcessaoLiminar from './concessao-liminar';
import ConcessaoLiminarDetail from './concessao-liminar-detail';
import ConcessaoLiminarUpdate from './concessao-liminar-update';
import ConcessaoLiminarDeleteDialog from './concessao-liminar-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConcessaoLiminarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConcessaoLiminarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConcessaoLiminarDetail} />
      <ErrorBoundaryRoute path={match.url} component={ConcessaoLiminar} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConcessaoLiminarDeleteDialog} />
  </>
);

export default Routes;
