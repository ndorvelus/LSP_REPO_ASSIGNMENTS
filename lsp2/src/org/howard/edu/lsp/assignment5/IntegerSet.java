package org.howard.edu.lsp.assignment5;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * A mathematical set of integers. A set holds no duplicates, and the order
 * values were added in does not matter.
 *
 * <p>Elements are kept in a {@link TreeSet}, so they are always sorted in
 * ascending order. Every operation that returns an {@code IntegerSet} builds a
 * new, independent set and leaves both operands unchanged.</p>
 */
public class IntegerSet {
    private final TreeSet<Integer> elements;

    /**
     * Creates an empty set.
     */
    public IntegerSet() {
        elements = new TreeSet<>();
    }

    /**
     * Builds a set holding a copy of the given elements. Used internally so
     * returned sets never share storage with the operands.
     *
     * @param source the elements to copy
     */
    private IntegerSet(TreeSet<Integer> source) {
        elements = new TreeSet<>(source);
    }

    /**
     * Removes every element from this set.
     */
    public void clear() {
        elements.clear();
    }

    /**
     * Returns the number of elements in this set.
     *
     * @return the number of unique values stored
     */
    public int length() {
        return elements.size();
    }

    /**
     * Checks whether this set and {@code b} hold exactly the same elements,
     * regardless of insertion order.
     *
     * @param b the set to compare against
     * @return {@code true} if both sets contain the same elements;
     *         {@code false} otherwise, including when {@code b} is null
     */
    public boolean equals(IntegerSet b) {
        if (b == null) {
            return false;
        }
        if (this == b) {
            return true;
        }
        return elements.equals(b.elements);
    }

    /**
     * Compares this set to any object. Two {@code IntegerSet}s are equal when
     * they hold the same elements.
     *
     * @param o the object to compare against
     * @return {@code true} if {@code o} is an {@code IntegerSet} with the same
     *         elements; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IntegerSet)) {
            return false;
        }
        return equals((IntegerSet) o);
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return the hash code of this set's elements
     */
    @Override
    public int hashCode() {
        return elements.hashCode();
    }

    /**
     * Checks whether a value is in this set.
     *
     * @param value the value to look for
     * @return {@code true} if the value is present; {@code false} otherwise
     */
    public boolean contains(int value) {
        return elements.contains(value);
    }

    /**
     * Returns the largest element in this set.
     *
     * @return the largest value
     * @throws IllegalStateException if the set is empty
     */
    public int largest() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("Cannot find largest of an empty set");
        }
        return elements.last();
    }

    /**
     * Returns the smallest element in this set.
     *
     * @return the smallest value
     * @throws IllegalStateException if the set is empty
     */
    public int smallest() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("Cannot find smallest of an empty set");
        }
        return elements.first();
    }

    /**
     * Adds a value to this set. Adding a value that is already present has no
     * effect.
     *
     * @param item the value to add
     */
    public void add(int item) {
        elements.add(item);
    }

    /**
     * Removes a value from this set. Removing a value that is not present has
     * no effect.
     *
     * @param item the value to remove
     */
    public void remove(int item) {
        elements.remove(item);
    }

    /**
     * Returns a new set with every element that is in this set, in {@code b},
     * or in both.
     *
     * @param b the other set
     * @return a new set holding the union; neither input is modified
     */
    public IntegerSet union(IntegerSet b) {
        IntegerSet result = new IntegerSet(elements);
        result.elements.addAll(b.elements);
        return result;
    }

    /**
     * Returns a new set with only the elements found in both this set and
     * {@code b}.
     *
     * @param b the other set
     * @return a new set holding the intersection; neither input is modified
     */
    public IntegerSet intersect(IntegerSet b) {
        IntegerSet result = new IntegerSet(elements);
        result.elements.retainAll(b.elements);
        return result;
    }

    /**
     * Returns a new set with the elements in this set that are not in
     * {@code b} (this minus b).
     *
     * @param b the set whose elements are removed
     * @return a new set holding the difference; neither input is modified
     */
    public IntegerSet diff(IntegerSet b) {
        IntegerSet result = new IntegerSet(elements);
        result.elements.removeAll(b.elements);
        return result;
    }

    /**
     * Returns a new set with the elements in {@code b} that are not in this
     * set (b minus this). This is the assignment's definition of complement.
     *
     * @param b the set whose elements are kept when absent from this set
     * @return a new set holding the complement; neither input is modified
     */
    public IntegerSet complement(IntegerSet b) {
        IntegerSet result = new IntegerSet(b.elements);
        result.elements.removeAll(elements);
        return result;
    }

    /**
     * Checks whether every element of this set is also in {@code b}. The
     * empty set is a subset of every set.
     *
     * @param b the possible superset
     * @return {@code true} if this set is a subset of {@code b};
     *         {@code false} otherwise
     */
    public boolean isSubsetOf(IntegerSet b) {
        return b.elements.containsAll(elements);
    }

    /**
     * Checks whether this set and {@code b} share no elements. An empty set
     * is disjoint from every set.
     *
     * @param b the other set
     * @return {@code true} if the sets have no elements in common;
     *         {@code false} otherwise
     */
    public boolean isDisjoint(IntegerSet b) {
        for (int value : elements) {
            if (b.elements.contains(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a new set with the elements that are in exactly one of the two
     * sets.
     *
     * @param b the other set
     * @return a new set holding the symmetric difference; neither input is
     *         modified
     */
    public IntegerSet symmetricDiff(IntegerSet b) {
        IntegerSet result = union(b);
        result.elements.removeAll(intersect(b).elements);
        return result;
    }

    /**
     * Checks whether this set has no elements.
     *
     * @return {@code true} if the set is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Returns the elements in ascending order, separated by a comma and a
     * space, inside square brackets, for example {@code [-3, 0, 8, 15]}. An
     * empty set returns {@code []}.
     *
     * @return the formatted set
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Iterator<Integer> it = elements.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
