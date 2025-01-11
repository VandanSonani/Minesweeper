import {FC, useEffect, useRef, useState} from "react";
import {Client} from "@stomp/stompjs";
import SockJS from "sockjs-client";
import {v4 as uuidv4} from 'uuid';
import {Board} from "../../../components/Board/Board.tsx";
import './PracticeMode.css'
import {useNavigate} from "react-router-dom";

const PracticeMode: FC = () => {
    const placeHolder = Array(15).fill(null).map(() => Array(15).fill("#"));
    const [gameboard, setGameboard] = useState<string[][]>(placeHolder);
    const [firstClick, setFirstClick] = useState(true);
    const stompClientRef = useRef<Client | null>(null);
    const sessionIdRef = useRef<string>("");
    const navigate = useNavigate();



    useEffect(() => {
        const sessionId = uuidv4();
        sessionIdRef.current = sessionId;
        const socket = new SockJS("http://localhost:8080/minesweeper-websocket");
        const stompClient = new Client({
            webSocketFactory: () => socket,
            connectHeaders: {
                'session-id': sessionId
            },
            debug: function (str) {
                console.log(str);
            },
            onConnect: () => {
                console.log("STOMP connected");

                stompClient.subscribe(`/topic/practice/${sessionId}`, (message) => {
                    const gameboard = JSON.parse(message.body);
                    setGameboard(gameboard?.gameBoard);
                });
                stompClientRef.current = stompClient;
            },
            onWebSocketError: (error) => {
                console.error("WebSocket error:", error);
            },
            onStompError: (frame) => {
                console.error("STOMP error:", frame);
            }
        });

        stompClient.activate();
        return () => {
            stompClient.deactivate();
        };
    }, []);

    const handleCellClick = (i: number, j: number) => {
        console.log(`Clicked on cell ${i}-${j} and it is ${gameboard[i][j]}`);

        if (firstClick) {
            stompClientRef.current?.publish({
                destination: "/app/minesweeper-websocket",
                body: JSON.stringify({
                    mode: "practice",
                    action: "initialize",
                    sessionId: sessionIdRef.current,
                    row: i,
                    column: j
                }),
            });
            setFirstClick(false);
        } else {
            stompClientRef.current?.publish({
                destination: "/app/minesweeper-websocket",
                body: JSON.stringify({
                    action: "reveal",
                    row: i,
                    column: j,
                    mode: "practice",
                    sessionId: sessionIdRef.current
                }),
            });
        }
    }

    return (
        <div className={'practice-page'}>
            <div className="exit" onClick={() => navigate("/selectgamemode")}></div>
            <div className={'board-container'}>
                <h1>Practice Mode</h1>
                <Board gameboard={gameboard ?? []} onCellClick={handleCellClick}/>
            </div>
        </div>
    );
};

export default PracticeMode;