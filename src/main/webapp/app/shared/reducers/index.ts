import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import comarca, {
  ComarcaState
} from 'app/entities/comarca/comarca.reducer';
// prettier-ignore
import quilombo, {
  QuilomboState
} from 'app/entities/quilombo/quilombo.reducer';
// prettier-ignore
import processo, {
  ProcessoState
} from 'app/entities/processo/processo.reducer';
// prettier-ignore
import concessaoLiminar, {
  ConcessaoLiminarState
} from 'app/entities/concessao-liminar/concessao-liminar.reducer';
// prettier-ignore
import tipoEmpreendimento, {
  TipoEmpreendimentoState
} from 'app/entities/tipo-empreendimento/tipo-empreendimento.reducer';
// prettier-ignore
import tipoDecisao, {
  TipoDecisaoState
} from 'app/entities/tipo-decisao/tipo-decisao.reducer';
// prettier-ignore
import embargoRespRe, {
  EmbargoRespReState
} from 'app/entities/embargo-resp-re/embargo-resp-re.reducer';
// prettier-ignore
import concessaoLiminarCassada, {
  ConcessaoLiminarCassadaState
} from 'app/entities/concessao-liminar-cassada/concessao-liminar-cassada.reducer';
// prettier-ignore
import embargoDeclaracao, {
  EmbargoDeclaracaoState
} from 'app/entities/embargo-declaracao/embargo-declaracao.reducer';
// prettier-ignore
import embargoDeclaracaoAgravo, {
  EmbargoDeclaracaoAgravoState
} from 'app/entities/embargo-declaracao-agravo/embargo-declaracao-agravo.reducer';
// prettier-ignore
import recurso, {
  RecursoState
} from 'app/entities/recurso/recurso.reducer';
// prettier-ignore
import tipoRecurso, {
  TipoRecursoState
} from 'app/entities/tipo-recurso/tipo-recurso.reducer';
// prettier-ignore
import opcaoRecurso, {
  OpcaoRecursoState
} from 'app/entities/opcao-recurso/opcao-recurso.reducer';
// prettier-ignore
import envolvidosConflitoLitigio, {
  EnvolvidosConflitoLitigioState
} from 'app/entities/envolvidos-conflito-litigio/envolvidos-conflito-litigio.reducer';
// prettier-ignore
import tipoData, {
  TipoDataState
} from 'app/entities/tipo-data/tipo-data.reducer';
// prettier-ignore
import data, {
  DataState
} from 'app/entities/data/data.reducer';
// prettier-ignore
import fundamentacaoDoutrinaria, {
  FundamentacaoDoutrinariaState
} from 'app/entities/fundamentacao-doutrinaria/fundamentacao-doutrinaria.reducer';
// prettier-ignore
import jurisprudencia, {
  JurisprudenciaState
} from 'app/entities/jurisprudencia/jurisprudencia.reducer';
// prettier-ignore
import fundamentacaoLegal, {
  FundamentacaoLegalState
} from 'app/entities/fundamentacao-legal/fundamentacao-legal.reducer';
// prettier-ignore
import instrumentoInternacional, {
  InstrumentoInternacionalState
} from 'app/entities/instrumento-internacional/instrumento-internacional.reducer';
// prettier-ignore
import problemaJuridico, {
  ProblemaJuridicoState
} from 'app/entities/problema-juridico/problema-juridico.reducer';
// prettier-ignore
import municipio, {
  MunicipioState
} from 'app/entities/municipio/municipio.reducer';
// prettier-ignore
import territorio, {
  TerritorioState
} from 'app/entities/territorio/territorio.reducer';
// prettier-ignore
import embargoRecursoEspecial, {
  EmbargoRecursoEspecialState
} from 'app/entities/embargo-recurso-especial/embargo-recurso-especial.reducer';
// prettier-ignore
import atividadeExploracaoIlegal, {
  AtividadeExploracaoIlegalState
} from 'app/entities/atividade-exploracao-ilegal/atividade-exploracao-ilegal.reducer';
// prettier-ignore
import unidadeConservacao, {
  UnidadeConservacaoState
} from 'app/entities/unidade-conservacao/unidade-conservacao.reducer';
// prettier-ignore
import terraIndigena, {
  TerraIndigenaState
} from 'app/entities/terra-indigena/terra-indigena.reducer';
// prettier-ignore
import etniaIndigena, {
  EtniaIndigenaState
} from 'app/entities/etnia-indigena/etnia-indigena.reducer';
// prettier-ignore
import direito, {
  DireitoState
} from 'app/entities/direito/direito.reducer';
// prettier-ignore
import conflito, {
  ConflitoState
} from 'app/entities/conflito/conflito.reducer';
// prettier-ignore
import processoConflito, {
  ProcessoConflitoState
} from 'app/entities/processo-conflito/processo-conflito.reducer';
// prettier-ignore
import tipoRepresentante, {
  TipoRepresentanteState
} from 'app/entities/tipo-representante/tipo-representante.reducer';
// prettier-ignore
import representanteLegal, {
  RepresentanteLegalState
} from 'app/entities/representante-legal/representante-legal.reducer';
// prettier-ignore
import parteInteresssada, {
  ParteInteresssadaState
} from 'app/entities/parte-interesssada/parte-interesssada.reducer';
// prettier-ignore
import relator, {
  RelatorState
} from 'app/entities/relator/relator.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly comarca: ComarcaState;
  readonly quilombo: QuilomboState;
  readonly processo: ProcessoState;
  readonly concessaoLiminar: ConcessaoLiminarState;
  readonly tipoEmpreendimento: TipoEmpreendimentoState;
  readonly tipoDecisao: TipoDecisaoState;
  readonly embargoRespRe: EmbargoRespReState;
  readonly concessaoLiminarCassada: ConcessaoLiminarCassadaState;
  readonly embargoDeclaracao: EmbargoDeclaracaoState;
  readonly embargoDeclaracaoAgravo: EmbargoDeclaracaoAgravoState;
  readonly recurso: RecursoState;
  readonly tipoRecurso: TipoRecursoState;
  readonly opcaoRecurso: OpcaoRecursoState;
  readonly envolvidosConflitoLitigio: EnvolvidosConflitoLitigioState;
  readonly tipoData: TipoDataState;
  readonly data: DataState;
  readonly fundamentacaoDoutrinaria: FundamentacaoDoutrinariaState;
  readonly jurisprudencia: JurisprudenciaState;
  readonly fundamentacaoLegal: FundamentacaoLegalState;
  readonly instrumentoInternacional: InstrumentoInternacionalState;
  readonly problemaJuridico: ProblemaJuridicoState;
  readonly municipio: MunicipioState;
  readonly territorio: TerritorioState;
  readonly embargoRecursoEspecial: EmbargoRecursoEspecialState;
  readonly atividadeExploracaoIlegal: AtividadeExploracaoIlegalState;
  readonly unidadeConservacao: UnidadeConservacaoState;
  readonly terraIndigena: TerraIndigenaState;
  readonly etniaIndigena: EtniaIndigenaState;
  readonly direito: DireitoState;
  readonly conflito: ConflitoState;
  readonly processoConflito: ProcessoConflitoState;
  readonly tipoRepresentante: TipoRepresentanteState;
  readonly representanteLegal: RepresentanteLegalState;
  readonly parteInteresssada: ParteInteresssadaState;
  readonly relator: RelatorState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  comarca,
  quilombo,
  processo,
  concessaoLiminar,
  tipoEmpreendimento,
  tipoDecisao,
  embargoRespRe,
  concessaoLiminarCassada,
  embargoDeclaracao,
  embargoDeclaracaoAgravo,
  recurso,
  tipoRecurso,
  opcaoRecurso,
  envolvidosConflitoLitigio,
  tipoData,
  data,
  fundamentacaoDoutrinaria,
  jurisprudencia,
  fundamentacaoLegal,
  instrumentoInternacional,
  problemaJuridico,
  municipio,
  territorio,
  embargoRecursoEspecial,
  atividadeExploracaoIlegal,
  unidadeConservacao,
  terraIndigena,
  etniaIndigena,
  direito,
  conflito,
  processoConflito,
  tipoRepresentante,
  representanteLegal,
  parteInteresssada,
  relator,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
