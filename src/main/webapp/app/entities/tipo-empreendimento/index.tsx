import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoEmpreendimento from './tipo-empreendimento';
import TipoEmpreendimentoDetail from './tipo-empreendimento-detail';
import TipoEmpreendimentoUpdate from './tipo-empreendimento-update';
import TipoEmpreendimentoDeleteDialog from './tipo-empreendimento-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TipoEmpreendimentoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TipoEmpreendimentoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TipoEmpreendimentoDetail} />
      <ErrorBoundaryRoute path={match.url} component={TipoEmpreendimento} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TipoEmpreendimentoDeleteDialog} />
  </>
);

export default Routes;
