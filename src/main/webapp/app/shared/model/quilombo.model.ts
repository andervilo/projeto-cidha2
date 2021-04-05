import { IProcesso } from 'app/shared/model/processo.model';

export interface IQuilombo {
  id?: number;
  nome?: string;
  processos?: IProcesso[];
}

export const defaultValue: Readonly<IQuilombo> = {};
