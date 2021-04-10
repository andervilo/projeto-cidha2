import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InstrumentoInternacional from './instrumento-internacional';
import InstrumentoInternacionalDetail from './instrumento-internacional-detail';
import InstrumentoInternacionalUpdate from './instrumento-internacional-update';
import InstrumentoInternacionalDeleteDialog from './instrumento-internacional-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InstrumentoInternacionalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InstrumentoInternacionalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InstrumentoInternacionalDetail} />
      <ErrorBoundaryRoute path={match.url} component={InstrumentoInternacional} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InstrumentoInternacionalDeleteDialog} />
  </>
);

export default Routes;
