package ru.samsu.mj.board;

/**
 * Encapsulates a two-dimensional matrix such that its <b>[i][j]</b><sup>th</sup> element is the number of rooks that are no higher than in <b>i</b><sup>th</sup> row and no righter than in <b>j</b><sup>th</sup> column in a {@link BoardMatrix}.
 */
class RankMatrix implements PartiallyComparable<RankMatrix> {
    private final int[][] RANK_MATRIX;

    private RankMatrix(int[][] rankMatrix) {
        this.RANK_MATRIX = rankMatrix;
    }

    /**
     * A static factory.
     *
     * @param boardMatrix see the class' commentary.
     * @return a new value.
     */
    static RankMatrix valueOfBoardMatrix(BoardMatrix boardMatrix) {
        int[][] rankMatrix = new int[boardMatrix.length()][];

        for (int i = 0; i < rankMatrix.length; i++)
            rankMatrix[i] = new int[boardMatrix.length(i)];

        // Initialize bottom-left corner.
        if (boardMatrix.get(boardMatrix.length() - 1, 0))
            rankMatrix[rankMatrix.length - 1][0] = 1;
        // Go through leftmost column.
        for (int i = rankMatrix.length - 2; i > 0; i--) {
            rankMatrix[i][0] = rankMatrix[1 + i][0];
            if (boardMatrix.get(i, 0))
                rankMatrix[i][0]++;
        }
        // Go through bottom row.
        for (int j = 1; j < rankMatrix[rankMatrix.length - 1].length; j++) {
            rankMatrix[rankMatrix.length - 1][j] = rankMatrix[rankMatrix.length - 1][j - 1];
            if (boardMatrix.get(rankMatrix.length - 1, j))
                rankMatrix[rankMatrix.length - 1][j]++;
        }
        // Go through the whole.
        for (int i = rankMatrix.length - 2, j = 1; i > j; i--, j++) {
            //Go up.
            for (int ii = i, jj = j; ii > j; ii--) {
                int onLeft = rankMatrix[ii][jj - 1];
                int onBottom = rankMatrix[1 + ii][jj];
                int onLeftBottom = rankMatrix[1 + ii][jj - 1];
                rankMatrix[ii][jj] = onLeft + onBottom - onLeftBottom;
                if (boardMatrix.get(ii, jj))
                    rankMatrix[ii][jj]++;
            }
            //Go right.
            for (int jj = j, ii = i; jj < i; jj++) {
                int onLeft = rankMatrix[ii][jj - 1];
                int onBottom = rankMatrix[1 + ii][jj];
                int onLeftBottom = rankMatrix[1 + ii][jj - 1];
                rankMatrix[ii][jj] = onLeft + onBottom - onLeftBottom;
                if (boardMatrix.get(ii, jj))
                    rankMatrix[ii][jj]++;
            }
        }

        return new RankMatrix(rankMatrix);
    }

    @Override
    public PartialComparison partiallyCompare(RankMatrix that) {
        boolean isLessOrEq = true;
        boolean isEqual = true;
        boolean isGreaterOrEq = true;

        for (int i = 0; i < this.RANK_MATRIX.length; i++)
            for (int j = 0; j < this.RANK_MATRIX[i].length; j++) {
                int thisRank = this.RANK_MATRIX[i][j];
                int thatRank = that.RANK_MATRIX[i][j];

                if (thisRank < thatRank)
                    isGreaterOrEq = false;
                if (thisRank != thatRank)
                    isEqual = false;
                if (thisRank > thatRank)
                    isLessOrEq = false;
            }

        if (isEqual)
            return PartialComparison.EQUALS;
        else if (isLessOrEq)
            return PartialComparison.LESS;
        else if (isGreaterOrEq)
            return PartialComparison.GREATER;
        else
            return PartialComparison.INCOMPARABLE;
    }
}
