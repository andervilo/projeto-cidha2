import { ITipoRecurso } from 'app/shared/model/tipo-recurso.model';
import { IOpcaoRecurso } from 'app/shared/model/opcao-recurso.model';
import { IProcesso } from 'app/shared/model/processo.model';

export interface IRecurso {
  id?: number;
  observacoes?: any;
  tipoRecurso?: ITipoRecurso;
  opcaoRecurso?: IOpcaoRecurso;
  processo?: IProcesso;
}

export const defaultValue: Readonly<IRecurso> = {};
