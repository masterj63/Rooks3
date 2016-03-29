package ru.samsu.mj.collection;

import ru.samsu.mj.board.Board;
import ru.samsu.mj.board.PartialComparison;

import java.util.*;
import java.util.stream.Collectors;

public class SortedBoardCollection extends AbstractCollection<Board> {
    private final static BoardCollection EMPTY_BOARD_COLLECTION = BoardCollection.valueOf(Collections.EMPTY_LIST);

    private final Map<Board, BoardCollection> MAP;
    private final Board THE_LEAST_BOARD;

    private SortedBoardCollection(Map<Board, BoardCollection> map, Board theLeastBoard) {
        MAP = map;
        THE_LEAST_BOARD = theLeastBoard;
    }

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

        return new SortedBoardCollection(map, theLeastBoard);
    }

    private static void injectMapping(Map<Board, BoardCollection> map, Set<Board> lastLayer, Set<Board> nextLayer) {
        for (Board oneBoard : lastLayer) {
            Set<Board> imageSet = nextLayer.stream()
                    .filter(anotherBoard -> oneBoard.partiallyCompare(anotherBoard) == PartialComparison.LESS)
                    .collect(Collectors.toSet());// and `oneBoard` is its key
            map.put(oneBoard, BoardCollection.valueOf(imageSet));
        }
    }

    /**
     * @param buffer --- the set of `Board`s to choose from.
     * @return the subset of `Board`s that are no greater than any other one in `buffer`.
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

    public Board theLeast() {
        return THE_LEAST_BOARD;
    }

    public BoardCollection closestAbove(Board board) {
        return MAP.getOrDefault(board, EMPTY_BOARD_COLLECTION);
    }

    @Override
    public Iterator<Board> iterator() {
        return new Iterator<Board>() {
            Iterator<Board> iterator = MAP.keySet().iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Board next() {
                return iterator.next();
            }
        };
    }

    @Override
    public int size() {
        return MAP.size();
    }
}
