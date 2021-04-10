import { IProcesso } from 'app/shared/model/processo.model';

export interface IUnidadeConservacao {
  id?: number;
  descricao?: string;
  processos?: IProcesso[];
}

export const defaultValue: Readonly<IUnidadeConservacao> = {};
