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
import java.util.*;
import java.util.stream.Collectors;

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

        log.info("Started initializing UI.");
        Graph<String, String> graph = new UndirectedSparseGraph<>();
        StaticLayout<String, String> layout = new StaticLayout<>(graph);
        VisualizationViewer<String, String> viewer = new VisualizationViewer<>(layout);
        GraphZoomScrollPane zoomPane = new GraphZoomScrollPane(viewer);

        viewer.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.SE);
        viewer.setGraphMouse(new DefaultModalGraphMouse<>());
        viewer.getRenderContext().setVertexLabelTransformer(x -> x);

        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(zoomPane);
        jf.pack();
        jf.setVisible(true);
        log.info("Finished initializing UI.");

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

        //NEW here
//        ArrayList<Board> kerovBoards = new ArrayList<>(boards.size());
//        HashMap<Board, Board> toKerovMap = new HashMap<>();
//        HashMap<Board, Board> toKerovMapInv = new HashMap<>();
//        for (Board board : boards) {
//            Board kerovC = board.kerov();
//            kerovBoards.add(kerovC);
//
//            toKerovMap.put(board, kerovC);
//            toKerovMapInv.put(kerovC, board);
//        }
//        BoardCollection kerovCollection = BoardCollection.valueOf(kerovBoards);
//        SortedBoardCollection sortedKerov = kerovCollection.sort();
//
//        boolean ch = check(
//                toKerovMap, toKerovMapInv,
//                sortedBoards, sortedKerov
//        );
//        log.info(String.format("hypothesis is %b", ch));

        //VERY NEW HERE
        BoardCollection t0 = BoardGenerator.generateByDimension(2 * n - 2);
        List<Board> t1 = new LinkedList<>(t0).stream()
                .filter(Board::isInvolution)
                .collect(Collectors.toList());
        BoardCollection t2 = BoardCollection.valueOf(t1);
        SortedBoardCollection t3 = t2.sort();


        HashMap<Board, Board> toKerovMapInv = new HashMap<>();
        HashMap<Board, Board> toKerovMap = new HashMap<>();
        for (Board board : boards) {
            Board kerovC = board.kerov();

            toKerovMap.put(board, kerovC);
            toKerovMapInv.put(kerovC, board);
        }
        boolean ch = check(
                toKerovMap, toKerovMapInv,
                sortedBoards, t3
        );
        log.info(String.format("hypothesis is %b", ch));
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
