package ru.samsu.mj.board;

public interface PartiallyComparable<E> {
    public PartialComparison partiallyCompare(E e);
}
