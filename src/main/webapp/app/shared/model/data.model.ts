import { Moment } from 'moment';
import { ITipoData } from 'app/shared/model/tipo-data.model';
import { IProcesso } from 'app/shared/model/processo.model';

export interface IData {
  id?: number;
  data?: string;
  tipoData?: ITipoData;
  processo?: IProcesso;
}

export const defaultValue: Readonly<IData> = {};
