import { IProblemaJuridico } from 'app/shared/model/problema-juridico.model';

export interface IFundamentacaoLegal {
  id?: number;
  fundamentacaoLegal?: string | null;
  folhasFundamentacaoLegal?: string | null;
  fundamentacaoLegalSugerida?: string | null;
  problemaJuridicos?: IProblemaJuridico[] | null;
}

export const defaultValue: Readonly<IFundamentacaoLegal> = {};
