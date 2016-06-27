package ru.samsu.mj.collection;

import ru.samsu.mj.board.Board;

public class MaxRankTest {
    protected static int maxRank(SortedBoardCollection boards) {
        int maxRank = 0;
        Board board = boards.theLeast();
        while (true) {
            BoardCollection closestAbove = boards.closestAbove(board);
            if (closestAbove.isEmpty())
                break;
            board = closestAbove.iterator().next();
            maxRank++;
        }
        return maxRank;
    }
}
