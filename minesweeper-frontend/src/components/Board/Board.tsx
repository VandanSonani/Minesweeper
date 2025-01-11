import './Board.css';
import {FC, useEffect, useState} from 'react';

type BoardProps = {
    gameboard: string[][];
    onCellClick: (i: number, j: number) => void;
}

const styleName = (cell: string) => {
    switch (cell) {
        case '#':
            return 'hidden';
        case 'E':
            return 'empty';
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


type flagPlacement = {
    row: number;
    col: number;
}


export const Board: FC<BoardProps> = ({gameboard, onCellClick}) => {

    const [flaggedCells, setFlaggedCells] = useState<flagPlacement[]>([]);
    const [isBombCheck, setIsBombCheck] = useState(false);


    const flagPlacement = (row: number, col: number) => {
        const flagPlacementObject: flagPlacement = {row, col};
        const isFlagged = flaggedCells.some(flag => flag.row === row && flag.col === col);
        if (gameboard[row][col] !== '#' && gameboard[row][col] !== 'B') {
            return;
        }
        if (isFlagged) {
            const newFlaggedCells = flaggedCells.filter((flag) => {
                return !(flag.row === row && flag.col === col);
            });
            setFlaggedCells(newFlaggedCells);
        } else {
            setFlaggedCells([...flaggedCells, flagPlacementObject]);
        }
    }

    useEffect(() => {
        console.log(flaggedCells);
    }, [flaggedCells]);

    const handleRightClick = (i: number, j: number) => {
        flagPlacement(i, j);
    }

    const isFlag = (row: number, col: number) => {
        const isFlagged = flaggedCells.some(flag => flag.row === row && flag.col === col);
        return isFlagged ? 'flag' : '';
    }

    const handleCellClick = (i: number, j: number) => {
        if (styleName(gameboard[i][j]) === 'hidden' || styleName(gameboard[i][j]) === 'hidden-bomb' && !isFlag(i, j)) {
            onCellClick(i, j);
            if (isBomb(i, j)) {
                console.log("You clicked on a bomb");
                setIsBombCheck(true);
            }
        }
    };


    //todo finish this function
    const isBomb = (row: number, col: number) => {
        const checkBomb = gameboard[row][col] === 'B';
        return checkBomb ? 'bomb' : '';
    }


    return (
        <div className="board">
            {gameboard.map((row, i) => {
                return row.map((cell, j) => {
                    return (
                        <div className="border" key={`${i}-${j}`}>
                            <div
                                className={`${isBombCheck && isBomb(i, j) ? 'bomb' : ''} ${isFlag(i, j)} ${styleName(cell)} cell`}
                                onClick={() => handleCellClick(i, j)}
                                onContextMenu={(e) => {
                                    e.preventDefault();
                                    handleRightClick(i, j);
                                }}
                            />
                        </div>
                    );
                });
            })}
        </div>
    );
};



