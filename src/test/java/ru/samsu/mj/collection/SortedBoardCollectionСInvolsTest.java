package ru.samsu.mj.collection;

import org.junit.Test;
import ru.samsu.mj.board.c_n.BoardGeneratorC;

import static org.junit.Assert.assertEquals;

/**
 * See https://oeis.org/A000217 for rooks.
 */
public class SortedBoardCollectionСInvolsTest extends MaxRankTest {
    @Test
    public void generateByDimensionInvols2() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimensionInvols(2);
        int maxRank = maxRank(boards.sort());
        assertEquals(1, maxRank);
    }

    @Test
    public void generateByDimensionInvols4() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimensionInvols(4);
        int maxRank = maxRank(boards.sort());
        assertEquals(3, maxRank);
    }

    @Test
    public void generateByDimensionInvols6() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimensionInvols(6);
        int maxRank = maxRank(boards.sort());
        assertEquals(6, maxRank);
    }

    @Test
    public void generateByDimensionInvols8() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimensionInvols(8);
        int maxRank = maxRank(boards.sort());
        assertEquals(10, maxRank);
    }

    @Test
    public void generateByDimensionInvols10() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimensionInvols(10);
        int maxRank = maxRank(boards.sort());
        assertEquals(15, maxRank);
    }

    @Test
    public void generateByDimensionInvols12() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimensionInvols(12);
        int maxRank = maxRank(boards.sort());
        assertEquals(21, maxRank);
    }

    @Test
    public void generateByDimensionInvols14() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimensionInvols(14);
        int maxRank = maxRank(boards.sort());
        assertEquals(28, maxRank);
    }
}
