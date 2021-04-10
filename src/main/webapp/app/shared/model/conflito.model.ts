import { IProcessoConflito } from 'app/shared/model/processo-conflito.model';

export interface IConflito {
  id?: number;
  descricao?: string | null;
  processoConflito?: IProcessoConflito | null;
}

export const defaultValue: Readonly<IConflito> = {};
