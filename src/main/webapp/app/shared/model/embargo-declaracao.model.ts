import { IProcesso } from 'app/shared/model/processo.model';

export interface IEmbargoDeclaracao {
  id?: number;
  descricao?: string;
  processo?: IProcesso;
}

export const defaultValue: Readonly<IEmbargoDeclaracao> = {};
