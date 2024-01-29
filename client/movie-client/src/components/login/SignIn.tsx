import { Link } from "react-router-dom";

function SignIn() {
  return (
    <>
      <div className="flex bg-gradient-to-b from-gray-950 to-slate-800 h-dvh content-center justify-center ">
        <div className="flex flex-col border-2 w-4/12 h-5/6 rounded-3xl border-white items-center self-center">
          <div className="flex text-5xl text-slate-100 font-abel mt-28">
            Login
          </div>
          <div className="flex w-1/2 h-20 mt-20 flex-col">
            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
              Username
            </label>
            <input
              type="text"
              className="border border-gray-300 text-white text-sm rounded-lg bg-gray-800 hover:bg-gray- p-2.5 "
              placeholder="John"
              required
            />
          </div>
          <div className="flex w-1/2 h-20 mt-14 flex-col">
            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
              Password
            </label>
            <input
              type="password"
              className="border border-gray-300 text-white text-sm rounded-lg bg-gray-800 hover:bg-gray- p-2.5 "
              placeholder="*********"
              required
            />
          </div>
          <button className="flex w-1/2 h-20 mt-24 border-[1px] rounded-full justify-center items-center text-3xl font-semibold text-white bg-sky-400 bg-opacity-30 hover:bg-opacity-20">
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

