import { ITipoRepresentante } from 'app/shared/model/tipo-representante.model';
import { IParteInteresssada } from 'app/shared/model/parte-interesssada.model';

export interface IRepresentanteLegal {
  id?: number;
  nome?: string | null;
  tipoRepresentante?: ITipoRepresentante | null;
  processoConflitos?: IParteInteresssada[] | null;
}

export const defaultValue: Readonly<IRepresentanteLegal> = {};
