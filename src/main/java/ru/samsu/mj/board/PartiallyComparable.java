package ru.samsu.mj.board;

public interface PartiallyComparable<E> {
    PartialComparison partiallyCompare(E e);
}
