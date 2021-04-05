import { IProcesso } from 'app/shared/model/processo.model';

export interface IRelator {
  id?: number;
  nome?: string;
  processos?: IProcesso[];
}

export const defaultValue: Readonly<IRelator> = {};
