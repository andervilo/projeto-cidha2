import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoData from './tipo-data';
import TipoDataDetail from './tipo-data-detail';
import TipoDataUpdate from './tipo-data-update';
import TipoDataDeleteDialog from './tipo-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TipoDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TipoDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TipoDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={TipoData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TipoDataDeleteDialog} />
  </>
);

export default Routes;
