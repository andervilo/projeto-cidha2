import { IProcesso } from 'app/shared/model/processo.model';

export interface IEmbargoDeclaracaoAgravo {
  id?: number;
  descricao?: string;
  processo?: IProcesso;
}

export const defaultValue: Readonly<IEmbargoDeclaracaoAgravo> = {};
