import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Jurisprudencia from './jurisprudencia';
import JurisprudenciaDetail from './jurisprudencia-detail';
import JurisprudenciaUpdate from './jurisprudencia-update';
import JurisprudenciaDeleteDialog from './jurisprudencia-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={JurisprudenciaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={JurisprudenciaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={JurisprudenciaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Jurisprudencia} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={JurisprudenciaDeleteDialog} />
  </>
);

export default Routes;
