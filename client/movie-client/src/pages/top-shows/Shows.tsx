import { useEffect, useState } from "react";
import ShowCard from "../../components/card/ShowCard";


interface IShow {
  id: string,
  url: string,
  title: string,
  rank: number,
  plot: string,
  genre: string,
  rating: number,
  year_start: number,
  year_end: number,
}

interface IProps {
  type: string  
}

function Shows({ type }: IProps) {
  const [shows, setShows] = useState<IShow[] | null>();

  const getShows = async (opt: string) => {
    try {
      const response = await fetch(`http://127.0.0.1:5000/${opt}shows`);
      const data = await response.json();
      setShows(data);
      console.log(data);
    } catch (error) {
        setShows(null);
    }
  };

  useEffect(() => {
    getShows(type);
  }, [type]);

  return (
    <>
      {shows ? (
        shows.map((show) => (
          <ShowCard show={show} key={show.id}/>
          ))) : "" }
    </>
  );
}

export default Shows;
