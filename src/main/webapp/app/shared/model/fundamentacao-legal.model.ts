import { IProblemaJuridico } from 'app/shared/model/problema-juridico.model';

export interface IFundamentacaoLegal {
  id?: number;
  fundamentacaoLegal?: any;
  folhasFundamentacaoLegal?: string;
  fundamentacaoLegalSugerida?: any;
  problemaJuridicos?: IProblemaJuridico[];
}

export const defaultValue: Readonly<IFundamentacaoLegal> = {};
