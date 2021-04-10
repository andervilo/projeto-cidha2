import { IEtniaIndigena } from 'app/shared/model/etnia-indigena.model';
import { IProcesso } from 'app/shared/model/processo.model';

export interface ITerraIndigena {
  id?: number;
  descricao?: any;
  etnias?: IEtniaIndigena[];
  processos?: IProcesso[];
}

export const defaultValue: Readonly<ITerraIndigena> = {};
