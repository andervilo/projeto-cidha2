import { IConcessaoLiminar } from 'app/shared/model/concessao-liminar.model';
import { IConcessaoLiminarCassada } from 'app/shared/model/concessao-liminar-cassada.model';
import { IEmbargoDeclaracao } from 'app/shared/model/embargo-declaracao.model';
import { IEmbargoDeclaracaoAgravo } from 'app/shared/model/embargo-declaracao-agravo.model';
import { IEmbargoRecursoEspecial } from 'app/shared/model/embargo-recurso-especial.model';
import { IEmbargoRespRe } from 'app/shared/model/embargo-resp-re.model';
import { ITipoDecisao } from 'app/shared/model/tipo-decisao.model';
import { ITipoEmpreendimento } from 'app/shared/model/tipo-empreendimento.model';
import { ISecaoJudiciaria } from 'app/shared/model/secao-judiciaria.model';
import { IComarca } from 'app/shared/model/comarca.model';
import { IMunicipio } from 'app/shared/model/municipio.model';
import { ITerritorio } from 'app/shared/model/territorio.model';
import { IAtividadeExploracaoIlegal } from 'app/shared/model/atividade-exploracao-ilegal.model';
import { IUnidadeConservacao } from 'app/shared/model/unidade-conservacao.model';
import { IEnvolvidosConflitoLitigio } from 'app/shared/model/envolvidos-conflito-litigio.model';
import { ITerraIndigena } from 'app/shared/model/terra-indigena.model';
import { IProcessoConflito } from 'app/shared/model/processo-conflito.model';
import { IParteInteresssada } from 'app/shared/model/parte-interesssada.model';
import { IRelator } from 'app/shared/model/relator.model';
import { IQuilombo } from 'app/shared/model/quilombo.model';
import { IProblemaJuridico } from 'app/shared/model/problema-juridico.model';
import { StatusProcesso } from 'app/shared/model/enumerations/status-processo.model';

export interface IProcesso {
  id?: number;
  numeroProcesso?: string | null;
  oficio?: string | null;
  assunto?: string | null;
  linkUnico?: string | null;
  linkTrf?: string | null;
  turmaTrf1?: string | null;
  numeroProcessoAdministrativo?: string | null;
  numeroProcessoJudicialPrimeiraInstancia?: string | null;
  numeroProcessoJudicialPrimeiraInstanciaLink?: string | null;
  numeroProcessoJudicialPrimeiraInstanciaObservacoes?: string | null;
  parecer?: boolean | null;
  folhasProcessoConcessaoLiminar?: string | null;
  concessaoLiminarObservacoes?: string | null;
  folhasProcessoCassacao?: string | null;
  folhasParecer?: string | null;
  folhasEmbargo?: string | null;
  acordaoEmbargo?: string | null;
  folhasCienciaJulgEmbargos?: string | null;
  apelacao?: string | null;
  folhasApelacao?: string | null;
  acordaoApelacao?: string | null;
  folhasCienciaJulgApelacao?: string | null;
  embargoDeclaracao?: boolean | null;
  embargoRecursoExtraordinario?: boolean | null;
  folhasRecursoEspecial?: string | null;
  acordaoRecursoEspecial?: string | null;
  folhasCienciaJulgamentoRecursoEspecial?: string | null;
  embargoRecursoEspecial?: boolean | null;
  folhasCiencia?: string | null;
  agravoRespRe?: string | null;
  folhasRespRe?: string | null;
  acordaoAgravoRespRe?: string | null;
  folhasCienciaJulgamentoAgravoRespRe?: string | null;
  embargoRespRe?: string | null;
  agravoInterno?: string | null;
  folhasAgravoInterno?: string | null;
  embargoRecursoAgravo?: boolean | null;
  observacoes?: string | null;
  recursoSTJ?: boolean | null;
  linkRecursoSTJ?: string | null;
  folhasRecursoSTJ?: string | null;
  recursoSTF?: boolean | null;
  linkRecursoSTF?: string | null;
  folhasRecursoSTF?: string | null;
  folhasMemorialMPF?: string | null;
  execusaoProvisoria?: boolean | null;
  numeracaoExecusaoProvisoria?: string | null;
  recuperacaoEfetivaCumprimentoSentenca?: string | null;
  recuperacaoEfetivaCumprimentoSentencaObservacoes?: string | null;
  envolveEmpreendimento?: boolean | null;
  envolveExploracaoIlegal?: boolean | null;
  envolveTerraQuilombola?: boolean | null;
  envolveTerraComunidadeTradicional?: boolean | null;
  envolveTerraIndigena?: boolean | null;
  resumoFatos?: string | null;
  tamanhoArea?: number | null;
  valorArea?: number | null;
  tamanhoAreaObservacao?: string | null;
  dadosGeograficosLitigioConflito?: boolean | null;
  latitude?: string | null;
  longitude?: string | null;
  numeroProcessoMPF?: string | null;
  numeroEmbargo?: string | null;
  pautaApelacao?: string | null;
  numeroRecursoEspecial?: string | null;
  admissiblidade?: string | null;
  envolveGrandeProjeto?: boolean | null;
  envolveUnidadeConservacao?: boolean | null;
  linkReferencia?: string | null;
  statusProcesso?: StatusProcesso | null;
  concessaoLiminars?: IConcessaoLiminar[] | null;
  concessaoLiminarCassadas?: IConcessaoLiminarCassada[] | null;
  embargoDeclaracaos?: IEmbargoDeclaracao[] | null;
  embargoDeclaracaoAgravos?: IEmbargoDeclaracaoAgravo[] | null;
  embargoRecursoEspecials?: IEmbargoRecursoEspecial[] | null;
  embargoRespRes?: IEmbargoRespRe[] | null;
  tipoDecisao?: ITipoDecisao | null;
  tipoEmpreendimento?: ITipoEmpreendimento | null;
  secaoJudiciaria?: ISecaoJudiciaria | null;
  comarcas?: IComarca[] | null;
  municipios?: IMunicipio[] | null;
  territorios?: ITerritorio[] | null;
  atividadeExploracaoIlegals?: IAtividadeExploracaoIlegal[] | null;
  unidadeConservacaos?: IUnidadeConservacao[] | null;
  envolvidosConflitoLitigios?: IEnvolvidosConflitoLitigio[] | null;
  terraIndigenas?: ITerraIndigena[] | null;
  processoConflitos?: IProcessoConflito[] | null;
  parteInteresssadas?: IParteInteresssada[] | null;
  relators?: IRelator[] | null;
  quilombos?: IQuilombo[] | null;
  problemaJuridicos?: IProblemaJuridico[] | null;
}

export const defaultValue: Readonly<IProcesso> = {
  parecer: false,
  embargoDeclaracao: false,
  embargoRecursoExtraordinario: false,
  embargoRecursoEspecial: false,
  embargoRecursoAgravo: false,
  recursoSTJ: false,
  recursoSTF: false,
  execusaoProvisoria: false,
  envolveEmpreendimento: false,
  envolveExploracaoIlegal: false,
  envolveTerraQuilombola: false,
  envolveTerraComunidadeTradicional: false,
  envolveTerraIndigena: false,
  dadosGeograficosLitigioConflito: false,
  envolveGrandeProjeto: false,
  envolveUnidadeConservacao: false,
};
