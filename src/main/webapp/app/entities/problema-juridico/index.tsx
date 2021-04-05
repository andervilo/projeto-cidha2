import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProblemaJuridico from './problema-juridico';
import ProblemaJuridicoDetail from './problema-juridico-detail';
import ProblemaJuridicoUpdate from './problema-juridico-update';
import ProblemaJuridicoDeleteDialog from './problema-juridico-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProblemaJuridicoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProblemaJuridicoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProblemaJuridicoDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProblemaJuridico} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProblemaJuridicoDeleteDialog} />
  </>
);

export default Routes;
