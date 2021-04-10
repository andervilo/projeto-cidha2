import { IProblemaJuridico } from 'app/shared/model/problema-juridico.model';

export interface IFundamentacaoDoutrinaria {
  id?: number;
  fundamentacaoDoutrinariaCitada?: string | null;
  folhasFundamentacaoDoutrinaria?: string | null;
  fundamentacaoDoutrinariaSugerida?: string | null;
  problemaJuridicos?: IProblemaJuridico[] | null;
}

export const defaultValue: Readonly<IFundamentacaoDoutrinaria> = {};
