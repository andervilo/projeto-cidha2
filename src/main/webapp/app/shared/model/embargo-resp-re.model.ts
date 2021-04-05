import { IProcesso } from 'app/shared/model/processo.model';

export interface IEmbargoRespRe {
  id?: number;
  descricao?: string;
  processo?: IProcesso;
}

export const defaultValue: Readonly<IEmbargoRespRe> = {};
