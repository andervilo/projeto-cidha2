import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmbargoRecursoEspecial from './embargo-recurso-especial';
import EmbargoRecursoEspecialDetail from './embargo-recurso-especial-detail';
import EmbargoRecursoEspecialUpdate from './embargo-recurso-especial-update';
import EmbargoRecursoEspecialDeleteDialog from './embargo-recurso-especial-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmbargoRecursoEspecialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmbargoRecursoEspecialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmbargoRecursoEspecialDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmbargoRecursoEspecial} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmbargoRecursoEspecialDeleteDialog} />
  </>
);

export default Routes;
