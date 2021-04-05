import { IRepresentanteLegal } from 'app/shared/model/representante-legal.model';
import { IProcesso } from 'app/shared/model/processo.model';

export interface IParteInteresssada {
  id?: number;
  nome?: string;
  classificacao?: string;
  representanteLegals?: IRepresentanteLegal[];
  processos?: IProcesso[];
}

export const defaultValue: Readonly<IParteInteresssada> = {};
