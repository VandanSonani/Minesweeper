import './TestPage.css';
import {FC} from "react";



const TestPage: FC = () => {
    return (
        <div className="">
            <h1>Test Page</h1>
            <input className="test-textarea" placeholder="x-input"></input>
            <input className="test-textarea" placeholder="y-input"></input>
            <button className="test-button">Enter</button>
        </div>
    );
}

export default TestPage;