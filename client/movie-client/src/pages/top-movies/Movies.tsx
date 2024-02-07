import { useEffect, useState } from "react";
import Card from "../../components/card/Card";
import { IMedia } from "../../utils/interfaces";

interface IProps {
  type: string;
}

function Movies({ type }: IProps) {
  const [movies, setMovies] = useState<IMedia[] | null>();

  const getMovies = async (opt: string) => {
    try {
      const response = await fetch(`http://127.0.0.1:5000/${opt}movies`);
      const data = await response.json();
      setMovies(data);
    } catch (error) {
      setMovies(null);
    }
  };

  useEffect(() => {
    getMovies(type);
  }, [type]);

  return (
    <>
      {movies
        ? movies.map((movie) => <Card media={movie} key={movie.imdbId} />)
        : ""}
    </>
  );
}

export default Movies;
