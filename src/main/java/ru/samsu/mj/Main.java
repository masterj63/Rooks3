package ru.samsu.mj;

import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.samsu.mj.board.Board;
import ru.samsu.mj.board.BoardGenerator;
import ru.samsu.mj.collection.BoardCollection;
import ru.samsu.mj.collection.SortedBoardCollection;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

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
                if (t % 2 != 0 || t <= 0 || 14 <= t)
                    throw new IllegalArgumentException("Must be even in [2...12]");
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
        log.info(format("Finished generating (%d) boards.", boards.size()));

        log.info("Started sorting boards.");
        SortedBoardCollection sortedBoards = boards.sort();
        log.info("Finished sorting boards.");

        //VERY NEW HERE
        log.info("started generating involutions.");
        BoardCollection t0 = BoardGenerator.generateInvsByDimension(2 * n - 2);
        log.info(format("finished generating (%d) involutions.", t0.size()));

        log.info("started sorting involutions");
        SortedBoardCollection t1 = t0.sort();
        log.info("finished sorting involutions");


        log.info("started trash");
        HashMap<Board, Board> toKerovMapInv = new HashMap<>();
        HashMap<Board, Board> toKerovMap = new HashMap<>();
        for (Board board : boards) {
            Board kerovC = board.kerov();

            toKerovMap.put(board, kerovC);
            toKerovMapInv.put(kerovC, board);
        }
        log.info("finished trash");

        log.info("started checking");
        boolean ch = check(
                toKerovMap, toKerovMapInv,
                sortedBoards, t1
        );
        log.info("finished checking");
        log.info(String.format("hypothesis is %b", ch));
//        sortedBoards.forEach(b -> log.info(String.format("%s has %d above", b, sortedBoards.closestAbove(b).size())));
    }


    private static boolean check(HashMap<Board, Board> toKerovMap, HashMap<Board, Board> toKerovMapInv,
                                 SortedBoardCollection sortedBoards, SortedBoardCollection sortedKerov) {
        Board board0 = sortedBoards.theLeast();
        return dfsCheck(board0,
                toKerovMap, toKerovMapInv,
                sortedBoards, sortedKerov,
                new HashMap<>());
    }

    private static boolean dfsCheck(Board currentBoard,
                                    HashMap<Board, Board> toKerovMap, HashMap<Board, Board> toKerovMapInv,
                                    SortedBoardCollection sortedBoards, SortedBoardCollection sortedKerov,
                                    Map<Board, Boolean> hypCache) {
        if (hypCache.containsKey(currentBoard))
            return hypCache.get(currentBoard);

        Board currentKerov = toKerovMap.get(currentBoard);
        BoardCollection boardsAbove = sortedBoards.closestAbove(currentBoard);
        BoardCollection kerovsAbove = sortedKerov.closestAbove(currentKerov);
        for (Board board : boardsAbove) {
            Board kerov = toKerovMap.get(board);
            boolean contains = kerovsAbove.contains(kerov);
            log.info("contains.");
            if (!contains) {
                log.info("here :(");
                log.info("");
                log.info("one is " + currentBoard);
                log.info("it has " + boardsAbove.size() + " aboves");
                log.info("they are:");
                boardsAbove.forEach(log::info);
                log.info("");
                log.info("one's kerov is " + currentKerov);
                log.info("it has " + kerovsAbove.size() + " aboves");
                log.info("they are:");
                kerovsAbove.forEach(log::info);
                log.info("");
                log.info("bad above is " + board);
                log.info("it's kerov is " + kerov);
                return false;
            }
            if (!dfsCheck(board, toKerovMap, toKerovMapInv, sortedBoards, sortedKerov, hypCache)) {
                hypCache.put(currentBoard, false);
                return false;
            }
        }

        hypCache.put(currentBoard, true);
        return true;
    }

    public static void main(String[] args) {
        new Main();
    }
}
