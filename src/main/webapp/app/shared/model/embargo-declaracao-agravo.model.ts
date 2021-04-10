import { IProcesso } from 'app/shared/model/processo.model';

export interface IEmbargoDeclaracaoAgravo {
  id?: number;
  descricao?: string | null;
  processo?: IProcesso | null;
}

export const defaultValue: Readonly<IEmbargoDeclaracaoAgravo> = {};
