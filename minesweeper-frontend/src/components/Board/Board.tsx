import './Board.css';
import {FC} from 'react';

type BoardProps = {
    gameboard: string[][];
}


export const Board: FC<BoardProps> = ({gameboard}) => {
    return <div className="board">
        {gameboard.map((row, i) => {
            return row.map((cell, j) => {
                return (
                    <div className="border">
                        <div className="cell" key={`${i}-${j}`} onClick={() => {
                            console.log(`Clicked on cell ${i}-${j} and it was a ${cell}`);
                        }}/>
                    </div>
                );
            })
        })
        }
    </div>;
};



