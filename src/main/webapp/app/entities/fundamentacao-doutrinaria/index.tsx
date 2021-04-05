import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FundamentacaoDoutrinaria from './fundamentacao-doutrinaria';
import FundamentacaoDoutrinariaDetail from './fundamentacao-doutrinaria-detail';
import FundamentacaoDoutrinariaUpdate from './fundamentacao-doutrinaria-update';
import FundamentacaoDoutrinariaDeleteDialog from './fundamentacao-doutrinaria-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FundamentacaoDoutrinariaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FundamentacaoDoutrinariaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FundamentacaoDoutrinariaDetail} />
      <ErrorBoundaryRoute path={match.url} component={FundamentacaoDoutrinaria} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FundamentacaoDoutrinariaDeleteDialog} />
  </>
);

export default Routes;
