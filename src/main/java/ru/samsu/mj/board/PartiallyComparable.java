package ru.samsu.mj.board;

/**
 * An interface to be implemented by elements of a poset.
 *
 * @param <E> type of the element.
 */
public interface PartiallyComparable<E> {
    /**
     * Partial comparison itself.
     *
     * @param that a value for <b>{@code this}</b> to be compared with.
     * @return the result of a comparison.
     */
    public PartialComparison partiallyCompare(E that);
}
