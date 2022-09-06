export interface IStyle {
  id: number;
  name?: string | null;
}

export type NewStyle = Omit<IStyle, 'id'> & { id: null };
