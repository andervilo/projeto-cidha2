import { IProcesso } from 'app/shared/model/processo.model';
import { TipoQuilombo } from 'app/shared/model/enumerations/tipo-quilombo.model';

export interface IQuilombo {
  id?: number;
  nome?: string | null;
  tipoQuilombo?: TipoQuilombo | null;
  processos?: IProcesso[] | null;
}

export const defaultValue: Readonly<IQuilombo> = {};
