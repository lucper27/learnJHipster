import dayjs from 'dayjs/esm';
import { IAlbum } from 'app/entities/album/album.model';

export interface ISong {
  id: number;
  title?: string | null;
  duration?: number | null;
  inclusionDate?: dayjs.Dayjs | null;
  album?: Pick<IAlbum, 'id' | 'title'> | null;
}

export type NewSong = Omit<ISong, 'id'> & { id: null };
