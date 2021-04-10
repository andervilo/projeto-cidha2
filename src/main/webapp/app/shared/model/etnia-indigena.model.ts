import { ITerraIndigena } from 'app/shared/model/terra-indigena.model';

export interface IEtniaIndigena {
  id?: number;
  nome?: string | null;
  terraIndigenas?: ITerraIndigena[] | null;
}

export const defaultValue: Readonly<IEtniaIndigena> = {};
