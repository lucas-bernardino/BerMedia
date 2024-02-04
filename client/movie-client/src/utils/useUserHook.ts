import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { getAuthenticatedUser } from "./helpers";

export function useUser(): IUser | null {
  const [user, setUser] = useState<IUser | null>(null);
  const navigate = useNavigate();
  const location = useLocation();

  const getUserDetails = async () => {
    const user = await getAuthenticatedUser();

    if (user == null) {
      if (location.pathname != "/signin") {
        navigate("http://localhost:8080/signin");
      }
      return;
    }

    setUser(user);
  };

  useEffect(() => {
    getUserDetails();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return user;
}

interface IUser {
  id: number;
  username: string;
  password: string;
  medias: IMedia[];
  role: string;
}

interface IMedia {
  id: number;
  title: string;
  imdbId: string;
  plot: string;
  pictureUrl: string;
  certificate: string;
  genre: string[];
  length: ILength;
  rank: string;
  ranking: string;
  titleType: string;
  yearStart: number;
  yearEnd: number;
}

interface ILength {
  hour: number;
  min: number;
}
