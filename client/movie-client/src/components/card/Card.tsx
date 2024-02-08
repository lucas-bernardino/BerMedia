import { AiFillLike } from "react-icons/ai";
import { FaClock, FaCommentAlt, FaStar } from "react-icons/fa";
import { IoInformationCircle } from "react-icons/io5";
import { PiCalendarFill } from "react-icons/pi";
import { RiSaveFill } from "react-icons/ri";

import { IMedia } from "../../utils/interfaces";
import { BiSolidCameraMovie } from "react-icons/bi";

import {
  getAuthenticatedUser,
  getTokenLocalStorage,
} from "../../utils/helpers";
import { useNavigate } from "react-router-dom";

interface IProps {
  media: IMedia;
}

function ShowCard({ media }: IProps) {
  //TODO: If user clicks on like, comment, or save button, check whether or not there's a token associated with this user.
  //if there isn't, navigate to the log in page.
  //if there is and it was a save, grab the username from the token, call the route responsible for appending a media to the user's media data.

  const navigate = useNavigate();

  const HandleSave = async () => {
    const user = await getAuthenticatedUser();
    if (!user) {
      navigate("/signin");
      return;
    }
    const token = getTokenLocalStorage();

    const getMediaResponse = await fetch(
      `http://localhost:8080/media/${media.imdbId}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token as string}`,
        },
      },
    );

    if (getMediaResponse.status === 404) {
      await fetch("http://localhost:8080/media", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token as string}`,
        },
        body: JSON.stringify({
          title: media.title,
          imdbId: media.imdbId,
          plot: media.plot,
          pictureUrl: media.pictureUrl,
          certificate: media.certificate,
          genre: media.genre,
          length: media.length,
          score: media.score,
          rank: media.rank,
          titleType: media.titleType,
          yearStart: media.yearStart,
          yearEnd: media.yearEnd,
          users: media.users,
        }),
      });
    }
    const addUserResponse = await fetch("http://localhost:8080/media/newuser", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token as string}`,
      },
      body: JSON.stringify({
        imdbId: media.imdbId,
        userId: user.id,
      }),
    });
    alert(addUserResponse.status);
  };

  return (
    <div className="group p-3 ml-10 w-[400px] h-[400px] flex content-center justify-center mt-10 hover:bg-slate-950 hover:rounded-[40px] hover:h-[700px] ease-in-out duration-300">
      <div className="m-3 p-4 bg-white rounded-xl flex-col justify-start items-center gap-4 inline-flex">
        <img
          className="w-[190px] h-[270px] relative bg-neutral-200 rounded-xl object-cover"
          src={media.pictureUrl}
        ></img>
        <div className="flex-col justify-start items-start gap-4 flex">
          <div className="justify-start items-start gap-6 inline-flex">
            <div className="w-32 text-black text-lg font-medium font-['Source Code Pro']">
              {media.title}
            </div>
            <div className="w-32 text-right text-black text-base font-semibold font-['Source Code Pro'] ">
              {media.certificate}
            </div>
          </div>
          <div className="w-[280px] text-stone-500 text-sm font-normal font-['Source Code Pro'] opacity-0 group-hover:opacity-100">
            {media.plot}
          </div>
          <div className="justify-between items-center gap-4  opacity-0 group-hover:opacity-100">
            <div className="justify-start items-center gap-1.5 flex">
              <div className="w-4 h-4 bg-neutral-200 rounded-full">
                <FaStar />
              </div>
              <div className="text-stone-500 text-sm font-normal font-['Source Code Pro']">
                {media.score}
              </div>
            </div>
            <div className="justify-start items-center gap-1.5 flex">
              <div className="w-4 h-4 bg-neutral-200 rounded-full">
                <IoInformationCircle />{" "}
              </div>
              <div className="text-stone-500 text-sm font-normal font-['Source Code Pro']">
                {media.genre.map((x, i) =>
                  i === media.genre.length - 1 ? x : x + ", ",
                )}
              </div>
            </div>
            <div className="justify-start items-center gap-1.5 flex">
              <div className="w-4 h-4 bg-neutral-200 rounded-full">
                <PiCalendarFill />{" "}
              </div>
              <div className="text-stone-500 text-sm font-normal font-['Source Code Pro']">
                {media.yearStart} {media.yearEnd == 0 ? "" : "-"}{" "}
                {media.yearEnd <= 0 ? "" : media.yearEnd}
              </div>
            </div>
            <div className="justify-start items-center gap-1.5 flex">
              <div className="w-4 h-4 bg-neutral-200 rounded-full">
                <BiSolidCameraMovie />{" "}
              </div>
              <div className="text-stone-500 text-sm font-normal font-['Source Code Pro']">
                {media.titleType.charAt(0).toUpperCase() +
                  media.titleType.slice(1)}
              </div>
            </div>
            <div className="justify-start items-center gap-1.5 flex">
              <div className="w-4 h-4 bg-neutral-200 rounded-full">
                <FaClock />{" "}
              </div>
              <div className="text-stone-500 text-sm font-normal font-['Source Code Pro']">
                {media.length / 60} min
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
              <RiSaveFill
                className="size-6 hover:scale-125 ease-in duration-75"
                onClick={HandleSave}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ShowCard;
