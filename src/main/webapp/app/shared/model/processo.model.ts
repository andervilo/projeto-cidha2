import { IConcessaoLiminar } from 'app/shared/model/concessao-liminar.model';
import { IConcessaoLiminarCassada } from 'app/shared/model/concessao-liminar-cassada.model';
import { IEmbargoRespRe } from 'app/shared/model/embargo-resp-re.model';
import { IEmbargoDeclaracaoAgravo } from 'app/shared/model/embargo-declaracao-agravo.model';
import { IEmbargoDeclaracao } from 'app/shared/model/embargo-declaracao.model';
import { IEmbargoRecursoEspecial } from 'app/shared/model/embargo-recurso-especial.model';
import { ITipoDecisao } from 'app/shared/model/tipo-decisao.model';
import { ITipoEmpreendimento } from 'app/shared/model/tipo-empreendimento.model';
import { IComarca } from 'app/shared/model/comarca.model';
import { IQuilombo } from 'app/shared/model/quilombo.model';
import { IMunicipio } from 'app/shared/model/municipio.model';
import { ITerritorio } from 'app/shared/model/territorio.model';
import { IAtividadeExploracaoIlegal } from 'app/shared/model/atividade-exploracao-ilegal.model';
import { IUnidadeConservacao } from 'app/shared/model/unidade-conservacao.model';
import { IEnvolvidosConflitoLitigio } from 'app/shared/model/envolvidos-conflito-litigio.model';
import { ITerraIndigena } from 'app/shared/model/terra-indigena.model';
import { IProcessoConflito } from 'app/shared/model/processo-conflito.model';
import { IParteInteresssada } from 'app/shared/model/parte-interesssada.model';
import { IRelator } from 'app/shared/model/relator.model';
import { IProblemaJuridico } from 'app/shared/model/problema-juridico.model';

export interface IProcesso {
  id?: number;
  oficio?: string | null;
  assunto?: string | null;
  linkUnico?: string | null;
  linkTrf?: string | null;
  subsecaoJudiciaria?: string | null;
  turmaTrf1?: string | null;
  numeroProcessoAdministrativo?: string | null;
  numeroProcessoJudicialPrimeiraInstancia?: string | null;
  numeroProcessoJudicialPrimeiraInstanciaLink?: string | null;
  numeroProcessoJudicialPrimeiraInstanciaObservacoes?: string | null;
  parecer?: boolean | null;
  apelacao?: string | null;
  concessaoLiminars?: IConcessaoLiminar[] | null;
  concessaoLiminarCassadas?: IConcessaoLiminarCassada[] | null;
  embargoRespRes?: IEmbargoRespRe[] | null;
  embargoDeclaracaoAgravos?: IEmbargoDeclaracaoAgravo[] | null;
  embargoDeclaracaos?: IEmbargoDeclaracao[] | null;
  embargoRecursoEspecials?: IEmbargoRecursoEspecial[] | null;
  tipoDecisao?: ITipoDecisao | null;
  tipoEmpreendimento?: ITipoEmpreendimento | null;
  comarcas?: IComarca[] | null;
  quilombos?: IQuilombo[] | null;
  municipios?: IMunicipio[] | null;
  territorios?: ITerritorio[] | null;
  atividadeExploracaoIlegals?: IAtividadeExploracaoIlegal[] | null;
  unidadeConservacaos?: IUnidadeConservacao[] | null;
  envolvidosConflitoLitigios?: IEnvolvidosConflitoLitigio[] | null;
  terraIndigenas?: ITerraIndigena[] | null;
  processoConflitos?: IProcessoConflito[] | null;
  parteInteresssadas?: IParteInteresssada[] | null;
  relators?: IRelator[] | null;
  problemaJuridicos?: IProblemaJuridico[] | null;
}

export const defaultValue: Readonly<IProcesso> = {
  parecer: false,
};
