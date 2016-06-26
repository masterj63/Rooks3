package ru.samsu.mj.board;

/**
 * Represents a bottom-left-cornered board in both <b>A<sub>n-1</sub></b> and <b>C<sub>n</sub></b> (where <b>n</b> is even).
 * Encapsulates a boolean array of two dimensions which <b>[i][j]</b><sup>th</sup> element is <b>true</b> iff there is a rook in the board in <b>i</b><sup>th</sup> row and in <b>j</b><sup>th</sup> column.
 */
class BoardMatrix {
    /**
     * See class' description.
     */
    private final boolean[][] MATRIX;

    /**
     * @param matrix the matrix to be encapsulated.
     */
    private BoardMatrix(boolean[][] matrix) {
        MATRIX = matrix;
    }

    /**
     * A static factory.
     *
     * @param deflatedBoard a deflated board a new {@link BoardMatrix} to be constructed from.
     * @return a new {@link BoardMatrix}.
     */
    static BoardMatrix valueOfDeflatedBoard(int[] deflatedBoard) {
        boolean[][] matrix = new boolean[deflatedBoard.length][];

        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new boolean[i];
            if (deflatedBoard[i] >= 0)
                matrix[i][deflatedBoard[i]] = true;
        }

        return new BoardMatrix(matrix);
    }

    /**
     * A getter for the incapsulated field.
     *
     * @param i number of a row.
     * @param j number of a column.
     * @return <b>[i][j]</b><sup>th</sup> value requested.
     */
    boolean get(int i, int j) {
        return MATRIX[i][j];
    }

    /**
     * @return the dimension of board. The number of rows, specifically.
     */
    int length() {
        return MATRIX.length;
    }

    //TODO see if the method below makes sense or just confuses.

    /**
     * Since the problem is set over the square boards, behaves exactly as <b>{@code ru.samsu.mj.board.length}</b> of zero arity does.
     *
     * @param i the number of row.
     * @return the length of <b>i</b><sup>th</sup> row.
     */
    int length(int i) {
        return MATRIX[i].length;
    }
}