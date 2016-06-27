package ru.samsu.mj.collection;

import org.junit.Test;
import ru.samsu.mj.board.a_n.BoardGeneratorA;

import static org.junit.Assert.assertEquals;

/**
 * See https://oeis.org/A000290 for involutions.
 */
public class SortedBoardCollectionAInvolsTest extends MaxRankTest {
    @Test
    public void maxRankgenerateByDimensionInvols2() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimensionInvols(2);
        int maxRank = maxRank(boards.sort());
        assertEquals(1, maxRank);
    }

    @Test
    public void maxRankgenerateByDimensionInvols4() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimensionInvols(4);
        int maxRank = maxRank(boards.sort());
        assertEquals(4, maxRank);
    }

    @Test
    public void maxRankgenerateByDimensionInvols6() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimensionInvols(6);
        int maxRank = maxRank(boards.sort());
        assertEquals(9, maxRank);
    }

    @Test
    public void maxRankgenerateByDimensionInvols8() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimensionInvols(8);
        int maxRank = maxRank(boards.sort());
        assertEquals(16, maxRank);
    }

    @Test
    public void maxRankgenerateByDimensionInvols10() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimensionInvols(10);
        int maxRank = maxRank(boards.sort());
        assertEquals(25, maxRank);
    }
}
