import { IProcesso } from 'app/shared/model/processo.model';

export interface IMunicipio {
  id?: number;
  amazoniaLegal?: boolean | null;
  codigoIbge?: number | null;
  estado?: string | null;
  nome?: string | null;
  processos?: IProcesso[] | null;
}

export const defaultValue: Readonly<IMunicipio> = {
  amazoniaLegal: false,
};
