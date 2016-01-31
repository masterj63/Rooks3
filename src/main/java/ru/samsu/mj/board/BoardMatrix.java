package ru.samsu.mj.board;

class BoardMatrix {
    private final boolean[][] MATRIX;

    private BoardMatrix(boolean[][] matrix) {
        MATRIX = matrix;
    }

    static BoardMatrix valueOfDeflatedBoard(int[] deflatedBoard) {
        boolean[][] matrix = new boolean[deflatedBoard.length][];

        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new boolean[i];
            if (deflatedBoard[i] > 0)
                matrix[i][deflatedBoard[i]] = true;
        }

        return new BoardMatrix(matrix);
    }

    boolean get(int i, int j) {
        return MATRIX[i][j];
    }

    int length() {
        return MATRIX.length;
    }

    int length(int i) {
        return MATRIX[i].length;
    }
}