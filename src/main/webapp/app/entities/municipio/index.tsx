import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Municipio from './municipio';
import MunicipioDetail from './municipio-detail';
import MunicipioUpdate from './municipio-update';
import MunicipioDeleteDialog from './municipio-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MunicipioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MunicipioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MunicipioDetail} />
      <ErrorBoundaryRoute path={match.url} component={Municipio} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MunicipioDeleteDialog} />
  </>
);

export default Routes;
