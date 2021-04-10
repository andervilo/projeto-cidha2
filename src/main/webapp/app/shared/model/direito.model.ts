import { IProcessoConflito } from 'app/shared/model/processo-conflito.model';

export interface IDireito {
  id?: number;
  descricao?: any;
  processoConflitos?: IProcessoConflito[];
}

export const defaultValue: Readonly<IDireito> = {};
