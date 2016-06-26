package ru.samsu.mj.board.a_n;

import org.junit.Test;
import ru.samsu.mj.collection.BoardCollection;

import static org.junit.Assert.assertEquals;

public class BoardGeneratorATest {
    @Test
    public void generateByDimension2() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(2);
        int size = boards.size();
        assertEquals(2, size);
    }

    @Test
    public void generateByDimension3() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(3);
        int size = boards.size();
        assertEquals(5, size);
    }

    @Test
    public void generateByDimension4() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(4);
        int size = boards.size();
        assertEquals(15, size);
    }

    @Test
    public void generateByDimension5() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(5);
        int size = boards.size();
        assertEquals(52, size);
    }

    @Test
    public void generateByDimension6() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(6);
        int size = boards.size();
        assertEquals(203, size);
    }

    @Test
    public void generateByDimension7() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(7);
        int size = boards.size();
        assertEquals(877, size);
    }

    @Test
    public void generateByDimension8() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(8);
        int size = boards.size();
        assertEquals(4140, size);
    }

    @Test
    public void generateByDimension9() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(9);
        int size = boards.size();
        assertEquals(21147, size);
    }

    @Test
    public void generateByDimension10() throws Exception {
        BoardCollection boards = BoardGeneratorA.generateByDimension(10);
        int size = boards.size();
        assertEquals(115975, size);
    }
}