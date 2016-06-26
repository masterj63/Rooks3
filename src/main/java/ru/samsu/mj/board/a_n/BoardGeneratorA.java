package ru.samsu.mj.board.a_n;

import ru.samsu.mj.board.Board;
import ru.samsu.mj.collection.BoardCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides two public methods for generating set of boards and for generating set of boards-involutions in root system of type <b>A</b><sub>n-1</sub>.
 * The dimension is <b>n x n</b>.
 */
public class BoardGeneratorA {
    /**
     * @param n the dimension of the boards.
     * @return involutions of type <b>A</b> of the dimension given.
     */
    public static BoardCollection generateByDimensionInvols(int n) {
        List<Board> list = new ArrayList<>();
        int[] board = new int[n];
        Arrays.fill(board, -1);
        generateByDimensionInvols(list, board, new boolean[n], n, 0);
        BoardCollection boardCollection = BoardCollection.valueOf(list);
        return boardCollection;
    }

    private static void generateByDimensionInvols(List<Board> list, int[] current, boolean[] occCols, int n, int i) {
        if (i == n) {
            Board board = Board.valueOf(current);
            list.add(board);
            return;
        }

        generateByDimensionInvols(list, current, occCols, n, 1 + i);

        int max = i;
        for (int j = 0; j < max; j++) {
            //TODO check these conditions
            if (occCols[i])
                continue;
            if (occCols[j])
                continue;
            if (current[i] != -1)
                continue;
            if (current[j] != -1)
                continue;

            occCols[j] = true;
            current[i] = j;
            generateByDimensionInvols(list, current, occCols, n, 1 + i);
            occCols[j] = false;
            current[i] = -1;
        }
    }

    /**
     * @param n the dimension of the boards.
     * @return involutions of type <b>A</b> of the dimension given.
     */
    public static BoardCollection generateByDimension(int n) {
        List<Board> list = new ArrayList<>();
        int[] board = new int[n];
        Arrays.fill(board, -1);
        generateByDimension(list, board, new boolean[n], n, 0);
        BoardCollection boardCollection = BoardCollection.valueOf(list);
        return boardCollection;
    }

    private static void generateByDimension(List<Board> list, int[] current, boolean[] occCols, int n, int i) {
        if (i == n) {
            Board board = Board.valueOf(current);
            list.add(board);
            return;
        }

        generateByDimension(list, current, occCols, n, 1 + i);

        int max = i;
        for (int j = 0; j < max; j++) {
            //TODO check these conditions
            if (occCols[j])
                continue;
            if (current[i] != -1)
                continue;

            occCols[j] = true;
            current[i] = j;
            generateByDimension(list, current, occCols, n, 1 + i);
            occCols[j] = false;
            current[i] = -1;
        }
    }
}