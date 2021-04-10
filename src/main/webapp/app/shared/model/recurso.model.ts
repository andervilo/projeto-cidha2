import { ITipoRecurso } from 'app/shared/model/tipo-recurso.model';
import { IOpcaoRecurso } from 'app/shared/model/opcao-recurso.model';
import { IProcesso } from 'app/shared/model/processo.model';

export interface IRecurso {
  id?: number;
  observacoes?: string | null;
  tipoRecurso?: ITipoRecurso | null;
  opcaoRecurso?: IOpcaoRecurso | null;
  processo?: IProcesso | null;
}

export const defaultValue: Readonly<IRecurso> = {};
