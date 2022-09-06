import { IAlbum, NewAlbum } from './album.model';

export const sampleWithRequiredData: IAlbum = {
  id: 51589,
  title: 'back-end',
  cover: '../fake-data/blob/hipster.png',
  coverContentType: 'unknown',
};

export const sampleWithPartialData: IAlbum = {
  id: 10236,
  title: 'Desarrollador',
  cover: '../fake-data/blob/hipster.png',
  coverContentType: 'unknown',
};

export const sampleWithFullData: IAlbum = {
  id: 277,
  title: 'Mesa',
  cover: '../fake-data/blob/hipster.png',
  coverContentType: 'unknown',
};

export const sampleWithNewData: NewAlbum = {
  title: 'Groenlandia Música',
  cover: '../fake-data/blob/hipster.png',
  coverContentType: 'unknown',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
