import { IProblemaJuridico } from 'app/shared/model/problema-juridico.model';

export interface IInstrumentoInternacional {
  id?: number;
  instrumentoInternacionalCitadoDescricao?: any;
  folhasInstrumentoInternacional?: string;
  instrumentoInternacionalSugerido?: any;
  problemaJuridicos?: IProblemaJuridico[];
}

export const defaultValue: Readonly<IInstrumentoInternacional> = {};
