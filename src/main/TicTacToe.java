package src.main;


import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int GRID_SIZE = 3;
    private char[][] grid;
    private char currentPlayer;

    public void start() {
        grid = createGrid();
        currentPlayer = 'X';

        while (true) {
            printGrid();
            makeMove(currentPlayer);
            if (checkGameState() != GameState.GAME_NOT_FINISHED) break;
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
        printGrid();
        System.out.println(checkGameState().getMessage());
    }

    private char[][] createGrid() {
        char[][] grid = new char[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = '_';
            }
        }
        return grid;
    }

    private void printGrid() {
        System.out.println("---------");
        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print("| ");
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("---------");
    }

    private void makeMove(char currentPlayer) {
        int row;
        int col;
        while (true) {
            try {
                row = SCANNER.nextInt() - 1;
                col = SCANNER.nextInt() - 1;
                if (row < 0 || row > GRID_SIZE - 1 || col < 0 || col > GRID_SIZE - 1) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else if (grid[row][col] == 'X' || grid[row][col] == 'O') {
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    grid[row][col] = currentPlayer;
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
                SCANNER.next();
            }
        }
    }

    private GameState checkGameState() {
        boolean xWins = false;
        boolean oWins = false;
        int xCount = 0;
        int oCount = 0;

        for (int i = 0; i < GRID_SIZE; i++) {
            int rowXCount = 0;
            int rowOCount = 0;
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 'X') {
                    xCount++;
                    rowXCount++;
                }
                if (grid[i][j] == 'O') {
                    oCount++;
                    rowOCount++;
                }
            }
            if (rowXCount == GRID_SIZE) xWins = true;
            if (rowOCount == GRID_SIZE) oWins = true;
        }
        for (int i = 0; i < GRID_SIZE; i++) {
            int colXCount = 0;
            int colOCount = 0;
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[j][i] == 'X') colXCount++;
                if (grid[j][i] == 'O') colOCount++;
            }
            if (colXCount == GRID_SIZE) xWins = true;
            if (colOCount == GRID_SIZE) oWins = true;
        }
        if (grid[0][0] == 'X' && grid[1][1] == 'X' && grid[2][2] == 'X') xWins = true;
        if (grid[2][0] == 'X' && grid[1][1] == 'X' && grid[0][2] == 'X') xWins = true;
        if (grid[0][0] == 'O' && grid[1][1] == 'O' && grid[2][2] == 'O') oWins = true;
        if (grid[2][0] == 'O' && grid[1][1] == 'O' && grid[0][2] == 'O') oWins = true;

        if (xWins && oWins) return GameState.IMPOSSIBLE;
        else if (xWins) return GameState.X_WINS;
        else if (oWins) return GameState.O_WINS;
        else if (xCount + oCount == 9) return GameState.DRAW;
        else if (xCount - oCount > 1 || oCount - xCount > 1) return GameState.IMPOSSIBLE;
        else return GameState.GAME_NOT_FINISHED;
    }

    private enum GameState {
        X_WINS("X wins"),
        O_WINS("O wins"),
        DRAW("Draw"),
        GAME_NOT_FINISHED("Game not finished"),
        IMPOSSIBLE("Impossible");

        final String message;
        GameState(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}