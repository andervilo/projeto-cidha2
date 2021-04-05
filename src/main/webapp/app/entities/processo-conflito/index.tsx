import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProcessoConflito from './processo-conflito';
import ProcessoConflitoDetail from './processo-conflito-detail';
import ProcessoConflitoUpdate from './processo-conflito-update';
import ProcessoConflitoDeleteDialog from './processo-conflito-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProcessoConflitoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProcessoConflitoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProcessoConflitoDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProcessoConflito} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProcessoConflitoDeleteDialog} />
  </>
);

export default Routes;
