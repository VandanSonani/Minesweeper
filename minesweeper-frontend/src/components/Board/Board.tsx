import './Board.css';
import {FC} from 'react';

type BoardProps = {
    gameboard: string[][];
    onCellClick: (i: number, j: number) => void;
}

const styleName = (cell: string) => {
    switch(cell) {
        case '#':
            return 'hidden';
        case 'F':
            return 'flag';
        case 'B':
            return 'hidden-bomb';
        case '1':
            return 'one';
        case '2':
            return 'two';
        case '3':
            return 'three';
        case '4':
            return 'four';
        case '5':
            return 'five';
        case '6':
            return 'six';
        case '7':
            return 'seven';
        case '8':
            return 'eight';
        default:
            return '';
    }
}


export const Board: FC<BoardProps> = ({gameboard, onCellClick}) => {


    return (<div className="board">
        {gameboard.map((row, i) => {
            return row.map((cell, j) => {
                return (
                    <div className="border" key={`${i}-${j}`} >
                        <div className={styleName(cell) + " cell"} onClick={() => {
                            onCellClick(i, j);
                        }}/>
                    </div>
                );
            })
        })
        }
    </div>);
};



