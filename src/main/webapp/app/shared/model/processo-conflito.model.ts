import { IConflito } from 'app/shared/model/conflito.model';
import { IDireito } from 'app/shared/model/direito.model';
import { IProcesso } from 'app/shared/model/processo.model';

export interface IProcessoConflito {
  id?: number;
  inicioConflitoObservacoes?: string | null;
  historicoConlito?: string | null;
  nomeCasoComuidade?: string | null;
  consultaPrevia?: boolean | null;
  conflitos?: IConflito[] | null;
  direitos?: IDireito[] | null;
  processos?: IProcesso[] | null;
}

export const defaultValue: Readonly<IProcessoConflito> = {
  consultaPrevia: false,
};
