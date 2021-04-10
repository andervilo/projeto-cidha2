import { IProcesso } from 'app/shared/model/processo.model';

export interface IComarca {
  id?: number;
  nome?: string | null;
  codigoCnj?: number | null;
  processos?: IProcesso[] | null;
}

export const defaultValue: Readonly<IComarca> = {};
