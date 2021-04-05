import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Comarca from './comarca';
import ComarcaDetail from './comarca-detail';
import ComarcaUpdate from './comarca-update';
import ComarcaDeleteDialog from './comarca-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ComarcaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ComarcaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ComarcaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Comarca} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ComarcaDeleteDialog} />
  </>
);

export default Routes;
