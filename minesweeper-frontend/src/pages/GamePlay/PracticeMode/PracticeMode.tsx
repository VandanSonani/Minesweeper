import {FC, useEffect, useRef, useState} from "react";
import {Client} from "@stomp/stompjs";
import SockJS from "sockjs-client";
import {v4 as uuidv4} from 'uuid';
import {Board} from "../../../components/Board/Board.tsx"; // Import UUID library
import './PracticeMode.css'
import {useNavigate} from "react-router-dom";

const PracticeMode: FC = () => {
    const placeHolder = Array(10).fill(null).map(() => Array(10).fill("#"));
    const [gameboard, setGameboard] = useState<string[][]>(placeHolder);
    const stompClientRef = useRef<Client | null>(null);
    const sessionIdRef = useRef<string>("");
    const navigate = useNavigate();


    useEffect(() => {
        const sessionId = uuidv4(); // Generate a unique session ID
        sessionIdRef.current = sessionId;
        const socket = new SockJS("http://localhost:8080/minesweeper-websocket");
        const stompClient = new Client({
            webSocketFactory: () => socket,
            connectHeaders: {
                'session-id': sessionId // Include the session ID in the headers
            },
            debug: function (str) {
                console.log(str);
            }, // Debug messages for STOMP
            onConnect: () => {
                console.log("STOMP connected");

                // Subscribe to the practice topic
                console.log("Subscribing to practice topic for session ID:", sessionId);
                stompClient.subscribe(`/topic/practice/${sessionId}`, (message) => {
                    const gameboard = JSON.parse(message.body);
                    setGameboard(gameboard?.gameBoard);
                });
                stompClientRef.current = stompClient;

                // Send the initial "initialize" message to start the gameboard for practice mode
                stompClient.publish({
                    destination: "/app/minesweeper-websocket", // Corrected destination
                    body: JSON.stringify({
                        mode: "practice",
                        action: "initialize",
                        sessionId: sessionId // Include the session ID in the message
                    }),
                });
            },
            onWebSocketError: (error) => {
                console.error("WebSocket error:", error);
            },
            onStompError: (frame) => {
                console.error("STOMP error:", frame);
            }
        });

        stompClient.activate(); // This starts the connection
        return () => {
            stompClient.deactivate();
        };
    }, []);

    const handleCellClick = (i: number, j: number) => {
        console.log(`Clicked on cell ${i}-${j} and it is ${gameboard[i][j]}`);

        stompClientRef.current?.publish({
            destination: "/app/minesweeper-websocket",
            body: JSON.stringify({
                action: "reveal",
                row: i,
                column: j,
                mode: "practice",
                sessionId: sessionIdRef.current // Include the sessio   n ID in the message
            }),
        });
    }

    return (
        <div>
            <div className="exit" onClick={() => navigate("/selectgamemode")}></div>
            <div className={'board-container'}>
                <h1>Practice Mode</h1>
                <Board gameboard={gameboard ?? []} onCellClick={handleCellClick}/>
            </div>
        </div>
    );
};

export default PracticeMode;