import { ITerraIndigena } from 'app/shared/model/terra-indigena.model';

export interface IEtniaIndigena {
  id?: number;
  nome?: string;
  terraIndigenas?: ITerraIndigena[];
}

export const defaultValue: Readonly<IEtniaIndigena> = {};
