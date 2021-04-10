import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoDecisao from './tipo-decisao';
import TipoDecisaoDetail from './tipo-decisao-detail';
import TipoDecisaoUpdate from './tipo-decisao-update';
import TipoDecisaoDeleteDialog from './tipo-decisao-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TipoDecisaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TipoDecisaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TipoDecisaoDetail} />
      <ErrorBoundaryRoute path={match.url} component={TipoDecisao} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TipoDecisaoDeleteDialog} />
  </>
);

export default Routes;
