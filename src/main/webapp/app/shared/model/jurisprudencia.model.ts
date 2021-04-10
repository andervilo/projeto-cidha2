import { IProblemaJuridico } from 'app/shared/model/problema-juridico.model';

export interface IJurisprudencia {
  id?: number;
  jurisprudenciaCitadaDescricao?: any;
  folhasJurisprudenciaCitada?: string;
  jurisprudenciaSugerida?: any;
  problemaJuridicos?: IProblemaJuridico[];
}

export const defaultValue: Readonly<IJurisprudencia> = {};
