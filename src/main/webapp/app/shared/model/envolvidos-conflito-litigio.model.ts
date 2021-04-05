import { IProcesso } from 'app/shared/model/processo.model';

export interface IEnvolvidosConflitoLitigio {
  id?: number;
  numeroIndividuos?: number;
  fonteInformacaoQtde?: any;
  observacoes?: any;
  processos?: IProcesso[];
}

export const defaultValue: Readonly<IEnvolvidosConflitoLitigio> = {};
