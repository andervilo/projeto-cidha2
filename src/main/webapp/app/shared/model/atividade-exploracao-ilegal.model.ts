import { IProcesso } from 'app/shared/model/processo.model';

export interface IAtividadeExploracaoIlegal {
  id?: number;
  descricao?: string | null;
  processos?: IProcesso[] | null;
}

export const defaultValue: Readonly<IAtividadeExploracaoIlegal> = {};
