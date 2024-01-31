import { Link, useNavigate } from "react-router-dom";
import { IoMdArrowDropright } from "react-icons/io";
import { useState } from "react";

function Navbar() {
  const [moviesMenu, setMoviesMenu] = useState(false);

  const [showsMenu, setShowsMenu] = useState(false);

  const navigate = useNavigate();

  const handleNavigate = (e: any) => {
    const title: string = e.target.title.value;

    e.preventDefault();
    navigate("/search", { state: { title } });
  };

  return (
    <div className="relative w-4/5 h-[100px] mt-2 rounded-3xl bg-zinc-950 flex justify-around">
      <div className="relative flex-col flex text-2xl text-neutral-200 font-abel justify-center p-4 cursor-pointer hover:scale-110 transform transition-transform">
        <div
          className="flex justify-center content-center"
          onMouseEnter={() => setMoviesMenu(true)}
          onMouseLeave={() => setMoviesMenu(false)}
        >
          <p>MOVIES</p>
          <IoMdArrowDropright className="self-center" />
          {moviesMenu ? (
            <div className="absolute top-[9px] left-[103px]  rounded-xl  p-3 flex flex-col text-xl">
              <Link
                to="/topmovies"
                className="flex self-center hover:text-neutral-400 hover:scale-125 transform transition-transform"
              >
                TOP
              </Link>
              <Link
                to="/popmovies"
                className="flex self-center hover:text-neutral-400 hover:scale-125 transform transition-transform"
              >
                POPULAR
              </Link>
            </div>
          ) : (
            ""
          )}
        </div>
      </div>
      <div className="flex-col flex text-2xl text-neutral-200 font-abel justify-center p-4 cursor-pointer hover:scale-110 transform transition-transform">
        <div
          className="flex justify-center content-center"
          onMouseEnter={() => setShowsMenu(true)}
          onMouseLeave={() => setShowsMenu(false)}
        >
          <p>TV SHOWS</p>
          <IoMdArrowDropright className="self-center" />
          {showsMenu ? (
            <div className="absolute top-[9px] left-[130px]  rounded-xl  p-3 flex flex-col text-xl">
              <Link
                to="/topshows"
                className="flex self-center hover:text-neutral-400 hover:scale-125 transform transition-transform"
              >
                TOP
              </Link>
              <Link
                to="/popshows"
                className="flex self-center hover:text-neutral-400 hover:scale-125 transform transition-transform"
              >
                POPULAR
              </Link>
            </div>
          ) : (
            ""
          )}
        </div>
      </div>
      <div className="flex-col flex text-2xl text-neutral-200 font-abel justify-center p-4 cursor-pointer hover:scale-110 transform transition-transform">
        <div className="flex justify-center content-center ">
          <Link to="/feed">FEED</Link>
          {/* <IoMdArrowDropdown className="self-center"/> */}
        </div>
      </div>
      <div className="flex-col flex text-2xl text-neutral-200 font-abel justify-center p-4 cursor-pointer hover:scale-110 transform transition-transform">
        <div className="flex justify-center content-center ">
          <Link to="/profile">PROFILE</Link>
          {/* <IoMdArrowDropdown className="self-center"/> */}
        </div>
      </div>
      <form
        className="ml-10 mt-7 w-[500px] h-[40px] bg-neutral-800 flex flex-row justify-center rounded-2xl cursor-pointer hover:bg-neutral-700 transition ease-in-out delay-100"
        onSubmit={handleNavigate}
      >
        <input
          className="bg-neutral-800 w-full rounded-2xl text-center text-white"
          placeholder="SEARCH"
          name="title"
        ></input>
      </form>
    </div>
  );
}

export default Navbar;
