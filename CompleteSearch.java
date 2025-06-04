import java.util.*;

public class A2_Q1 {

    static int L = 9; //board length is 9
    static int W = 5; // board width is 5

    static int[] columnChange = {1, 0, -1, 0}; // right down left and up y coordinate change
    static int[] rowChange = {0, 1, 0, -1}; // right down left and up x coordinate change

    static Stack<String[][]> stackBoard = new Stack<>();
    static Stack<int[]> stackCounters = new Stack<>();

    public static int[] solveGame(String[][] board, int currentBallsLeft, int currentMovesTaken, int[] solution) {
        int[] currentSolution = {currentBallsLeft, currentMovesTaken};

        // base case. board is idle -> check new solution conditions.
        if (!movesPossible(board)) {

            // less balls on the board better solution or same amount of balls and less moves required
            if (currentSolution[0] < solution[0] ||
                    (currentSolution[0] == solution[0] && currentSolution[1] < solution[1])) {
                solution[0] = currentSolution[0];
                solution[1] = currentSolution[1];
            }
            return solution;
        }




        for (int r = 0; r < W; r++) { // iterate through rows
            for (int c = 0; c < L; c++) { // iterate through columns in each row
                if (board[r][c].equals("o")) { // ball found, move on to checking possible moves
                    for (int direction = 0; direction < 4; direction++) { // iterate through possible moves
                        if (canMove(board, r, c, direction)) {

                            stackBoard.push(saveBoard(board));

                            int[] counters = {currentMovesTaken, currentBallsLeft};
                            stackCounters.push(counters);

                            makeMove(board, r, c, direction);
                            currentMovesTaken++; // move taken
                            currentBallsLeft--; // move removes ball


                            solveGame(board, currentBallsLeft, currentMovesTaken, solution); // recursive call

                            counters = stackCounters.pop();
                            currentBallsLeft = counters[1];
                            currentMovesTaken = counters[0];

                            board = stackBoard.pop();

                        }
                    }
                }
            }
        }
        return solution;
    }


    // calls recursive function
    public static int[] game(String[][] board) {
        int[] solution = {Integer.MAX_VALUE, Integer.MAX_VALUE}; // set to max initially because of base case condition
        int currentBallsLeft = countBalls(board);
        int currentMovesTaken = 0;
        return solveGame(board, currentBallsLeft, currentMovesTaken, solution);
    }

    public static String[][] saveBoard(String[][] board) { // used to save amd restore state of board
        String[][] copy = new String[board.length][board[0].length];
        for (int r = 0; r < board.length; r++) {
            System.arraycopy(board[r], 0, copy[r], 0, board[r].length);
        }
        return copy;
    }

    // scans whole grid and increments counter if a ball is found.
    public static int countBalls(String[][] board) {

        int currentBallCount = 0;
        for (int i = 0; i < W; i++) { // iterate through rows
            for (int j = 0; j < L; j++) { // iterate through columns in each row
                if (board[i][j].equals("o")) {
                    currentBallCount++;
                }
            }
        }
        return currentBallCount;
    }

    // scans whole board to check if grid is in idle state. helps to identify base case
    private static boolean movesPossible(String[][] board) {
        for (int r = 0; r < W; r++) {
            for (int c = 0; c < L; c++) {
                if (board[r][c].equals("o")) {
                    for (int direction = 0; direction < 4; direction++) {
                        if (canMove(board, r, c, direction)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void undoMove(String[][] board, int r, int c, int direction) {
        board[r][c] = "o";
        board[r - rowChange[direction]][c - columnChange[direction]] = "o";
        board[r - 2 * rowChange[direction]][c - 2 * columnChange[direction]] = ".";
    }

    // change grid values to simulate movement
    public static void makeMove(String[][] board, int r, int c, int direction) {
        board[r][c] = "."; // cell that originally contained ball
        board[r + rowChange[direction]][c + columnChange[direction]] = "."; // cell to be jumped over
        board[r + 2 * rowChange[direction]][c + 2 * columnChange[direction]] = "o"; // cell to be landed on
    }

    // check boundary conditions, movement conditions using helper method
    static boolean canMove(String[][] board, int i, int j, int direction) {
        if (board[i][j].equals("o")) {
            if (validToJumpOver(i + rowChange[direction], j + columnChange[direction], board)) {
                if (validToMoveTo(i + 2 * rowChange[direction], j + 2 * columnChange[direction], board)) {
                    return true;
                }
            }
        }
        return false;
    }

    // a cell can have a ball placed in it if its in bounds and its '.'
    static boolean validToMoveTo(int i, int j, String[][] board) {
        if ((i >= W || i < 0 || j >= L || j < 0) || board[i][j].equals("#") || board[i][j].equals("o")) {
            return false;
        }
        return true;
    }

    // a cell can have a ball jump over it if its in bounds and it's a ball
    static boolean validToJumpOver(int i, int j, String[][] board) {
        if ((i >= W || i < 0 || j >= L || j < 0) || board[i][j].equals("#") || board[i][j].equals(".")) {
            return false;
        }
        return true;
    }
}
