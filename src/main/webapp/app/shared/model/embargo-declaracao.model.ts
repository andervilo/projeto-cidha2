import { IProcesso } from 'app/shared/model/processo.model';

export interface IEmbargoDeclaracao {
  id?: number;
  descricao?: string | null;
  processo?: IProcesso | null;
}

export const defaultValue: Readonly<IEmbargoDeclaracao> = {};
