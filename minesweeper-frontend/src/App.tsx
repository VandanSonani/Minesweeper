import {BrowserRouter, Route, Routes,} from "react-router-dom";

import './App.css'
import DailySweep from "./pages/GamePlay/DailySweep/DailySweep.tsx";
import PracticeMode from "./pages/GamePlay/PracticeMode/PracticeMode.tsx";
import RankedMode from "./pages/GamePlay/RankedMode/RankedMode.tsx";
import SelectGameMode from "./pages/SelectGameMode/SelectGameMode.tsx";
import {useEffect, useState} from "react";
import Login from "./components/oauth/Login.tsx";
import {jwtDecode} from "jwt-decode";


declare const google: any;

function App() {

    const [user, setUser] = useState({});

    const handleCallbackResponse = (response: any) => {
        console.log(response);
        const userObject = jwtDecode(response.credential);
        console.log("userObject", userObject);
        setUser(userObject);
        const signInDiv = document.getElementById("signInDiv");
        if (signInDiv) {
            signInDiv.hidden = true;
        }
    }

    const handleSignOut = () => {
        setUser({});
        const signInDiv = document.getElementById("signInDiv");
        if (signInDiv) {
            signInDiv.hidden = false;
        }
    }

    useEffect(() => {
        /* global google */
        google.accounts.id.initialize({
            client_id: "1043948039298-32l7ito98ck7hv8bfisjjr5cgv9iumpp.apps.googleusercontent.com",
            callback: handleCallbackResponse
        });

        google.accounts.id.renderButton(
            document.getElementById("signInDiv"),
            {theme: "outline", size: "large"}
        );
    }, [])

    // if we have no user, then show sign in button
    // if we have user then show log out button


    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<SelectGameMode/>}/>
                <Route path="/dailysweep" element={<DailySweep/>}/>
                <Route path="/ranked" element={<RankedMode/>}/>
                <Route path="/practice" element={<PracticeMode/>}/>
                <Route path={"/selectgamemode"} element={<SelectGameMode/>}/>
                <Route path="/login" element={<Login handleSignOut={handleSignOut} user={user}/>}/>
            </Routes>
        </BrowserRouter>
    )
}

export default App
