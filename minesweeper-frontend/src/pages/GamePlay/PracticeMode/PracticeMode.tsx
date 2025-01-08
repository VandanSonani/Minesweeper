import {FC, useEffect, useState} from "react";
import {Client} from "@stomp/stompjs";
import SockJS from "sockjs-client";
import {v4 as uuidv4} from 'uuid';
import {Board} from "../../../components/Board/Board.tsx"; // Import UUID library
import './PracticeMode.css'

const PracticeMode: FC = () => {
    const placeHolder = Array(10).fill(null).map(() => Array(10).fill("#"));
    const [gameboard, setGameboard] = useState(placeHolder);


    useEffect(() => {
        const sessionId = uuidv4(); // Generate a unique session ID
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
                    console.log("Received gameboard:", gameboard?.gameBoard);
                    setGameboard(gameboard?.gameBoard);
                });

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

    return (
        <div>
            <div className={'board-container'}>
                <h1>Practice Mode</h1>

                <Board gameboard={gameboard ?? []}/>
            </div>
        </div>
    );
};

export default PracticeMode;