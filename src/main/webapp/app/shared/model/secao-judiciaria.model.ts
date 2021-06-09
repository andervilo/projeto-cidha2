import { ISubsecaoJudiciaria } from 'app/shared/model/subsecao-judiciaria.model';

export interface ISecaoJudiciaria {
  id?: number;
  sigla?: string | null;
  nome?: string | null;
  subsecaoJudiciaria?: ISubsecaoJudiciaria | null;
}

export const defaultValue: Readonly<ISecaoJudiciaria> = {};
