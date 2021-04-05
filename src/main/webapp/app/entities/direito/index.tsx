import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Direito from './direito';
import DireitoDetail from './direito-detail';
import DireitoUpdate from './direito-update';
import DireitoDeleteDialog from './direito-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DireitoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DireitoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DireitoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Direito} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DireitoDeleteDialog} />
  </>
);

export default Routes;
