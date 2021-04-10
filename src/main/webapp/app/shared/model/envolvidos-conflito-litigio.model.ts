import { IProcesso } from 'app/shared/model/processo.model';

export interface IEnvolvidosConflitoLitigio {
  id?: number;
  numeroIndividuos?: number | null;
  fonteInformacaoQtde?: string | null;
  observacoes?: string | null;
  processos?: IProcesso[] | null;
}

export const defaultValue: Readonly<IEnvolvidosConflitoLitigio> = {};
