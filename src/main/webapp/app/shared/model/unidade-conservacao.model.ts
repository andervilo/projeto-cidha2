import { IProcesso } from 'app/shared/model/processo.model';

export interface IUnidadeConservacao {
  id?: number;
  descricao?: string | null;
  processos?: IProcesso[] | null;
}

export const defaultValue: Readonly<IUnidadeConservacao> = {};
