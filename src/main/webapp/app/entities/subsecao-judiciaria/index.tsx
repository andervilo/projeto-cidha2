import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SubsecaoJudiciaria from './subsecao-judiciaria';
import SubsecaoJudiciariaDetail from './subsecao-judiciaria-detail';
import SubsecaoJudiciariaUpdate from './subsecao-judiciaria-update';
import SubsecaoJudiciariaDeleteDialog from './subsecao-judiciaria-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SubsecaoJudiciariaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SubsecaoJudiciariaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SubsecaoJudiciariaDetail} />
      <ErrorBoundaryRoute path={match.url} component={SubsecaoJudiciaria} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SubsecaoJudiciariaDeleteDialog} />
  </>
);

export default Routes;
