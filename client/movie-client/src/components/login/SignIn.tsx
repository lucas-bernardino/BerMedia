import { Link, useNavigate } from "react-router-dom";
import { useUser } from "../../utils/useUserHook";
import { useState } from "react";
import { setTokenLocalStorage } from "../../utils/helpers";
import { IException, IForm } from "../../utils/interfaces";

interface IToken {
  token: string;
}

function SignIn() {
  const navigate = useNavigate();
  const user = useUser();
  if (user) {
    navigate("/");
  }

  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  const [messageFlag, setMessageFlag] = useState<boolean>(false);
  const [message, setMessage] = useState<string>("");

  const signIn = async (e: any) => {
    e.preventDefault();
    const userForm: IForm = { username, password };
    const response = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userForm),
    });

    const status = response.status;
    const responseJson: IException | IToken = await response.json();

    if (status == 200) {
      const token = (responseJson as IToken).token;
      setTokenLocalStorage(token);
      navigate("/");
    }
    if (status == 400 || status == 403) {
      const exceptionMessage = (responseJson as IException).message;
      setMessageFlag(true);
      setMessage(exceptionMessage);
      setTimeout(() => {
        setMessageFlag(false);
      }, 2000);
    }
  };

  return (
    <>
      <div className="flex bg-gradient-to-b from-gray-950 to-slate-800 h-dvh content-center justify-center ">
        <div className="flex flex-col border-2 w-4/12 h-5/6 rounded-3xl border-white items-center self-center">
          {messageFlag ? (
            <div className="text-slate-50 font-bold fixed w-[270px] h-[70px] border border-black bg-red-700 rounded-3xl text-center mt-3 flex items-center justify-center">
              {message ?? message}
            </div>
          ) : (
            ""
          )}
          <div className="flex text-5xl text-slate-100 font-abel mt-28">
            Login
          </div>
          <form
            className="flex w-1/2 h-20 mt-20 flex-col"
            onChange={(e: any) => setUsername(e.target.value)}
          >
            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
              Username
            </label>
            <input
              type="text"
              className="border border-gray-300 text-white text-sm rounded-lg bg-gray-800 hover:bg-gray- p-2.5 "
              placeholder="John"
              required
            />
          </form>
          <form
            className="flex w-1/2 h-20 mt-14 flex-col"
            onChange={(e: any) => setPassword(e.target.value)}
            onSubmit={signIn}
          >
            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
              Password
            </label>
            <input
              type="password"
              className="border border-gray-300 text-white text-sm rounded-lg bg-gray-800 hover:bg-gray- p-2.5 "
              placeholder="*********"
              required
            />
          </form>
          <button
            className="flex w-1/2 h-20 mt-24 border-[1px] rounded-full justify-center items-center text-3xl font-semibold text-white bg-sky-400 bg-opacity-30 hover:bg-opacity-20"
            onClick={signIn}
          >
            LOGIN
          </button>
          <div className="flex w-fit h-20 mt-14 flex-col ml-72">
            <p className="text-white font-semibold">
              Don't have an account yet?
            </p>
            <div className="flex h-20 justify-end">
              <Link
                to="/signup"
                className="text-white font-semibold mt-3 text-xl hover:text-gray-400 hover:cursor-pointer "
              >
                SIGN UP
              </Link>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default SignIn;
