import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UnidadeConservacao from './unidade-conservacao';
import UnidadeConservacaoDetail from './unidade-conservacao-detail';
import UnidadeConservacaoUpdate from './unidade-conservacao-update';
import UnidadeConservacaoDeleteDialog from './unidade-conservacao-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UnidadeConservacaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UnidadeConservacaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UnidadeConservacaoDetail} />
      <ErrorBoundaryRoute path={match.url} component={UnidadeConservacao} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UnidadeConservacaoDeleteDialog} />
  </>
);

export default Routes;
