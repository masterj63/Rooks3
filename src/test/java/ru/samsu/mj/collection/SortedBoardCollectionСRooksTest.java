package ru.samsu.mj.collection;

import org.junit.Test;
import ru.samsu.mj.board.c_n.BoardGeneratorC;

import static org.junit.Assert.assertEquals;


/**
 * See https://oeis.org/A000290 for rooks.
 */
public class SortedBoardCollectionСRooksTest extends MaxRankTest {
    @Test
    public void generateByDimension2() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimension(2);
        int maxRank = maxRank(boards.sort());
        assertEquals(1, maxRank);
    }

    @Test
    public void generateByDimension4() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimension(4);
        int maxRank = maxRank(boards.sort());
        assertEquals(4, maxRank);
    }

    @Test
    public void generateByDimension6() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimension(6);
        int maxRank = maxRank(boards.sort());
        assertEquals(9, maxRank);
    }

    @Test
    public void generateByDimension8() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimension(8);
        int maxRank = maxRank(boards.sort());
        assertEquals(16, maxRank);
    }

    @Test
    public void generateByDimension10() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimension(10);
        int maxRank = maxRank(boards.sort());
        assertEquals(25, maxRank);
    }

    @Test
    public void generateByDimension12() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimension(12);
        int maxRank = maxRank(boards.sort());
        assertEquals(36, maxRank);
    }
}
