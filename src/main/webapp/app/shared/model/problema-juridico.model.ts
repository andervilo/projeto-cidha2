import { IFundamentacaoDoutrinaria } from 'app/shared/model/fundamentacao-doutrinaria.model';
import { IJurisprudencia } from 'app/shared/model/jurisprudencia.model';
import { IFundamentacaoLegal } from 'app/shared/model/fundamentacao-legal.model';
import { IInstrumentoInternacional } from 'app/shared/model/instrumento-internacional.model';
import { IProcesso } from 'app/shared/model/processo.model';

export interface IProblemaJuridico {
  id?: number;
  prolemaJuridicoRespondido?: any;
  folhasProblemaJuridico?: string;
  fundamentacaoDoutrinarias?: IFundamentacaoDoutrinaria[];
  jurisprudencias?: IJurisprudencia[];
  fundamentacaoLegals?: IFundamentacaoLegal[];
  instrumentoInternacionals?: IInstrumentoInternacional[];
  processos?: IProcesso[];
}

export const defaultValue: Readonly<IProblemaJuridico> = {};
