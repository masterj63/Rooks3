package ru.samsu.mj.board;

import ru.samsu.mj.collection.BoardCollection;

import java.util.ArrayList;
import java.util.List;

public class BoardGenerator {
    public static BoardCollection generateByDimension(int n) {
        List<Board> list = new ArrayList<>();
        generateByDimension(list, new int[n], new boolean[n], n, 0);
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
            if (occCols[j])
                continue;

            occCols[j] = true;
            current[i] = j;
            generateByDimension(list, current, occCols, n, 1 + i);
            occCols[j] = false;
            current[i] = -1;
        }
    }
}
