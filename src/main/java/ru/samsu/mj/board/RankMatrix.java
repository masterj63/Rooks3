package ru.samsu.mj.board;

class RankMatrix implements PartiallyComparable<RankMatrix> {
    private final int[][] RANK_MATRIX;

    private RankMatrix(int[][] rankMatrix) {
        this.RANK_MATRIX = rankMatrix;
    }

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
            for (int off = 0; i - off > j + off; off++) {
                int ii = i - off, jj = j + off;
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
