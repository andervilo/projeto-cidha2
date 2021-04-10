import { IProcesso } from 'app/shared/model/processo.model';

export interface IConcessaoLiminar {
  id?: number;
  descricao?: string;
  processo?: IProcesso;
}

export const defaultValue: Readonly<IConcessaoLiminar> = {};
