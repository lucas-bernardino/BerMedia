import Navbar from "../../components/navbar/Navbar";
import Search from "../../components/navbar/Search";

function ShowPage() {
  return (
    <>
      <div className="sticky top-0 z-50 flex justify-center bg-gradient-to-r from-slate-900 via-slate-700 to-slate-900">
        <Navbar />
      </div>
      <div className="relative bg-gradient-to-r from-slate-900 via-slate-700 to-slate-900 flex-wrap flex content-center justify-center min-h-screen">
        <Search />
      </div>
    </>
  );
}

export default ShowPage;
