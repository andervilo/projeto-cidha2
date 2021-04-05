import { IProcessoConflito } from 'app/shared/model/processo-conflito.model';

export interface IConflito {
  id?: number;
  descricao?: any;
  processoConflito?: IProcessoConflito;
}

export const defaultValue: Readonly<IConflito> = {};
