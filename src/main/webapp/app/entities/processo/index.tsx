import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Processo from './processo';
import ProcessoDetail from './processo-detail';
import ProcessoUpdate from './processo-update';
import ProcessoDeleteDialog from './processo-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProcessoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProcessoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProcessoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Processo} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProcessoDeleteDialog} />
  </>
);

export default Routes;
