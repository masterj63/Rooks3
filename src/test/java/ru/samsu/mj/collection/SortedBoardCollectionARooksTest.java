package ru.samsu.mj.collection;

import org.junit.Test;
import ru.samsu.mj.board.a_n.BoardGeneratorA;

import static org.junit.Assert.assertEquals;

/**
 * See https://oeis.org/A000217 for rooks.
 */
public class SortedBoardCollectionARooksTest extends MaxRankTest {
    @Test
    public void maxRankgenerateByDimension2() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(2);
        int maxRank = maxRank(boards.sort());
        assertEquals(1, maxRank);
    }

    @Test
    public void maxRankgenerateByDimension3() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(3);
        int maxRank = maxRank(boards.sort());
        assertEquals(3, maxRank);
    }

    @Test
    public void maxRankgenerateByDimension4() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(4);
        int maxRank = maxRank(boards.sort());
        assertEquals(6, maxRank);
    }

    @Test
    public void maxRankgenerateByDimension5() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(5);
        int maxRank = maxRank(boards.sort());
        assertEquals(10, maxRank);
    }

    @Test
    public void maxRankgenerateByDimension6() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(6);
        int maxRank = maxRank(boards.sort());
        assertEquals(15, maxRank);
    }

    @Test
    public void maxRankgenerateByDimension7() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(7);
        int maxRank = maxRank(boards.sort());
        assertEquals(21, maxRank);
    }

    @Test
    public void maxRankgenerateByDimension8() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(8);
        int maxRank = maxRank(boards.sort());
        assertEquals(28, maxRank);
    }

    @Test
    public void maxRankgenerateByDimension9() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(9);
        int maxRank = maxRank(boards.sort());
        assertEquals(36, maxRank);
    }
}