import { IProblemaJuridico } from 'app/shared/model/problema-juridico.model';

export interface IInstrumentoInternacional {
  id?: number;
  instrumentoInternacionalCitadoDescricao?: string | null;
  folhasInstrumentoInternacional?: string | null;
  instrumentoInternacionalSugerido?: string | null;
  problemaJuridicos?: IProblemaJuridico[] | null;
}

export const defaultValue: Readonly<IInstrumentoInternacional> = {};
