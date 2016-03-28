package ru.samsu.mj.board;

import java.util.Arrays;

/**
 * Represents a bottom-left-cornered board in C_{n} (n is even).
 */
public class Board implements PartiallyComparable<Board> {
    private static final char rookUnicode = '\u2656';
    private static final char darkCellUnicode = '\u2613';
    private static final char lightCellUnicode = '\u2610';
    private final int[] DEFLATED_BOARD;
    private final BoardMatrix BOARD_MATRIX;
    private final RankMatrix RANK_MATRIX;
    private String html;

    private Board(int[] deflatedBoard) {
        this.DEFLATED_BOARD = deflatedBoard;
        this.BOARD_MATRIX = BoardMatrix.valueOfDeflatedBoard(deflatedBoard);
        this.RANK_MATRIX = RankMatrix.valueOfBoardMatrix(BOARD_MATRIX);
    }

    public boolean isInvolution() {
        int[] invDeflatedBoard = new int[DEFLATED_BOARD.length];
        Arrays.fill(invDeflatedBoard, -1);
        for (int i = 0; i < DEFLATED_BOARD.length; i++)
            if (DEFLATED_BOARD[i] != -1)
                invDeflatedBoard[DEFLATED_BOARD[i]] = i;
        for (int i = 0; i < DEFLATED_BOARD.length; i++) {
            int j = DEFLATED_BOARD[i];
            if (j == -1)
                continue;
            if (invDeflatedBoard[i] != -1)
                return false;
            if (DEFLATED_BOARD[j] != -1)
                return false;
        }
        return true;
    }

    public static Board valueOf(int[] deflatedBoard) {
        return new Board(deflatedBoard.clone());
    }

    public boolean isEmpty() {
        for (int i : DEFLATED_BOARD)
            if (i != -1)
                return false;
        return true;
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
    public int hashCode() {
        return Arrays.hashCode(DEFLATED_BOARD);
    }

    @Override
    public PartialComparison partiallyCompare(Board board) {
        return this.RANK_MATRIX.partiallyCompare(board.RANK_MATRIX);
    }

    public Board kerov() {
        int[] kerovDeflatedBoard = new int[-2 + 2 * DEFLATED_BOARD.length];
        Arrays.fill(kerovDeflatedBoard, -1);
        for (int i = 0; i < DEFLATED_BOARD.length; i++) {
            if (DEFLATED_BOARD[i] == -1)
                continue;
            kerovDeflatedBoard[2 * i - 1] = DEFLATED_BOARD[i] * 2;
        }
        return new Board(kerovDeflatedBoard);
    }

    public String html() {
        if (html == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><font size=8>");
            for (int i = 0; i < BOARD_MATRIX.length(); i++) {
                for (int j = 0; j < i; j++)
                    stringBuilder.append(BOARD_MATRIX.get(i, j) ? rookUnicode : lightCellUnicode);
                for (int j = i; j < BOARD_MATRIX.length(); j++)
                    stringBuilder.append(darkCellUnicode);
                stringBuilder.append("<br>");
            }
            stringBuilder.append("</font></html>");
            html = stringBuilder.toString();
        }

        return html;
    }
}
