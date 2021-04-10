import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Conflito from './conflito';
import ConflitoDetail from './conflito-detail';
import ConflitoUpdate from './conflito-update';
import ConflitoDeleteDialog from './conflito-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConflitoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConflitoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConflitoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Conflito} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConflitoDeleteDialog} />
  </>
);

export default Routes;
