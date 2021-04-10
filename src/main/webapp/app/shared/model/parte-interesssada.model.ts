import { IRepresentanteLegal } from 'app/shared/model/representante-legal.model';
import { IProcesso } from 'app/shared/model/processo.model';

export interface IParteInteresssada {
  id?: number;
  nome?: string | null;
  classificacao?: string | null;
  representanteLegals?: IRepresentanteLegal[] | null;
  processos?: IProcesso[] | null;
}

export const defaultValue: Readonly<IParteInteresssada> = {};
