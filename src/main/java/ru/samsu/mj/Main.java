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
import ru.samsu.mj.board.c_n.BoardGeneratorC;
import ru.samsu.mj.collection.BoardCollection;
import ru.samsu.mj.collection.SortedBoardCollection;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * The main class where the project begins.
 */
class Main {
    private static final Logger log = LogManager.getLogger(Main.class);
    private static final int DEFAULT_DIMENSION = 4;

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
        BoardCollection boards = BoardGeneratorC.generateByDimension(n);
        log.info(format("Finished generating (%d) boards.", boards.size()));

        log.info("Started sorting boards.");
        SortedBoardCollection sortedBoards = boards.sort();
        log.info("Finished sorting boards.");

        log.info("Started initializing UI.");
        drawGraph(sortedBoards, String.format("Boards, %d-boards", n));
        log.info("Finished initializing UI.");

        HashMap<Board, Board> toKerovMap = new HashMap<>();
        for (Board board : boards) {
            Board kerovC = board.kerov();
            toKerovMap.put(board, kerovC);
        }

        log.info("Started generating invols.");
        BoardCollection invols = BoardGeneratorC.generateByDimensionInvols(2 * n - 2);
        log.info("Generated ({}) invols.", invols.size());
        log.info("Started sorting invols.", "");
        SortedBoardCollection sortedInvols = invols.sort();
        log.info("Sorted invols.", "");
        log.info("Checking invols...");
        boolean ch = check(
                toKerovMap,
                sortedBoards,
                sortedInvols
        );
        log.info(String.format("hypothesis is %b", ch));

        drawGraph(sortedInvols, String.format("Involutions, %d-boards", 2 * n - 2));
    }

    /**
     * Checks that <b>D_1 &rarr;<sub>R</sub> D_2</b> (as a rook placement) iff <b>K(D_1) &rarr;<sub>I</sub> K(D_2)</b> (as an involution).
     *
     * @param toKerovMap   <b>D &isin; R<sub>n x n</sub> &rarr; K(D) &isin; I<sub>2n-2 x 2n-2</sub></b>.
     * @param sortedBoards sorted <b>R<sub>n x n</sub></b>.
     * @param sortedInvols sorted <b>I<sub>2n-2 x 2n-2</sub></b>.
     * @return whether or not the hypothesis is true.
     */
    private static boolean check(HashMap<Board, Board> toKerovMap,
                                 SortedBoardCollection sortedBoards,
                                 SortedBoardCollection sortedInvols) {
        Board board0 = sortedBoards.theLeast();
        return dfsCheck(board0,
                toKerovMap,
                sortedBoards, sortedInvols,
                new HashMap<>());
    }

    /**
     * Recursively searches for the hypothesis failure. Just a DFS, see `check`.
     *
     * @param currentBoard current board requiring the check. This function the recurs from every successive.
     * @param toKerovMap   <b>D &isin; R<sub>n x n</sub> &rarr; K(D) &isin; I<sub>2n-2 x 2n-2</sub></b>.
     * @param sortedBoards sorted <b>R<sub>n x n</sub></b>.
     * @param sortedInvols sorted <b>I<sub>2n-2 x 2n-2</sub></b>.
     * @param hypCache
     * @return whether or not the hypothesis is true.
     */
    private static boolean dfsCheck(Board currentBoard,
                                    HashMap<Board, Board> toKerovMap,
                                    SortedBoardCollection sortedBoards,
                                    SortedBoardCollection sortedInvols,
                                    Map<Board, Boolean> hypCache) {
        if (hypCache.containsKey(currentBoard))
            return hypCache.get(currentBoard);

        Board currentKerov = toKerovMap.get(currentBoard);
        BoardCollection boardsAbove = sortedBoards.closestAbove(currentBoard);
        BoardCollection inolsAbove = sortedInvols.closestAbove(currentKerov);
        for (Board board : boardsAbove) {
            Board kerov = toKerovMap.get(board);
            boolean contains = inolsAbove.contains(kerov);
//            log.info("contains.");
            if (!contains) {
                log.info("here :( it begins");

                log.info("currentBoard = ");
                log.info(currentBoard);

                log.info("currentAbove = ");
                log.info(board);

                log.info("currentKerov = ");
                log.info(currentKerov);

                log.info("kerovAbove = ");
                log.info(kerov);

                log.info("it ends");
                return false;
            }
            if (!dfsCheck(board, toKerovMap, sortedBoards, sortedInvols, hypCache)) {
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

    /**
     * Plots the Hasse diagram for `sortedBoards`.
     *
     * @param sortedBoards the set to plot.
     * @param frameTitle   a string to be printed on a JFrame bar.
     */
    private void drawGraph(SortedBoardCollection sortedBoards, String frameTitle) {
        Graph<String, String> graph = new UndirectedSparseGraph<>();
        StaticLayout<String, String> layout = new StaticLayout<>(graph);
        VisualizationViewer<String, String> viewer = new VisualizationViewer<>(layout);
        GraphZoomScrollPane zoomPane = new GraphZoomScrollPane(viewer);

        viewer.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.SE);
        viewer.setGraphMouse(new DefaultModalGraphMouse<>());
        viewer.getRenderContext().setVertexLabelTransformer(x -> x);

        JFrame jf = new JFrame(frameTitle);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.add(zoomPane);
        jf.pack();
        jf.setVisible(true);

        ArrayDeque<Board> queue = new ArrayDeque<>();
        queue.add(sortedBoards.theLeast());

        HashMap<Board, Integer> layer = new HashMap<>();
        layer.put(sortedBoards.theLeast(), 0);

        HashMap<Integer, Integer> verticalCount = new HashMap<>();
        verticalCount.put(0, 0);

        log.info("Started drawing graph.");
        final int A = 800;
        graph.addVertex(sortedBoards.theLeast().html());
        layout.setLocation(sortedBoards.theLeast().html(), new Point2D.Double(0, 0));
        while (!queue.isEmpty()) {
            Board thisBoard = queue.pollFirst();
            String thisHtml = thisBoard.html();
            int nextLayer = 1 + layer.get(thisBoard);

            for (Board nextBoard : sortedBoards.closestAbove(thisBoard)) {
                String nextHtml = nextBoard.html();
                if (!layer.containsKey(nextBoard)) {
                    queue.addLast(nextBoard);
                    layer.put(nextBoard, nextLayer);

                    verticalCount.putIfAbsent(nextLayer, -1);
                    verticalCount.put(nextLayer, 1 + verticalCount.get(nextLayer));//TODO compute?
                    Point2D.Double nextPoint = new Point2D.Double(A * nextLayer, A * verticalCount.get(nextLayer));
                    graph.addVertex(nextHtml);
                    layout.setLocation(nextHtml, nextPoint);
                }

                graph.addEdge(thisHtml + nextHtml, thisHtml, nextHtml);
            }
        }
        log.info("Finished drawing graph.");
    }
}
