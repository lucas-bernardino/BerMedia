import { Route, Routes } from "react-router-dom";
import "./App.css";
import MoviePage from "./pages/home-page/MoviePage";
import ShowPage from "./pages/home-page/ShowPage";
import SignIn from "./components/login/SignIn";
import SignUp from "./components/login/SignUp";
import SearchPage from "./pages/home-page/SearchPage";
import Profile from "./pages/home-page/Profile";

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<MoviePage type="top" />} />
        <Route path="/signin" element={<SignIn />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/topshows" element={<ShowPage type="top" />} />
        <Route path="/popshows" element={<ShowPage type="pop" />} />
        <Route path="/topmovies" element={<MoviePage type="top" />} />
        <Route path="/popmovies" element={<MoviePage type="pop" />} />
        <Route path="/search" element={<SearchPage />} />
        <Route path="/profile" element={<Profile />} />
      </Routes>
    </>
  );
}

export default App;
