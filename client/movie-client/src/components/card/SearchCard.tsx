import { FaCommentAlt, FaStar } from "react-icons/fa";
import { IoInformationCircle } from "react-icons/io5";
import { PiCalendarFill } from "react-icons/pi";
import { BiSolidCameraMovie } from "react-icons/bi";
import { FaClock } from "react-icons/fa";
import { AiFillLike } from "react-icons/ai";
import { RiSaveFill } from "react-icons/ri";

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

interface IProps {
  titleInfo: ISearchCard;
}

function SearchCard({ titleInfo: titleInfo }: IProps) {
  return (
    <div className="group p-3 ml-10 w-[400px] h-[400px] flex content-center justify-center mt-10 hover:bg-slate-950 hover:rounded-[40px] hover:h-[700px] ease-in-out duration-300">
      <div className="m-3 p-4 bg-white rounded-xl flex-col justify-start items-center gap-4 inline-flex">
        <img
          className="w-[190px] h-[270px] relative bg-neutral-200 rounded-xl object-cover"
          src={titleInfo.url}
        ></img>
        <div className="flex-col justify-start items-start gap-4 flex">
          <div className="justify-start items-start gap-6 inline-flex">
            <div className="w-32 text-black text-lg font-medium font-['Source Code Pro']">
              {titleInfo.title}
            </div>
            <div className="w-32 text-right text-black text-base font-semibold font-['Source Code Pro'] ">
              {titleInfo.certificate}
            </div>
          </div>
          <div className="w-[280px] text-stone-500 text-sm font-normal font-['Source Code Pro'] opacity-0 group-hover:opacity-100">
            {titleInfo.plot}
          </div>
          <div className="justify-between items-center gap-4  opacity-0 group-hover:opacity-100">
            <div className="justify-start items-center gap-1.5 flex">
              <div className="w-4 h-4 bg-neutral-200 rounded-full">
                <FaStar />
              </div>
              <div className="text-stone-500 text-sm font-normal font-['Source Code Pro']">
                {titleInfo.rating}
              </div>
            </div>
            <div className="justify-start items-center gap-1.5 flex">
              <div className="w-4 h-4 bg-neutral-200 rounded-full">
                <IoInformationCircle />{" "}
              </div>
              <div className="text-stone-500 text-sm font-normal font-['Source Code Pro']">
                {titleInfo.genre}
              </div>
            </div>
            <div className="justify-start items-center gap-1.5 flex">
              <div className="w-4 h-4 bg-neutral-200 rounded-full">
                <PiCalendarFill />{" "}
              </div>
              <div className="text-stone-500 text-sm font-normal font-['Source Code Pro']">
                {titleInfo.year_start} {titleInfo.year_end == 0 ? "" : "-"}{" "}
                {titleInfo.year_end <= 0 ? "" : titleInfo.year_end}
              </div>
            </div>
            <div className="justify-start items-center gap-1.5 flex">
              <div className="w-4 h-4 bg-neutral-200 rounded-full">
                <BiSolidCameraMovie />{" "}
              </div>
              <div className="text-stone-500 text-sm font-normal font-['Source Code Pro']">
                {titleInfo.title_type.charAt(0).toUpperCase() +
                  titleInfo.title_type.slice(1)}
              </div>
            </div>
            <div className="justify-start items-center gap-1.5 flex">
              <div className="w-4 h-4 bg-neutral-200 rounded-full">
                <FaClock />{" "}
              </div>
              <div className="text-stone-500 text-sm font-normal font-['Source Code Pro']">
                {titleInfo.length.hour}h : {titleInfo.length.min}min
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

export default SearchCard;
