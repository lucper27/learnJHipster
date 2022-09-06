import { IArtist } from 'app/entities/artist/artist.model';
import { IStyle } from 'app/entities/style/style.model';

export interface IAlbum {
  id: number;
  title?: string | null;
  cover?: string | null;
  coverContentType?: string | null;
  artist?: Pick<IArtist, 'id' | 'name'> | null;
  style?: Pick<IStyle, 'id' | 'name'> | null;
}

export type NewAlbum = Omit<IAlbum, 'id'> & { id: null };
