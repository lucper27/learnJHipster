import { IArtist, NewArtist } from './artist.model';

export const sampleWithRequiredData: IArtist = {
  id: 70921,
  name: 'synthesizing Cantabria Parcela',
};

export const sampleWithPartialData: IArtist = {
  id: 52215,
  name: 'ábanico hacking driver',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithFullData: IArtist = {
  id: 94104,
  name: 'Kenia de',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithNewData: NewArtist = {
  name: 'array',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
