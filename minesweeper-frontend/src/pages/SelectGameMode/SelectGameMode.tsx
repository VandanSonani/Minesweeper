import {FC} from "react";
import {useNavigate} from "react-router-dom";
import './SelectGameMode.css';

const SelectGameMode: FC = () => {
    const navigate = useNavigate();


    return (
        <div>
            <div className={'page-content'}>
                <h1>Welcome, user!</h1>
                <div style={{height: '25rem'}}/>
                <section className={"holder"}>
                    <button className={"home"} onClick={() => navigate("/")}>Home</button>
                    {/*<button className={"campaign"} onClick={() => navigate("/campaign")}>Campaign</button>*/}
                    <button className={"practice"} onClick={() => navigate("/practice")}>Practice</button>
                    <button className={"ranked"} onClick={() => navigate("/ranked")}>Ranked</button>
                    {/*<button className={"dailysweep"} onClick={() => navigate("/dailysweep")}>DailySweep</button>*/}
                </section>
            </div>

        </div>
    );
};

export default SelectGameMode;