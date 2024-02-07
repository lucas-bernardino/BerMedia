import { FaStar } from "react-icons/fa";
import { PiCalendarFill } from "react-icons/pi";
import { IoInformationCircle } from "react-icons/io5";
import { AiFillLike } from "react-icons/ai";
import { FaCommentAlt } from "react-icons/fa";
import { RiSaveFill } from "react-icons/ri";

interface ILength {
  hour: number;
  min: number;
}

interface IMovie {
  id: string;
  url: string;
  title: string;
  rank: number;
  plot: string;
  genre: string;
  rating: number;
  year: number;
  length: ILength;
}

interface IProps {
  movie: IMovie;
}

function MovieCard({ movie }: IProps) {
  return (
    <div className="group p-3 ml-10 w-[400px] h-[400px] flex content-center justify-center mt-10 hover:bg-slate-950 hover:rounded-[40px] hover:h-[700px] ease-in-out duration-300">
      <div className="m-3 p-4 bg-white rounded-xl flex-col justify-start items-center gap-4 inline-flex">
        <img
          className="w-[190px] h-[270px] relative bg-neutral-200 rounded-xl object-cover"
          src={movie.url}
        ></img>
        <div className="flex-col justify-start items-start gap-4 flex">
          <div className="justify-start items-start gap-6 inline-flex">
            <div className="w-32 text-black text-lg font-medium font-['Source Code Pro']">
              {movie.title}
            </div>
            <div className="w-32 text-right text-black text-base font-semibold font-['Source Code Pro'] ">
              {movie.rank}
            </div>
          </div>
          <div className="w-[280px] text-stone-500 text-sm font-normal font-['Source Code Pro'] opacity-0 group-hover:opacity-100">
            {movie.plot}
          </div>
          <div className="justify-start items-start gap-4 inline-flex opacity-0 group-hover:opacity-100">
            <div className="justify-start items-center gap-1.5 flex">
              <div className="w-4 h-4 bg-neutral-200 rounded-full">
                <FaStar />
              </div>
              <div className="text-stone-500 text-sm font-normal font-['Source Code Pro']">
                {movie.rating}
              </div>
            </div>
            <div className="justify-start items-center gap-1.5 flex">
              <div className="w-4 h-4 bg-neutral-200 rounded-full">
                <IoInformationCircle />
              </div>
              <div className="text-stone-500 text-sm font-normal font-['Source Code Pro']">
                {movie.genre}
              </div>
            </div>
            <div className="justify-start items-center gap-1.5 flex">
              <div className="w-4 h-4 bg-neutral-200 rounded-full">
                <PiCalendarFill />
              </div>
              <div className="text-stone-500 text-sm font-normal font-['Source Code Pro']">
                {movie.year}
              </div>
            </div>
          </div>
          <div className="flex w-full items-center justify-around opacity-0 group-hover:opacity-100">
            <div>
              <AiFillLike className="size-5 hover:scale-125 ease-in duration-75" />
            </div>
            <div>
              <FaCommentAlt className="size-4 hover:scale-125 ease-in duration-75" />
            </div>
            <div>
              <RiSaveFill className="size-6 hover:scale-125 ease-in duration-75" />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default MovieCard;
