package ru.samsu.mj.collection;


import ru.samsu.mj.board.Board;

import java.util.*;

public class BoardCollection extends AbstractCollection<Board> {
    private final Collection<Board> COLLECTION;

    private BoardCollection(Collection<Board> collection) {
        COLLECTION = collection;
    }

    /**
     * A static factory.
     *
     * @param boards to create a `BoardCollection` from.
     * @return new `BoardCollection`
     */
    public static BoardCollection valueOf(Collection<Board> boards) {
        Collection<Board> unmodifiableBoards = Collections.unmodifiableCollection(boards);
        BoardCollection boardCollection = new BoardCollection(unmodifiableBoards);
        return boardCollection;
    }

    public BoardCollection valueOfCollection(Collection<Board> collection) {
        ArrayList<Board> boards = new ArrayList<>(collection.size());
        return new BoardCollection(boards);
    }

    @Override
    public Iterator<Board> iterator() {
        return COLLECTION.iterator();
    }

    @Override
    public int size() {
        return COLLECTION.size();
    }

    /**
     * @return the `BoardCollection`, sorted though.
     */
    public SortedBoardCollection sort() {
        return SortedBoardCollection.valueOf(COLLECTION);
    }
}
