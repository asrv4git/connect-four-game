package com.hackerearth.connectfourgameapi.core;

import com.hackerearth.connectfourgameapi.models.GameStatus;
import com.hackerearth.connectfourgameapi.models.PlayResult;
import com.hackerearth.connectfourgameapi.models.entity.Board;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GameEngine {
    // dimensions for our board
    private static final int WIDTH = 7, HEIGHT = 6;

    private static char[][] grid;
    private static int lastTop;
    private static int lastCol;

    // we define characters for players (R for Red, Y for Yellow)
    private static final char[] PLAYERS = {'R', 'Y'};

    public static PlayResult playGame(Board board, int columnNumber) {
        //initialize
        grid = GridRepresentationConverter.stringToTwoDimensionalCharArray(board.getGrid());
        lastTop = board.getLastTop();
        lastCol = board.getLastCol();
        int player = board.getChanceOf();
        int moves = (WIDTH*HEIGHT)-board.getValidMoveCount();

        if(moves>0){
            // symbol for current player
            char symbol = PLAYERS[player];

            // we ask user to choose a column
            chooseAndDrop(symbol, columnNumber);

            System.out.println(GridRepresentationConverter.TwoDimensionalCharArrayToString(grid));
            // we need to check if a player won. If not, 
            // we continue, otherwise, we display a message
            if (isWinningPlay()) {
                System.out.println("\nPlayer " + symbol + " wins!");
                return new PlayResult(symbol=='Y'?GameStatus.YELLOW_WINS:GameStatus.READ_WINS,
                        new Board(lastCol, lastTop,
                                board.getValidMoveCount()+1,
                                GridRepresentationConverter.TwoDimensionalCharArrayToString(grid),
                                1-player));
            }
            else if(--moves==0){
                System.out.println("Game over. No winner. Try again!");
                return new PlayResult(GameStatus.DRAW,
                        new Board(lastCol, lastTop,
                                board.getValidMoveCount()+1,
                                GridRepresentationConverter.TwoDimensionalCharArrayToString(grid),
                                1-player));
            }
            else {
                System.out.println("Game In progress");
                return new PlayResult(GameStatus.INPROGRESS,
                        new Board(lastCol, lastTop,
                                board.getValidMoveCount() + 1,
                                GridRepresentationConverter.TwoDimensionalCharArrayToString(grid),
                                1 - player));
            }
        }
        else
            return null;
    }

    // prompts the user for a column, repeating until a valid choice is made
    private static void chooseAndDrop(char symbol, int col) {
        do {
            System.out.println("\nPlayer " + symbol + " turn: ");
            // now we can place the symbol to the first 
            // available row in the asked column
            for (int h = HEIGHT - 1; h >= 0; h--) {
                if (grid[h][col] == '.') {
                    grid[lastTop = h][lastCol = col] = symbol;
                    return;
                }
            }
            // if column is full ==> we need to ask for a new input
            System.out.println("Column " + col + " is full.");
            throw new ResponseStatusException(HttpStatus.ACCEPTED, "INVALID");
        } while (true);
    }

    // get string representation of the row containing
    // the last play of the user
    private static String horizontal() {
        return new String(grid[lastTop]);
    }

    // get string representation fo the col containing
    // the last play of the user
    private static String vertical() {
        StringBuilder sb = new StringBuilder(HEIGHT);

        for (int h = 0; h < HEIGHT; h++) {
            sb.append(grid[h][lastCol]);
        }

        return sb.toString();
    }

    // get string representation of the "/" diagonal
    // containing the last play of the user
    private static String slashDiagonal() {
        StringBuilder sb = new StringBuilder(HEIGHT);

        for (int h = 0; h < HEIGHT; h++) {
            int w = lastCol + lastTop - h;

            if (0 <= w && w < HEIGHT) {
                sb.append(grid[h][w]);
            }
        }

        return sb.toString();
    }

    // get string representation of the "\"
    // diagonal containing the last play of the user
    private static String backslashDiagonal() {
        StringBuilder sb = new StringBuilder(HEIGHT);

        for (int h = 0; h < HEIGHT; h++) {
            int w = lastCol - lastTop + h;

            if (0 <= w && w < WIDTH) {
                sb.append(grid[h][w]);
            }
        }

        return sb.toString();
    }

    // static method checking if a substring is in str
    private static boolean contains(String str, String substring) {
        return str.indexOf(substring) >= 0;
    }

    // now, we create a method checking if last play is a winning play
    private static boolean isWinningPlay() {
        if (lastCol == -1) {
            System.err.println("No move has been made yet");
            return false;
        }

        char sym = grid[lastTop][lastCol];
        // winning streak with the last play symbol
        String streak = String.format("%c%c%c%c", sym, sym, sym, sym);

        // check if streak is in row, col,
        // diagonal or backslash diagonal
        return contains(horizontal(), streak) ||
                contains(vertical(), streak) ||
                contains(slashDiagonal(), streak) ||
                contains(backslashDiagonal(), streak);
    }
}
