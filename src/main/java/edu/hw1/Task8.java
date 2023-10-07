package edu.hw1;

public class Task8 {
    private static final int[][] possibleTurns = new int[][] {{2, 1}, {1, 2}, {-1, 2},
        {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};
    private static final int BOARD_DIMENSIONS = 8;

    private Task8() {
        throw new IllegalStateException();
    }

    public static boolean knightBoardCapture(int[][] chessBoard) {
        if (chessBoard.length != BOARD_DIMENSIONS || chessBoard[0].length != BOARD_DIMENSIONS) {
            throw new IllegalArgumentException("Board is on incorrect size!");
        }
        for (int row = 0; row < BOARD_DIMENSIONS; row++) {
            for (int column = 0; column < BOARD_DIMENSIONS; column++) {
                if (chessBoard[row][column] != 1) {
                    continue;
                }
                for (int[] turn : possibleTurns) {
                    if (turn[0] + row >= BOARD_DIMENSIONS ||
                        turn[0] + row < 0 || turn[1] + column >= BOARD_DIMENSIONS ||
                        turn[1] + column < 0) {
                        continue;
                    }
                    if (chessBoard[row + turn[0]][column + turn[1]] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
