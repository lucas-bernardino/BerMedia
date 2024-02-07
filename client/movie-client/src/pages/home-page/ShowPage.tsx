import Navbar from "../../components/navbar/Navbar";
import Shows from "../top-shows/Shows";

interface IProps {
  type: string;
}

function ShowPage({ type }: IProps) {
  return (
    <>
      <div className="sticky top-0 z-50 flex justify-center bg-gradient-to-r from-slate-900 via-slate-700 to-slate-900">
        <Navbar />
      </div>
      <div className="relative bg-gradient-to-r from-slate-900 via-slate-700 to-slate-900 flex-wrap flex content-center justify-center min-h-screen">
        <Shows type={type} />
      </div>
    </>
  );
}

export default ShowPage;
