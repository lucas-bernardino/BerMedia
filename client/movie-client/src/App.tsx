import { Route, Routes } from "react-router-dom";
import "./App.css";
import MoviePage from "./pages/home-page/MoviePage";
import ShowPage from "./pages/home-page/ShowPage";

function App() {


  return (
    <>
      <Routes>
        <Route path="/" element={<MoviePage type="top"/>}/>
        <Route path="/topshows" element={<ShowPage type="top"/>}/>
        <Route path="/popshows" element={<ShowPage type="pop"/>}/>
        <Route path="/topmovies" element={<MoviePage type="top"/>}/>
        <Route path="/popmovies" element={<MoviePage type="pop"/>}/>
      </Routes>
    </>
  );
}

export default App;
