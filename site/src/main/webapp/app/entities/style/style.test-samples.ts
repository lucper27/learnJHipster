import { IStyle, NewStyle } from './style.model';

export const sampleWithRequiredData: IStyle = {
  id: 96546,
  name: 'Orígenes Account',
};

export const sampleWithPartialData: IStyle = {
  id: 61211,
  name: 'Planificador Ladrillo',
};

export const sampleWithFullData: IStyle = {
  id: 95020,
  name: 'bypass Pound',
};

export const sampleWithNewData: NewStyle = {
  name: 'harness Granito Fuerte',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
