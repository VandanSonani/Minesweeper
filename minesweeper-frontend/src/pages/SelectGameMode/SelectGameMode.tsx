import { FC } from "react";
import { useNavigate } from "react-router-dom";

const SelectGameMode: FC = () => {
    const navigate = useNavigate();

    return (
        <div>
            <h1>Select Game Mode</h1>
            <button onClick={() => navigate("/campaign")}>Campaign Mode</button>
            <button onClick={() => navigate("/practice")}>Practice Mode</button>
            <button onClick={() => navigate("/dailysweep")}>DailySweep Mode</button>
        </div>
    );
};

export default SelectGameMode;