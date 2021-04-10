import { IProcessoConflito } from 'app/shared/model/processo-conflito.model';

export interface IDireito {
  id?: number;
  descricao?: string | null;
  processoConflitos?: IProcessoConflito[] | null;
}

export const defaultValue: Readonly<IDireito> = {};
