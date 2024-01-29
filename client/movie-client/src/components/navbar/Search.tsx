import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import SearchCard from "../card/SearchCard";

interface ILength {
  hour: number;
  min: number;
}

interface ISearchCard {
  certificate: string;
  genre: string[];
  id: string;
  length: ILength;
  plot: string;
  rank: number;
  rating: number;
  title: string;
  title_type: string;
  url: string;
  year_end: number;
  year_start: number;
}

function Search() {
  const { state } = useLocation();
  console.log(state);

  const [titleData, setTitleData] = useState<ISearchCard[] | null>(null);

  const getDataFromTitle = async () => {
    const title: string = state.title;
    const titleFormated: string = title.replaceAll(" ", "%20");

    const response = await fetch(
      `http://127.0.0.1:5000/search?q=${titleFormated}`,
    );
    const data = await response.json();
    setTitleData(data);
    console.log(data);
  };

  useEffect(() => {
    getDataFromTitle();
  }, [state]);

  return (
    <>
      {titleData
        ? titleData.map((item) => <SearchCard titleInfo={item} key={item.id} />)
        : ""}
    </>
  );
}

export default Search;
