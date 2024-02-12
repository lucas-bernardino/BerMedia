import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

interface IForm {
  username: string;
  password: string;
}

function SignUp() {
  const navigate = useNavigate();

  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [checkPassword, setCheckPassword] = useState<string>("");

  const [success, setSuccess] = useState<boolean>(false);
  const [error, setError] = useState<boolean>(false);
  const [message, setMessage] = useState<boolean>(false);

  const signUp = async (e: any) => {
    e.preventDefault();
    if (checkPassword !== password) {
      setMessage(true);
      setTimeout(() => {
        setMessage(false);
      }, 2000);
      return;
    }
    const userForm: IForm = { username, password };
    const response = await fetch("http://localhost:8080/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userForm),
    });
    const text = response.status;
    if (text == 200) {
      setSuccess(true);
      setTimeout(() => {
        setSuccess(false);
        navigate("/signin");
      }, 2000);
    }
    if (text == 400) {
      setError(true);
      setTimeout(() => {
        setError(false);
      }, 2000);
    }
  };

  return (
    <>
      <div className="flex bg-gradient-to-b from-gray-950 to-slate-800 h-dvh content-center justify-center ">
        <div className="absolute flex flex-col border-2 w-4/12 h-5/6 rounded-3xl border-white items-center self-center">
          {success ? (
            <div className="text-slate-50 font-bold fixed w-[270px] h-[70px] border border-black bg-green-700 rounded-3xl text-center mt-3 flex items-center justify-center">
              USER SUCCESSFULLY REGISTERED
            </div>
          ) : (
            ""
          )}
          {error ? (
            <div className="text-slate-50 font-bold fixed w-[270px] h-[70px] border border-black bg-red-700 rounded-3xl text-center mt-3 flex items-center justify-center">
              USERNAME ALREADY USED
            </div>
          ) : (
            ""
          )}
          {message ? (
            <div className="text-slate-50 font-bold fixed w-[270px] h-[70px] border border-black bg-red-700 rounded-3xl text-center mt-3 flex items-center justify-center">
              PASSWORDS MUST BE EQUAL
            </div>
          ) : (
            ""
          )}
          <div className="flex text-5xl text-slate-100 font-abel mt-24">
            Register
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
              name="username"
              required
            />
          </form>
          <form
            className="flex w-1/2 h-20 mt-14 flex-col"
            onChange={(e: any) => setPassword(e.target.value)}
          >
            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
              Password
            </label>
            <input
              type="password"
              className="border border-gray-300 text-white text-sm rounded-lg bg-gray-800 hover:bg-gray- p-2.5 "
              placeholder="*********"
              name="password"
              required
            />
          </form>
          <form
            className="flex w-1/2 h-20 mt-14 flex-col"
            onChange={(e: any) => setCheckPassword(e.target.value)}
            onSubmit={signUp}
          >
            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
              Confirm Password
            </label>
            <input
              type="password"
              className="border border-gray-300 text-white text-sm rounded-lg bg-gray-800 hover:bg-gray- p-2.5 "
              placeholder="*********"
              required
            />
          </form>
          <button
            className="flex w-1/2 h-20 mt-24 border-[1px] p-2 rounded-full justify-center items-center text-3xl font-semibold text-white bg-sky-400 bg-opacity-30 hover:bg-opacity-20"
            onClick={signUp}
          >
            REGISTER
          </button>
          <div className="flex w-fit h-20 mt-4 flex-col ml-72">
            <p className="text-white font-semibold">Already have an account?</p>
            <div className="flex h-20 justify-end">
              <Link
                to="/signin"
                className="text-white font-semibold mt-3 text-xl hover:text-gray-400 hover:cursor-pointer "
              >
                SIGN IN
              </Link>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default SignUp;
