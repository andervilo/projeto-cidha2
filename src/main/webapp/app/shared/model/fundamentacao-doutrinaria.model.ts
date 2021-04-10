import { IProblemaJuridico } from 'app/shared/model/problema-juridico.model';

export interface IFundamentacaoDoutrinaria {
  id?: number;
  fundamentacaoDoutrinariaCitada?: any;
  folhasFundamentacaoDoutrinaria?: string;
  fundamentacaoDoutrinariaSugerida?: any;
  problemaJuridicos?: IProblemaJuridico[];
}

export const defaultValue: Readonly<IFundamentacaoDoutrinaria> = {};
