package ru.samsu.mj.board;

import java.util.Arrays;

/**
 * Represents a bottom-left-cornered board.
 */
public class Board implements PartiallyComparable<Board> {
    private final int[] DEFLATED_BOARD;
    private final BoardMatrix BOARD_MATRIX;
    private final RankMatrix RANK_MATRIX;

    private Board(int[] deflatedBoard) {
        this.DEFLATED_BOARD = deflatedBoard;
        this.BOARD_MATRIX = BoardMatrix.valueOfDeflatedBoard(deflatedBoard);
        this.RANK_MATRIX = RankMatrix.valueOfBoardMatrix(BOARD_MATRIX);
    }

    static Board valueOf(int[] deflatedBoard) {
        return new Board(deflatedBoard.clone());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Board))
            return false;

        if (obj == this)
            return true;
        Board that = (Board) obj;

        return Arrays.equals(this.DEFLATED_BOARD, that.DEFLATED_BOARD);
    }

    @Override
    public PartialComparison partiallyCompare(Board board) {
        return this.RANK_MATRIX.partiallyCompare(board.RANK_MATRIX);
    }
}
