import { IProcesso } from 'app/shared/model/processo.model';

export interface IConcessaoLiminarCassada {
  id?: number;
  descricao?: string;
  processo?: IProcesso;
}

export const defaultValue: Readonly<IConcessaoLiminarCassada> = {};
