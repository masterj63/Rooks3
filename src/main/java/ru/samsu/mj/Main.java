package ru.samsu.mj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.samsu.mj.board.BoardGenerator;
import ru.samsu.mj.collection.BoardCollection;
import ru.samsu.mj.collection.SortedBoardCollection;

import javax.swing.*;

import static java.lang.String.format;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);
    private static final int DEFAULT_DIMENSION = 6;

    private Main() {
        final int n;

        {
            int t = -1;
            try {
                String dimension = JOptionPane.showInputDialog(
                        null,
                        "What is the dimension?",
                        Integer.toString(DEFAULT_DIMENSION)
                );
                t = Integer.parseInt(dimension);
                if (t % 2 != 0 || t <= 0 || 12 <= t)
                    throw new IllegalArgumentException("Must be even in [2...10]");
            } catch (IllegalArgumentException e) {
                log.warn("Received incorrect dimension.", e);
                t = DEFAULT_DIMENSION;
            } finally {
                n = t;
                log.info(format("Dimension set to %d.", n));
            }
        }

        log.info("Started generating boards.");
        BoardCollection boards = BoardGenerator.generateByDimension(n);
        log.info(format("Finished generating (%d) boards.", boards));

        log.info("Started sorting boards.");
        SortedBoardCollection sortedBoards = boards.sort();
        log.info("Finished sorting boards.");
    }

    public static void main(String[] args) {
        new Main();
    }
}
