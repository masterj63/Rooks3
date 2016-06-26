package ru.samsu.mj.board.c_n;

import org.junit.Test;
import ru.samsu.mj.collection.BoardCollection;

import static org.junit.Assert.assertEquals;

/**
 * See https://oeis.org/A002872 for rooks.
 * See https://oeis.org/A000898 for involutions.
 */
public class BoardGeneratorCTest {
    @Test
    public void generateByDimension2() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimension(2);
        int size = boards.size();
        assertEquals(2, size);
    }

    @Test
    public void generateByDimension4() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimension(4);
        int size = boards.size();
        assertEquals(7, size);
    }

    @Test
    public void generateByDimension6() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimension(6);
        int size = boards.size();
        assertEquals(31, size);
    }

    @Test
    public void generateByDimension8() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimension(8);
        int size = boards.size();
        assertEquals(164, size);
    }

    @Test
    public void generateByDimension10() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimension(10);
        int size = boards.size();
        assertEquals(999, size);
    }

    @Test
    public void generateByDimension12() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimension(12);
        int size = boards.size();
        assertEquals(6841, size);
    }

    @Test
    public void generateByDimension14() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimension(14);
        int size = boards.size();
        assertEquals(51790, size);
    }

    @Test
    public void generateByDimensionInvols() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimensionInvols(2);
        int size = boards.size();
        assertEquals(2, size);
    }

    @Test
    public void generateByDimensionInvols4() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimensionInvols(4);
        int size = boards.size();
        assertEquals(6, size);
    }

    @Test
    public void generateByDimensionInvols6() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimensionInvols(6);
        int size = boards.size();
        assertEquals(20, size);
    }

    @Test
    public void generateByDimensionInvols8() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimensionInvols(8);
        int size = boards.size();
        assertEquals(76, size);
    }

    @Test
    public void generateByDimensionInvols10() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimensionInvols(10);
        int size = boards.size();
        assertEquals(312, size);
    }

    @Test
    public void generateByDimensionInvols12() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimensionInvols(12);
        int size = boards.size();
        assertEquals(1384, size);
    }

    @Test
    public void generateByDimensionInvols14() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimensionInvols(14);
        int size = boards.size();
        assertEquals(6512, size);
    }

    @Test
    public void generateByDimensionInvols16() throws Exception {
        BoardCollection boards = BoardGeneratorC.generateByDimensionInvols(16);
        int size = boards.size();
        assertEquals(32400, size);
    }
}