import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import Card from "../card/Card";

import { IMedia } from "../../utils/interfaces";

function Search() {
  const { state } = useLocation();

  const [titleData, setTitleData] = useState<IMedia[] | null>(null);

  const getDataFromTitle = async () => {
    const title: string = state.title;
    const titleFormated: string = title.replaceAll(" ", "%20");

    const response = await fetch(
      `http://127.0.0.1:5000/search?q=${titleFormated}`,
    );
    const data = await response.json();
    setTitleData(data);
  };

  useEffect(() => {
    getDataFromTitle();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [state]);

  return (
    <>
      {titleData
        ? titleData.map((item) => <Card media={item} key={item.imdbId} />)
        : ""}
    </>
  );
}

export default Search;
