import { useEffect, useState } from "react";
import { getAuthenticatedUser } from "../../utils/helpers";
import { useNavigate } from "react-router-dom";
import { IUser } from "../../utils/interfaces";
import Card from "../../components/card/Card";
import Navbar from "../../components/navbar/Navbar";

function Profile() {
  const navigate = useNavigate();

  const [user, setUser] = useState<IUser | null>();

  const isAuthenticated = async () => {
    const userDetails = await getAuthenticatedUser();
    if (!userDetails) {
      navigate("/signin");
      return;
    }
    setUser(userDetails);
  };

  useEffect(() => {
    isAuthenticated();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  console.log(user?.medias);

  return (
    <>
      <div className="sticky top-0 z-50 flex justify-center bg-gradient-to-r from-slate-900 via-slate-700 to-slate-900">
        <Navbar />
      </div>
      <div>
        {user ? (
          <div className="relative bg-gradient-to-r from-slate-900 via-slate-700 to-slate-900 flex-wrap flex content-center justify-center min-h-screen">
            {user.medias.map((media) => (
              <Card media={media} />
            ))}
          </div>
        ) : (
          ""
        )}
      </div>
    </>
  );
}

export default Profile;

// const user = await getAuthenticatedUser();
// if (!user) {
//   navigate("/signin");
//   return;
// }
