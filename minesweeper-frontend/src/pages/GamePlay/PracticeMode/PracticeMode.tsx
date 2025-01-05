import { FC, useEffect } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { v4 as uuidv4 } from 'uuid'; // Import UUID library

const PracticeMode: FC = () => {
    useEffect(() => {
        const sessionId = uuidv4(); // Generate a unique session ID
        const socket = new SockJS("http://localhost:8080/minesweeper-websocket");
        const stompClient = new Client({
            webSocketFactory: () => socket,
            connectHeaders: {
                'session-id': sessionId // Include the session ID in the headers
            },
            debug: function (str) { console.log(str); }, // Debug messages for STOMP
            onConnect: () => {
                console.log("STOMP connected");

                // Subscribe to the practice topic
                console.log("Subscribing to practice topic for session ID:", sessionId);
                stompClient.subscribe(`/topic/practice/${sessionId}`, (message) => {
                    const gameboard = JSON.parse(message.body);
                    console.log("Received gameboard:", gameboard);
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

        // Cleanup function to deactivate the client on component unmount
        return () => {
            stompClient.deactivate();
        };
    }, []); // Empty dependency array ensures this runs only once

    return (
        <div>
            <h2>Practice Mode</h2>
        </div>
    );
};

export default PracticeMode;