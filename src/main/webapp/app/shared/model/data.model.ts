import dayjs from 'dayjs';
import { ITipoData } from 'app/shared/model/tipo-data.model';
import { IProcesso } from 'app/shared/model/processo.model';

export interface IData {
  id?: number;
  data?: string | null;
  tipoData?: ITipoData | null;
  processo?: IProcesso | null;
}

export const defaultValue: Readonly<IData> = {};
