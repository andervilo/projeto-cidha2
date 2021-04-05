import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Quilombo from './quilombo';
import QuilomboDetail from './quilombo-detail';
import QuilomboUpdate from './quilombo-update';
import QuilomboDeleteDialog from './quilombo-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={QuilomboUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={QuilomboUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={QuilomboDetail} />
      <ErrorBoundaryRoute path={match.url} component={Quilombo} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={QuilomboDeleteDialog} />
  </>
);

export default Routes;
