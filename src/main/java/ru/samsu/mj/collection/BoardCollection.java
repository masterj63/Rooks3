package ru.samsu.mj.collection;


import ru.samsu.mj.board.Board;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class BoardCollection extends AbstractCollection<Board> {
    private final Collection<Board> COLLECTION;

    private BoardCollection(Collection<Board> collection) {
        COLLECTION = collection;
    }

    public static BoardCollection valueOf(Collection<Board> boards) {
        Collection<Board> unmodifiableBoards = Collections.unmodifiableCollection(boards);
        BoardCollection boardCollection = new BoardCollection(unmodifiableBoards);
        return boardCollection;
    }

    @Override
    public Iterator<Board> iterator() {
        return COLLECTION.iterator();
    }

    @Override
    public int size() {
        return COLLECTION.size();
    }

    public SortedBoardCollection sort() {
        return SortedBoardCollection.valueOf(COLLECTION);
    }
}
