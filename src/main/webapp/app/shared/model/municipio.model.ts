import { IProcesso } from 'app/shared/model/processo.model';

export interface IMunicipio {
  id?: number;
  amazoniaLegal?: boolean;
  codigoIbge?: number;
  estado?: string;
  nome?: string;
  processos?: IProcesso[];
}

export const defaultValue: Readonly<IMunicipio> = {
  amazoniaLegal: false,
};
