import {
  BrowserRouter,
  Route,
  Routes,
} from "react-router-dom";

import './App.css'
import TestPage from "./pages/test/TestPage.tsx";

function App() {

  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<TestPage />}>

          </Route>
        </Routes>
      </BrowserRouter>
  )
}

export default App
