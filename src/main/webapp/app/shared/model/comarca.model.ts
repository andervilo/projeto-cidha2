import { IProcesso } from 'app/shared/model/processo.model';

export interface IComarca {
  id?: number;
  nome?: string;
  codigoCnj?: number;
  processos?: IProcesso[];
}

export const defaultValue: Readonly<IComarca> = {};
