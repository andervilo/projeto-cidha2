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
  oficio?: string;
  assunto?: any;
  linkUnico?: string;
  linkTrf?: string;
  subsecaoJudiciaria?: string;
  turmaTrf1?: string;
  numeroProcessoAdministrativo?: string;
  numeroProcessoJudicialPrimeiraInstancia?: string;
  numeroProcessoJudicialPrimeiraInstanciaLink?: string;
  numeroProcessoJudicialPrimeiraInstanciaObservacoes?: any;
  parecer?: boolean;
  concessaoLiminars?: IConcessaoLiminar[];
  concessaoLiminarCassadas?: IConcessaoLiminarCassada[];
  embargoRespRes?: IEmbargoRespRe[];
  embargoDeclaracaoAgravos?: IEmbargoDeclaracaoAgravo[];
  embargoDeclaracaos?: IEmbargoDeclaracao[];
  embargoRecursoEspecials?: IEmbargoRecursoEspecial[];
  tipoDecisao?: ITipoDecisao;
  tipoEmpreendimento?: ITipoEmpreendimento;
  comarcas?: IComarca[];
  quilombos?: IQuilombo[];
  municipios?: IMunicipio[];
  territorios?: ITerritorio[];
  atividadeExploracaoIlegals?: IAtividadeExploracaoIlegal[];
  unidadeConservacaos?: IUnidadeConservacao[];
  envolvidosConflitoLitigios?: IEnvolvidosConflitoLitigio[];
  terraIndigenas?: ITerraIndigena[];
  processoConflitos?: IProcessoConflito[];
  parteInteresssadas?: IParteInteresssada[];
  relators?: IRelator[];
  problemaJuridicos?: IProblemaJuridico[];
}

export const defaultValue: Readonly<IProcesso> = {
  parecer: false,
};
