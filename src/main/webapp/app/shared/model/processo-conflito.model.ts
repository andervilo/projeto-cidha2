import { IConflito } from 'app/shared/model/conflito.model';
import { IDireito } from 'app/shared/model/direito.model';
import { IProcesso } from 'app/shared/model/processo.model';

export interface IProcessoConflito {
  id?: number;
  inicioConflitoObservacoes?: any;
  historicoConlito?: any;
  nomeCasoComuidade?: string;
  consultaPrevia?: boolean;
  conflitos?: IConflito[];
  direitos?: IDireito[];
  processos?: IProcesso[];
}

export const defaultValue: Readonly<IProcessoConflito> = {
  consultaPrevia: false,
};
