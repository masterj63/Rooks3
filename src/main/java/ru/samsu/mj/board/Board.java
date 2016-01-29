package ru.samsu.mj.board;

public class Board implements Comparable<Board> {
    private final int[] deflatedBoard;

    private Board(int[] deflatedBoard) {
        this.deflatedBoard = deflatedBoard;
    }

    static Board valueOf(int[] deflatedBoard) {
        Board board = new Board(deflatedBoard.clone());
        return board;
    }

    @Override
    public int compareTo(Board o) {
        throw new UnsupportedOperationException();
    }
}
