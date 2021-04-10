import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoRepresentante from './tipo-representante';
import TipoRepresentanteDetail from './tipo-representante-detail';
import TipoRepresentanteUpdate from './tipo-representante-update';
import TipoRepresentanteDeleteDialog from './tipo-representante-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TipoRepresentanteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TipoRepresentanteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TipoRepresentanteDetail} />
      <ErrorBoundaryRoute path={match.url} component={TipoRepresentante} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TipoRepresentanteDeleteDialog} />
  </>
);

export default Routes;
