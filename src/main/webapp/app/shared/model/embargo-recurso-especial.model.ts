import { IProcesso } from 'app/shared/model/processo.model';

export interface IEmbargoRecursoEspecial {
  id?: number;
  descricao?: string;
  processo?: IProcesso;
}

export const defaultValue: Readonly<IEmbargoRecursoEspecial> = {};
