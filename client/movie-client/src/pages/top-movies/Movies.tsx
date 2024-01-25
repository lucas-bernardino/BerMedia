import { useEffect, useState } from "react";
import MovieCard from "../../components/card/MovieCard";

interface ILength {
  hour: number,
  min: number
}

interface IMovie {
  id: string,
  url: string,
  title: string,
  rank: number,
  plot: string,
  genre: string,
  rating: number,
  year: number,
  length: ILength
}

interface IProps {
  type: string
}

function Movies({ type }: IProps) {
  const [movies, setMovies] = useState<IMovie[] | null>();

  const getMovies = async (opt: string) => {
    try {
      const response = await fetch(`http://127.0.0.1:5000/${opt}movies`);
      const data = await response.json();
      setMovies(data);
      console.log(data);
    } catch (error) {
      setMovies(null);
    }
  };

  useEffect(() => {
    getMovies(type);
  }, [type]);

  return (
    <>
      {movies ? (
        movies.map((movie) => (
          <MovieCard movie={movie} key={movie.id}/>
          ))) : "" }
    </>
  );
}

export default Movies;
