import { IEtniaIndigena } from 'app/shared/model/etnia-indigena.model';
import { IProcesso } from 'app/shared/model/processo.model';

export interface ITerraIndigena {
  id?: number;
  descricao?: string | null;
  etnias?: IEtniaIndigena[] | null;
  processos?: IProcesso[] | null;
}

export const defaultValue: Readonly<ITerraIndigena> = {};
