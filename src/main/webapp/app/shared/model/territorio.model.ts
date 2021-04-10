import { IProcesso } from 'app/shared/model/processo.model';

export interface ITerritorio {
  id?: number;
  nome?: string | null;
  processos?: IProcesso[] | null;
}

export const defaultValue: Readonly<ITerritorio> = {};
