import { useEffect, useState } from "react";
import Card from "../../components/card/Card";
import { IMedia } from "../../utils/interfaces";

interface IProps {
  type: string;
}

function Shows({ type }: IProps) {
  const [shows, setShows] = useState<IMedia[] | null>();

  const getShows = async (opt: string) => {
    try {
      const response = await fetch(`http://127.0.0.1:5000/${opt}shows`);
      const data = await response.json();
      setShows(data);
    } catch (error) {
      setShows(null);
    }
  };

  useEffect(() => {
    getShows(type);
  }, [type]);

  return (
    <>
      {shows
        ? shows.map((show) => <Card media={show} key={show.imdbId} />)
        : ""}
    </>
  );
}

export default Shows;
