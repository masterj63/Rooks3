package ru.samsu.mj.board.a_n;

import ru.samsu.mj.board.Board;
import ru.samsu.mj.collection.BoardCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardGeneratorA {
    public static BoardCollection generateByDimensionInvols(int n) {
        List<Board> list = new ArrayList<>();
        int[] board = new int[n];
        Arrays.fill(board, -1);
        generateByDimensionInvols(list, board, new boolean[n], n, 0);
        BoardCollection boardCollection = BoardCollection.valueOf(list);
        return boardCollection;
    }

    private static void generateByDimensionInvols(List<Board> list, int[] current, boolean[] occCols, int n, int i) {
        throw new UnsupportedOperationException();
    }

    public static BoardCollection generateByDimension(int n) {
        List<Board> list = new ArrayList<>();
        int[] board = new int[n];
        Arrays.fill(board, -1);
        generateByDimension(list, board, new boolean[n], n, 0);
        BoardCollection boardCollection = BoardCollection.valueOf(list);
        return boardCollection;
    }

    private static void generateByDimension(List<Board> list, int[] current, boolean[] occCols, int n, int i) {
        if (1 == 1)
            throw new UnsupportedOperationException();

        if (i == n) {
            Board board = Board.valueOf(current);
            list.add(board);
            return;
        }

        generateByDimension(list, current, occCols, n, 1 + i);

        for (int j = 0; j < n; j++) {
            throw new UnsupportedOperationException();
        }
    }
}