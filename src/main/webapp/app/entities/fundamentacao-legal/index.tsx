import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FundamentacaoLegal from './fundamentacao-legal';
import FundamentacaoLegalDetail from './fundamentacao-legal-detail';
import FundamentacaoLegalUpdate from './fundamentacao-legal-update';
import FundamentacaoLegalDeleteDialog from './fundamentacao-legal-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FundamentacaoLegalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FundamentacaoLegalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FundamentacaoLegalDetail} />
      <ErrorBoundaryRoute path={match.url} component={FundamentacaoLegal} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FundamentacaoLegalDeleteDialog} />
  </>
);

export default Routes;
