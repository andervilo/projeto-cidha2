import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EnvolvidosConflitoLitigio from './envolvidos-conflito-litigio';
import EnvolvidosConflitoLitigioDetail from './envolvidos-conflito-litigio-detail';
import EnvolvidosConflitoLitigioUpdate from './envolvidos-conflito-litigio-update';
import EnvolvidosConflitoLitigioDeleteDialog from './envolvidos-conflito-litigio-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EnvolvidosConflitoLitigioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EnvolvidosConflitoLitigioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EnvolvidosConflitoLitigioDetail} />
      <ErrorBoundaryRoute path={match.url} component={EnvolvidosConflitoLitigio} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EnvolvidosConflitoLitigioDeleteDialog} />
  </>
);

export default Routes;
