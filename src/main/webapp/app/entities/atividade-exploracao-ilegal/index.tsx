import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AtividadeExploracaoIlegal from './atividade-exploracao-ilegal';
import AtividadeExploracaoIlegalDetail from './atividade-exploracao-ilegal-detail';
import AtividadeExploracaoIlegalUpdate from './atividade-exploracao-ilegal-update';
import AtividadeExploracaoIlegalDeleteDialog from './atividade-exploracao-ilegal-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AtividadeExploracaoIlegalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AtividadeExploracaoIlegalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AtividadeExploracaoIlegalDetail} />
      <ErrorBoundaryRoute path={match.url} component={AtividadeExploracaoIlegal} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AtividadeExploracaoIlegalDeleteDialog} />
  </>
);

export default Routes;
