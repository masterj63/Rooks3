package ru.samsu.mj.collection;

import ru.samsu.mj.board.Board;
import ru.samsu.mj.board.PartialComparison;

import java.util.*;
import java.util.stream.Collectors;

public class SortedBoardCollection extends AbstractCollection<Board> {
    private final static BoardCollection EMPTY_BOARD_COLLECTION = BoardCollection.valueOf(Collections.EMPTY_LIST);

    private final Map<Board, BoardCollection> MAP;
    private final Board THE_LEAST_BOARD;
    private final HashSet<Board> ORIGINAL_SET_COLLECTION;

    private SortedBoardCollection(Map<Board, BoardCollection> map, Board theLeastBoard, HashSet<Board> originalCollection) {
        MAP = map;
        THE_LEAST_BOARD = theLeastBoard;
        ORIGINAL_SET_COLLECTION = originalCollection;
    }

    /**
     * A static factory.
     *
     * @param boardCollection Boards to sort.
     * @return new `SortedBoardCollection`.
     */
    static SortedBoardCollection valueOf(Collection<Board> boardCollection) {
        Map<Board, BoardCollection> map = new HashMap<>();
        Board theLeastBoard = null;
        Set<Board> buffer = new HashSet<>();

        // Initialization.
        for (Board board : boardCollection)
            if (board.isEmpty())
                theLeastBoard = board;
            else
                buffer.add(board);

        // Sorting itself.
        Set<Board> lastLayer = Collections.singleton(theLeastBoard);
        while (!buffer.isEmpty()) {
            Set<Board> nextLayer = nextLayer(buffer);
            injectMapping(map, lastLayer, nextLayer);
            buffer.removeAll(nextLayer);
            lastLayer = nextLayer;
        }

        return new SortedBoardCollection(map, theLeastBoard, new HashSet<>(boardCollection));
    }

    /**
     * Injects into `map` a mapping from every Board of `lastLayer` to a set of its closest above.
     *
     * @param map       to inject in.
     * @param lastLayer boards to choose mappings' keys from.
     * @param nextLayer boards to choose mappings' values from.
     */
    private static void injectMapping(Map<Board, BoardCollection> map, Set<Board> lastLayer, Set<Board> nextLayer) {
        for (Board oneBoard : lastLayer) {
            Set<Board> imageSet = nextLayer.stream().
                    filter(anotherBoard ->
                            oneBoard.partiallyCompare(anotherBoard) == PartialComparison.LESS)
                    .collect(Collectors.toSet());// and `oneBoard` is its key
            map.put(oneBoard, BoardCollection.valueOf(imageSet));
        }
    }

    /**
     * @param buffer --- the set of `Board`s to choose from.
     * @return the subset of `Board`s that are no greater than any other one in `buffer`. The subset of minimals in other words.
     */
    private static Set<Board> nextLayer(Set<Board> buffer) {
        Set<Board> result = new HashSet<>();
        for (Board oneBoard : buffer) {
            boolean isGreaterThanSome = false;
            for (Board anotherBoard : buffer) {
                if (oneBoard.partiallyCompare(anotherBoard) == PartialComparison.GREATER) {
                    isGreaterThanSome = true;
                    break;
                }
            }
            if (isGreaterThanSome)
                continue;
            result.add(oneBoard);
        }
        return result;
    }

    /**
     * @return the least board in the collection.
     */
    public Board theLeast() {
        return THE_LEAST_BOARD;
    }

    /**
     * @param board is any Board in the collection.
     * @return set of the closest boards above from the collection.
     */
    public BoardCollection closestAbove(Board board) {
        return MAP.getOrDefault(board, EMPTY_BOARD_COLLECTION);
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("will be implemented eventually");
    }

    @Override
    public boolean contains(Object o) {
        return ORIGINAL_SET_COLLECTION.contains(o);
    }

    @Override
    public int size() {
        return MAP.size();
    }
}
