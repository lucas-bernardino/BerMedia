import { FaClock, FaCommentAlt, FaStar } from "react-icons/fa";
import { IoInformationCircle } from "react-icons/io5";
import { PiCalendarFill } from "react-icons/pi";
import { IoCloseCircle } from "react-icons/io5";
import { IoBookmarkSharp } from "react-icons/io5";
import { IComment, IMedia } from "../../utils/interfaces";
import { BiSolidCameraMovie } from "react-icons/bi";

import {
  getAuthenticatedUser,
  getTokenLocalStorage,
} from "../../utils/helpers";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

interface IProps {
  media: IMedia;
}

function ShowCard({ media }: IProps) {
  const navigate = useNavigate();

  const [showComments, setShowComments] = useState<boolean>(false);
  const [hasFetchedComments, setHasFetchedComments] = useState<boolean>(false);

  const [mediaComments, setMediaComments] = useState<IComment[] | null>();
  const [mediaCommentsNumber, setMediaCommentsNumber] = useState<number>(0);

  const getNumberOfComments = async () => {
    const responseNumber = await fetch(
      `http://localhost:8080/comment/total/${media.imdbId}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      },
    );
    const numberComments = await responseNumber.json();
    setMediaCommentsNumber(numberComments);
  };

  const getCommentsDataFromMedia = async () => {
    const responseComments = await fetch(
      `http://localhost:8080/comment/${media.imdbId}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      },
    );

    const comments = await responseComments.json();
    setMediaComments(comments);
  };

  useEffect(() => {
    if (showComments && !hasFetchedComments) {
      getCommentsDataFromMedia();
      setHasFetchedComments(true);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [showComments, hasFetchedComments]);

  const HandleSave = async () => {
    const user = await getAuthenticatedUser();
    if (!user) {
      navigate("/signin");
      return;
    }
    const token = getTokenLocalStorage();
    if (!token) {
      navigate("/signin");
    }

    const flagContinue = await unsaveMedia({ media }, token as string);

    if (flagContinue) {
      window.location.reload();
      return;
    }

    await handleMediaCreation({ media }, token as string);

    await fetch("http://localhost:8080/media/newuser", {
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
  };

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const HandleComment = async (e: any) => {
    e.preventDefault();
    const user = await getAuthenticatedUser();
    if (!user) {
      navigate("/signin");
      return;
    }
    const token = getTokenLocalStorage();
    if (!token) {
      navigate("/signin");
    }

    setShowComments(!showComments);

    await handleMediaCreation({ media }, token as string);

    const userComment = e.target.comment.value as string;
    if (userComment.length < 2) {
      alert("Your comment must have more than three characters");
      return;
    }

    await fetch(`http://localhost:8080/comment/${media.imdbId}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token as string}`,
      },
      body: JSON.stringify({
        userComment: userComment,
      }),
    });
    mediaComments
      ? setMediaComments((prev) => [
          ...prev!,
          { username: user.username, userComment: userComment },
        ])
      : setMediaComments([
          { username: user.username, userComment: userComment },
        ]);
  };

  return (
    <div
      className="group p-3 ml-10 w-[400px] h-[400px] flex content-center justify-center mt-10 hover:bg-slate-950 hover:rounded-[40px] hover:h-[700px] ease-in-out duration-300"
      onMouseEnter={getNumberOfComments}
    >
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
            <div className="flex items-center gap-2">
              <FaCommentAlt
                className="size-4 hover:scale-125 ease-in duration-75"
                onClick={() => setShowComments(!showComments)}
              />
              <p>{mediaCommentsNumber ?? mediaCommentsNumber}</p>
              {showComments ? (
                <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 z-50 flex justify-center items-center">
                  <div className="flex flex-col absolute border border-white rounded-xl bg-sky-950/90 p-4 w-1/3">
                    <IoCloseCircle
                      className="absolute right-2 top-1 size-5 hover:scale-125 ease-in duration-75 text-white"
                      onClick={() => setShowComments(!showComments)}
                    />
                    {mediaComments?.map((item, i) => (
                      <div
                        className="flex flex-col mb-2 bg-sky-800/90 rounded-xl p-2"
                        key={i}
                      >
                        <div className="flex gap-9">
                          <div className="font-bold">{item.username}</div>
                          <div className="font-light">
                            {item.createdOn?.slice(8, 10)}/
                            {item.createdOn?.slice(5, 7)} -{" "}
                            {item.createdOn?.slice(11, 16)}
                          </div>
                        </div>
                        <div className="text-sky-50 font-sans">
                          {item.userComment}
                        </div>
                      </div>
                    ))}
                    <form
                      className="flex gap-3 justify-start items-center mt-5"
                      onSubmit={HandleComment}
                    >
                      <label className="font-semibold text-white">
                        New Comment
                      </label>
                      <input
                        className="p-1 bg-sky-700/90 rounded-md text-white"
                        type="text"
                        placeholder="Add a new comment"
                        name="comment"
                      />
                    </form>
                  </div>
                </div>
              ) : (
                ""
              )}
            </div>
            <div>
              <IoBookmarkSharp
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

// This function is responsible for unsaving the media if the user clicks in the save button and the media is already saved.
// Returns a boolean: false if it was necessary to unsave, or true if it was necessary.
const unsaveMedia = async (
  { media }: IProps,
  token: string,
): Promise<boolean> => {
  const getMediaByToken = await fetch("http://localhost:8080/media", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token as string}`,
    },
  });

  let noMoreOperations: boolean = false;

  const mediasArray: IMedia[] = await getMediaByToken.json();
  await Promise.all(
    // need to use Promise.All because it needs to wait for all the promises of the array.
    mediasArray.map(async (item: IMedia) => {
      if (item.imdbId === media.imdbId) {
        await fetch(`http://localhost:8080/media/${media.imdbId}`, {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token as string}`,
          },
        });
        noMoreOperations = true;
      }
    }),
  );
  return noMoreOperations;
};

// This function is responsible for checking if the media exists in the database. If it doesn't exist, it will create it.
const handleMediaCreation = async (
  { media }: IProps,
  token: string,
): Promise<void> => {
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
        comments: media.comments,
      }),
    });
  }
};

export default ShowCard;
