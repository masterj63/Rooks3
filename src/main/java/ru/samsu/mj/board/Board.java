package ru.samsu.mj.board;

import java.util.Arrays;

/**
 * Represents a bottom-left-cornered board in both <b>A<sub>n-1</sub></b> and <b>C<sub>n</sub></b> (where <b>n</b> is even).
 * We number its columns from left to right and its rows from top to bottom starting from <b>0</b> and up to <b>n-1</b> (both inclusively).
 * We define a <i>deflated board</i> as an integer array of one dimension reflecting board's rows.
 * Its <b>i</b><sup>th</sup> element is either <b>-1</b> (if there is no rook in <b>i</b><sup>th</sup> row) or <b>j</b> (positive or zero, if there is a rook in <b>i</b><sup>th</sup> row and <b>j</b><sup>th</sup> column).
 */
public class Board implements PartiallyComparable<Board> {
    private static final char rookUnicode = '\u2656';
    private static final char darkCellUnicode = '\u2613';
    private static final char lightCellUnicode = '\u2610';
    private final int[] DEFLATED_BOARD;
    private final BoardMatrix BOARD_MATRIX;
    private final RankMatrix RANK_MATRIX;
    private String html;

    /**
     * Constructs a new {@link Board} from a deflated board.
     *
     * @param deflatedBoard a ``deflated'' representation for a new {@link Board} to be constructed from.
     */
    private Board(int[] deflatedBoard) {
        this.DEFLATED_BOARD = deflatedBoard;
        this.BOARD_MATRIX = BoardMatrix.valueOfDeflatedBoard(deflatedBoard);
        this.RANK_MATRIX = RankMatrix.valueOfBoardMatrix(BOARD_MATRIX);
    }

    /**
     * A static factory.
     *
     * @param deflatedBoard a ``deflated'' representation for a new {@link Board} to be constructed from.
     * @return a new {@link Board}.
     */
    public static Board valueOf(int[] deflatedBoard) {
        return new Board(deflatedBoard.clone());
    }

    /**
     * @return whether this board contains no rooks.
     */
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

    /**
     * @return Kerov image of this board.
     */
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

    /**
     * Implementation note: the return value is cached.
     *
     * @return an HTML code to be used to display this board.
     */
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

    @Override
    public String toString() {
        return html();
    }
}
