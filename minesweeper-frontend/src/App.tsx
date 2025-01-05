import {
  BrowserRouter,
  Route,
  Routes,
} from "react-router-dom";

import './App.css'
import TestPage from "./pages/test/TestPage.tsx";
import DailySweep from "./pages/GamePlay/DailySweep/DailySweep.tsx";
import PracticeMode from "./pages/GamePlay/PracticeMode/PracticeMode.tsx";
import CampaignMode from "./pages/GamePlay/CampaignMode/CampaignMode.tsx";
import SelectGameMode from "./pages/SelectGameMode/SelectGameMode.tsx";

function App() {

  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<TestPage />}/>
          <Route path="/dailysweep" element={<DailySweep />}/>
            <Route path="/campaign" element={<CampaignMode/>}/>
            <Route path="/practice" element={<PracticeMode/>}/>
            <Route path={"/selectgamemode"} element={<SelectGameMode/>}/>
        </Routes>
      </BrowserRouter>
  )
}

export default App
