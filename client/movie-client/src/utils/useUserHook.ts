import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { getAuthenticatedUser } from "./helpers";

import { IUser } from "./interfaces";

export function useUser(): IUser | null {
  const [user, setUser] = useState<IUser | null>(null);
  const navigate = useNavigate();
  const location = useLocation();

  const getUserDetails = async () => {
    const userDetails = await getAuthenticatedUser();

    if (userDetails == null) {
      if (location.pathname != "/signin") {
        navigate("http://localhost:8080/signin");
      }
      return;
    }

    setUser(userDetails);
  };

  useEffect(() => {
    getUserDetails();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return user;
}
