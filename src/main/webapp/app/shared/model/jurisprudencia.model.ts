import { IProblemaJuridico } from 'app/shared/model/problema-juridico.model';

export interface IJurisprudencia {
  id?: number;
  jurisprudenciaCitadaDescricao?: string | null;
  folhasJurisprudenciaCitada?: string | null;
  jurisprudenciaSugerida?: string | null;
  problemaJuridicos?: IProblemaJuridico[] | null;
}

export const defaultValue: Readonly<IJurisprudencia> = {};
