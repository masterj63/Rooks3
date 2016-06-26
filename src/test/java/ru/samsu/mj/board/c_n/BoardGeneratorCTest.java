package ru.samsu.mj.board.c_n;

import org.junit.Test;
import ru.samsu.mj.collection.BoardCollection;

import static org.junit.Assert.assertEquals;

/**
 * See https://oeis.org/A002872 for rooks.
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
}