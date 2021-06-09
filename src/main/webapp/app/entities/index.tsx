import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Comarca from './comarca';
import Quilombo from './quilombo';
import Processo from './processo';
import ConcessaoLiminar from './concessao-liminar';
import TipoEmpreendimento from './tipo-empreendimento';
import TipoDecisao from './tipo-decisao';
import EmbargoRespRe from './embargo-resp-re';
import ConcessaoLiminarCassada from './concessao-liminar-cassada';
import EmbargoDeclaracao from './embargo-declaracao';
import EmbargoDeclaracaoAgravo from './embargo-declaracao-agravo';
import Recurso from './recurso';
import TipoRecurso from './tipo-recurso';
import OpcaoRecurso from './opcao-recurso';
import EnvolvidosConflitoLitigio from './envolvidos-conflito-litigio';
import TipoData from './tipo-data';
import Data from './data';
import FundamentacaoDoutrinaria from './fundamentacao-doutrinaria';
import Jurisprudencia from './jurisprudencia';
import FundamentacaoLegal from './fundamentacao-legal';
import InstrumentoInternacional from './instrumento-internacional';
import ProblemaJuridico from './problema-juridico';
import Municipio from './municipio';
import Territorio from './territorio';
import EmbargoRecursoEspecial from './embargo-recurso-especial';
import AtividadeExploracaoIlegal from './atividade-exploracao-ilegal';
import UnidadeConservacao from './unidade-conservacao';
import TerraIndigena from './terra-indigena';
import EtniaIndigena from './etnia-indigena';
import Direito from './direito';
import Conflito from './conflito';
import ProcessoConflito from './processo-conflito';
import TipoRepresentante from './tipo-representante';
import RepresentanteLegal from './representante-legal';
import ParteInteresssada from './parte-interesssada';
import Relator from './relator';
import SecaoJudiciaria from './secao-judiciaria';
import SubsecaoJudiciaria from './subsecao-judiciaria';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}comarca`} component={Comarca} />
      <ErrorBoundaryRoute path={`${match.url}quilombo`} component={Quilombo} />
      <ErrorBoundaryRoute path={`${match.url}processo`} component={Processo} />
      <ErrorBoundaryRoute path={`${match.url}concessao-liminar`} component={ConcessaoLiminar} />
      <ErrorBoundaryRoute path={`${match.url}tipo-empreendimento`} component={TipoEmpreendimento} />
      <ErrorBoundaryRoute path={`${match.url}tipo-decisao`} component={TipoDecisao} />
      <ErrorBoundaryRoute path={`${match.url}embargo-resp-re`} component={EmbargoRespRe} />
      <ErrorBoundaryRoute path={`${match.url}concessao-liminar-cassada`} component={ConcessaoLiminarCassada} />
      <ErrorBoundaryRoute path={`${match.url}embargo-declaracao`} component={EmbargoDeclaracao} />
      <ErrorBoundaryRoute path={`${match.url}embargo-declaracao-agravo`} component={EmbargoDeclaracaoAgravo} />
      <ErrorBoundaryRoute path={`${match.url}recurso`} component={Recurso} />
      <ErrorBoundaryRoute path={`${match.url}tipo-recurso`} component={TipoRecurso} />
      <ErrorBoundaryRoute path={`${match.url}opcao-recurso`} component={OpcaoRecurso} />
      <ErrorBoundaryRoute path={`${match.url}envolvidos-conflito-litigio`} component={EnvolvidosConflitoLitigio} />
      <ErrorBoundaryRoute path={`${match.url}tipo-data`} component={TipoData} />
      <ErrorBoundaryRoute path={`${match.url}data`} component={Data} />
      <ErrorBoundaryRoute path={`${match.url}fundamentacao-doutrinaria`} component={FundamentacaoDoutrinaria} />
      <ErrorBoundaryRoute path={`${match.url}jurisprudencia`} component={Jurisprudencia} />
      <ErrorBoundaryRoute path={`${match.url}fundamentacao-legal`} component={FundamentacaoLegal} />
      <ErrorBoundaryRoute path={`${match.url}instrumento-internacional`} component={InstrumentoInternacional} />
      <ErrorBoundaryRoute path={`${match.url}problema-juridico`} component={ProblemaJuridico} />
      <ErrorBoundaryRoute path={`${match.url}municipio`} component={Municipio} />
      <ErrorBoundaryRoute path={`${match.url}territorio`} component={Territorio} />
      <ErrorBoundaryRoute path={`${match.url}embargo-recurso-especial`} component={EmbargoRecursoEspecial} />
      <ErrorBoundaryRoute path={`${match.url}atividade-exploracao-ilegal`} component={AtividadeExploracaoIlegal} />
      <ErrorBoundaryRoute path={`${match.url}unidade-conservacao`} component={UnidadeConservacao} />
      <ErrorBoundaryRoute path={`${match.url}terra-indigena`} component={TerraIndigena} />
      <ErrorBoundaryRoute path={`${match.url}etnia-indigena`} component={EtniaIndigena} />
      <ErrorBoundaryRoute path={`${match.url}direito`} component={Direito} />
      <ErrorBoundaryRoute path={`${match.url}conflito`} component={Conflito} />
      <ErrorBoundaryRoute path={`${match.url}processo-conflito`} component={ProcessoConflito} />
      <ErrorBoundaryRoute path={`${match.url}tipo-representante`} component={TipoRepresentante} />
      <ErrorBoundaryRoute path={`${match.url}representante-legal`} component={RepresentanteLegal} />
      <ErrorBoundaryRoute path={`${match.url}parte-interesssada`} component={ParteInteresssada} />
      <ErrorBoundaryRoute path={`${match.url}relator`} component={Relator} />
      <ErrorBoundaryRoute path={`${match.url}secao-judiciaria`} component={SecaoJudiciaria} />
      <ErrorBoundaryRoute path={`${match.url}subsecao-judiciaria`} component={SubsecaoJudiciaria} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
