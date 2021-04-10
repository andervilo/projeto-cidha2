import { IProcesso } from 'app/shared/model/processo.model';

export interface IConcessaoLiminarCassada {
  id?: number;
  descricao?: string | null;
  processo?: IProcesso | null;
}

export const defaultValue: Readonly<IConcessaoLiminarCassada> = {};
