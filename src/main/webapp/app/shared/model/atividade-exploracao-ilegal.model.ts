import { IProcesso } from 'app/shared/model/processo.model';

export interface IAtividadeExploracaoIlegal {
  id?: number;
  descricao?: string;
  processos?: IProcesso[];
}

export const defaultValue: Readonly<IAtividadeExploracaoIlegal> = {};
