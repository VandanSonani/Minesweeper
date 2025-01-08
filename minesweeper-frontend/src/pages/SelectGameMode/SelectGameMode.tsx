import {FC} from "react";
import {useNavigate} from "react-router-dom";
import './SelectGameMode.css';

const SelectGameMode: FC = () => {
    const navigate = useNavigate();

    return (
        <div>
            <h1>Select Game Mode</h1>
            <div className={"holder"}>
                <button className={"home"} onClick={() => navigate("/")}>Home</button>
                <button className={"campaign"} onClick={() => navigate("/campaign")}>Campaign</button>
                <button className={"practice"} onClick={() => navigate("/practice")}>Practice</button>
                <button className={"dailysweep"} onClick={() => navigate("/dailysweep")}>DailySweep</button>
            </div>

        </div>
    );
};

export default SelectGameMode;