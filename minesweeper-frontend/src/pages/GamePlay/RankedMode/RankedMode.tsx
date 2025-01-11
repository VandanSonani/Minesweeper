import {FC, useEffect, useRef, useState} from "react";
import './RankedMode.css'
import {v4 as uuidv4} from "uuid";
import SockJS from "sockjs-client";
import {Client} from "@stomp/stompjs";
import {useNavigate} from "react-router-dom";


const RankedMode: FC = () => {
    const placeHolder = Array(15).fill(null).map(() => Array(15).fill("#"));
    const [gameboard, setGameboard] = useState<string[][]>(placeHolder);
    const stompClientRef = useRef<Client | null>(null);
    const sessionIdRef = useRef<string>("");
    const navigate = useNavigate();
    const [inQueue, setInQueue] = useState<boolean>(false);
    const [eventLog, setEventLog] = useState<string[]>([]);
    const [timer, setTimer] = useState<number | null>(null);
    const [elapsedTime, setElapsedTime] = useState<number>(1);

    const handleMessage = (message: any) => {
        const messageBody = message.body;
        try {
            const gameboard = JSON.parse(messageBody);
            handleGameboardUpdate(gameboard);
        } catch (e) {
            handleTextMessage(messageBody);
        }
    };

    const handleGameboardUpdate = (gameboard: any) => {
        setGameboard(gameboard?.gameBoard);
    };


    const handleTextMessage = (message: string) => {
        if (message.includes("[LOG]")) {
            setEventLog(prevLog => [...prevLog, message]);
        }
        if (message === "Match will be confirmed in 3 seconds") {
            console.log(message);
            startTimer(3);
            setInQueue(false);
            // Handle the match confirmation message
        }
    };

    const startTimer = (duration: number) => {
        setTimer(duration);
        const interval = setInterval(() => {
            setTimer(prevTimer => {
                if (prevTimer === null || prevTimer <= 1) {
                    clearInterval(interval);
                    return null;
                }
                return prevTimer - 1;
            });
        }, 1000);
    };

    useEffect(() => {
        let timer: NodeJS.Timeout | null = null;
        if (inQueue) {
            const startTime = Date.now();
            setElapsedTime(1); // Start the timer at 1 second
            timer = setInterval(() => {
                const elapsedTime = Math.floor((Date.now() - startTime) / 1000) + 1;
                setElapsedTime(elapsedTime);
            }, 1000);
        } else {
            setElapsedTime(0);
        }

        return () => {
            if (timer) {
                clearInterval(timer);
            }
        };
    }, [inQueue]);

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
                stompClient.subscribe(`/topic/ranked/${sessionId}`, handleMessage);
                stompClientRef.current = stompClient;
            },
            onWebSocketError: (error) => {
                console.error("WebSocket error:", error);
            },
            onStompError: (frame) => {
                console.error("STOMP error:", frame);
            },
            onDisconnect: () => {
                console.log("STOMP disconnected");
                stompClientRef.current?.publish({
                    destination: "/app/minesweeper-websocket",
                    body: JSON.stringify({
                        mode: "ranked",
                        action: "leaveRankedQueue",
                        sessionId: sessionIdRef.current,
                    }),
                });
                navigate('/');
            },
            onWebSocketClose: () => {
                console.log("WebSocket closed");
                stompClientRef.current?.publish({
                    destination: "/app/minesweeper-websocket",
                    body: JSON.stringify({
                        mode: "ranked",
                        action: "leaveRankedQueue",
                        sessionId: sessionIdRef.current,
                    }),
                });
                navigate('/');
            },

        });

        stompClient.activate();
        return () => {
            stompClient.deactivate();
        };
    }, []);

    const handleJoinOrLeaveQueue = () => {
        if (inQueue) {
            // Leave the queue
            setInQueue(false);
            stompClientRef.current?.publish({
                destination: "/app/minesweeper-websocket",
                body: JSON.stringify({
                    mode: "ranked",
                    action: "leaveRankedQueue",
                    sessionId: sessionIdRef.current,
                }),
            });
        } else {
            // Join the queue
            setInQueue(true);
            stompClientRef.current?.publish({
                destination: "/app/minesweeper-websocket",
                body: JSON.stringify({
                    mode: "ranked",
                    action: "joinRankedQueue",
                    sessionId: sessionIdRef.current,
                }),
            });
        }
    }
    return (
        <div>
            <h4>Ranked Mode</h4>
            <div className={'page-container'}>
                {timer !== null && (
                    <div className="timer">
                        {timer}
                    </div>
                )}

                <div style={{height: '25rem'}}/>

                <div className={'ranked-enter-container'}>
                    <button className={'join-queue-button'} onClick={handleJoinOrLeaveQueue}>
                        {inQueue ? 'Leave Ranked Queue' : 'Join Ranked Queue'}
                        {inQueue && <div className="loading-circle"></div>}
                    </button>
                    { (elapsedTime > 0) && (<div>
                        {elapsedTime}
                    </div>)}
                </div>
            </div>
        </div>

    )
}

export default RankedMode;