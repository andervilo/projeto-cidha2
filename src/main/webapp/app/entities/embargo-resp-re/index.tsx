import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmbargoRespRe from './embargo-resp-re';
import EmbargoRespReDetail from './embargo-resp-re-detail';
import EmbargoRespReUpdate from './embargo-resp-re-update';
import EmbargoRespReDeleteDialog from './embargo-resp-re-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmbargoRespReUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmbargoRespReUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmbargoRespReDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmbargoRespRe} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmbargoRespReDeleteDialog} />
  </>
);

export default Routes;
