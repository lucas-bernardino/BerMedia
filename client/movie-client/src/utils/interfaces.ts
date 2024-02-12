export interface IMedia {
  id: number;
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
  comments: IComment[];
}

export interface IUser {
  id: number;
  username: string;
  password: string;
  medias: IMedia[];
  role: string;
}

export interface IComment {
  username: string;
  userComment: string;
}
