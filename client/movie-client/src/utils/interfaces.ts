export interface IMedia {
  title: string;
  imdbId: string;
  plot: string;
  pictureUrl: string;
  certificate: string;
  genre: string[];
  length: number;
  score: number;
  rank: number;
  titleType: string;
  yearStart: number;
  yearEnd: number;
  users: IUser[];
}

export interface IUser {
  id: number;
  username: string;
  password: string;
  medias: IMedia[];
  role: string;
}
