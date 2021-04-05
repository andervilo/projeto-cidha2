import { IProcesso } from 'app/shared/model/processo.model';

export interface ITerritorio {
  id?: number;
  nome?: string;
  processos?: IProcesso[];
}

export const defaultValue: Readonly<ITerritorio> = {};
