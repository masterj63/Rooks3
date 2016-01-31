package ru.samsu.mj.board;

class RankMatrix implements PartiallyComparable<RankMatrix> {
    private RankMatrix(BoardMatrix boardMatrix) {

    }

    static RankMatrix valueOfBoardMatrix(BoardMatrix boardMatrix) {
        return new RankMatrix(boardMatrix);
    }

    public PartialComparison partiallyCompare(RankMatrix rankMatrix) {
        throw new UnsupportedOperationException();
    }
}
