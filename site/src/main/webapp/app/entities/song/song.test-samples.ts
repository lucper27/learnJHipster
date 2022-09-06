import dayjs from 'dayjs/esm';

import { ISong, NewSong } from './song.model';

export const sampleWithRequiredData: ISong = {
  id: 99772,
  title: 'Bebes',
  duration: 61480,
  inclusionDate: dayjs('2022-09-05'),
};

export const sampleWithPartialData: ISong = {
  id: 69456,
  title: 'Corporativo',
  duration: 64493,
  inclusionDate: dayjs('2022-09-04'),
};

export const sampleWithFullData: ISong = {
  id: 14722,
  title: 'experiences firmware Ingeniero',
  duration: 82359,
  inclusionDate: dayjs('2022-09-05'),
};

export const sampleWithNewData: NewSong = {
  title: 'Violeta',
  duration: 58501,
  inclusionDate: dayjs('2022-09-04'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
