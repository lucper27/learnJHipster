export interface IArtist {
  id: number;
  name?: string | null;
  image?: string | null;
  imageContentType?: string | null;
}

export type NewArtist = Omit<IArtist, 'id'> & { id: null };
