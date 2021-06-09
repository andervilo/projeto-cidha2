import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SecaoJudiciaria from './secao-judiciaria';
import SecaoJudiciariaDetail from './secao-judiciaria-detail';
import SecaoJudiciariaUpdate from './secao-judiciaria-update';
import SecaoJudiciariaDeleteDialog from './secao-judiciaria-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SecaoJudiciariaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SecaoJudiciariaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SecaoJudiciariaDetail} />
      <ErrorBoundaryRoute path={match.url} component={SecaoJudiciaria} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SecaoJudiciariaDeleteDialog} />
  </>
);

export default Routes;
