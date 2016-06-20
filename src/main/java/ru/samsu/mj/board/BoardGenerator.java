package ru.samsu.mj.board;

import ru.samsu.mj.collection.BoardCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardGenerator {
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

        int max = n / 2 - Math.abs(n / 2 - i);
        for (int j = 0; j < max; j++) {
            int d = n - 1 - i - j;
            int i2 = i + d, j2 = j + d;

            if (occCols[j] || occCols[j2])
                continue;
            if (occCols[i] || occCols[i2])
                continue;

            occCols[j] = occCols[j2] = true;
            current[i] = j;
            current[i2] = j2;
            generateByDimensionInvols(list, current, occCols, n, 1 + i);
            occCols[j] = occCols[j2] = false;
            current[i] = -1;
            current[i2] = -1;
        }
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
        if (i == n) {
            Board board = Board.valueOf(current);
            list.add(board);
            return;
        }

        generateByDimension(list, current, occCols, n, 1 + i);

        int max = n / 2 - Math.abs(n / 2 - i);
        for (int j = 0; j < max; j++) {
            int d = n - 1 - i - j;
            int i2 = i + d, j2 = j + d;

            if (occCols[j] || occCols[j2])
                continue;

            occCols[j] = occCols[j2] = true;
            current[i] = j;
            current[i2] = j2;
            generateByDimension(list, current, occCols, n, 1 + i);
            occCols[j] = occCols[j2] = false;
            current[i] = -1;
            current[i2] = -1;
        }
    }
}
