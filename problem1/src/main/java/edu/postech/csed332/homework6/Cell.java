package edu.postech.csed332.homework6;

import edu.postech.csed332.homework6.events.DisabledEvent;
import edu.postech.csed332.homework6.events.EnabledEvent;
import edu.postech.csed332.homework6.events.SetNumberEvent;
import edu.postech.csed332.homework6.events.UnsetNumberEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A cell that has a number and a set of possibilities. Even cells can contain only even numbers, and odd cells can
 * contain only odd numbers. A cell may have a number of observers, and notifies events to the observers.
 */
public class Cell extends Subject {
    enum Type {EVEN, ODD}
    private int number;
    private final Type type;
    private final Set<Integer> possibilities = new HashSet<>();

    /**
     * Creates an empty cell with a given type. Initially, no number is assigned.
     *
     * @param type EVEN or ODD
     */
    public Cell(@NotNull Type type) {
        this.type = type;
        if (type == Type.ODD)
            possibilities.addAll(List.of(1, 3, 5, 7, 9));
        else
            possibilities.addAll(List.of(2, 4, 6, 8));
        number = 0;
    }

    /**
     * Returns the type of this cell.
     *
     * @return the type
     */
    @NotNull
    public Type getType() {
        return type;
    }

    /**
     * Returns the number of this cell.
     *
     * @return the number; Optional.empty() if no number assigned
     */
    @NotNull
    public Optional<Integer> getNumber() {
        if (number == 0)
            return Optional.empty();
        return Optional.of(number);
    }

    /**
     * Sets a number of this cell and notifies a SetNumberEvent, provided that the cell has previously no number
     * and the given number to be set is in the set of possibilities.
     *
     * @param number the number
     */
    public void setNumber(int number) {
        if (!containsPossibility(number))
            return;

        notifyObservers(new SetNumberEvent(number));
        notifyObservers(new DisabledEvent());
        this.number = number;
    }

    /**
     * Removes the number of this cell and notifies an UnsetNumberEvent, provided that the cell has a number.
     */
    public void unsetNumber() {
        if (number == 0)
            return;
        notifyObservers(new UnsetNumberEvent(number));
        notifyObservers(new EnabledEvent());
        number = 0;
    }

    /**
     * Adds a group for this cell. This methods should also call addObserver(group).
     *
     * @param group Group to add.
     */
    public void addGroup(@NotNull Group group) {
        addObserver(group);
    }

    /**
     * Returns true if a given number is in the set of possibilities
     *
     * @param n a number
     * @return true if n is in the set of possibilities
     */
    @NotNull
    public Boolean containsPossibility(int n) {
        return possibilities.contains(n);
    }

    /**
     * Returns true if the cell has no possibility
     *
     * @return true if the set of possibilities is empty
     */
    @NotNull
    public Boolean emptyPossibility() {
        return possibilities.isEmpty();
    }

    /**
     * Adds the possibility of a given number, if possible, and notifies an EnabledEvent if the set of possibilities,
     * previously empty, becomes non-empty. Even (resp., odd) cells have only even (resp., odd) possibilities. Also,
     * if this number is already used by another cell in the same group with this cell, the number cannot be added to
     * the set of possibilities.
     *
     * @param number the number
     */
    public void addPossibility(int number) {
        if (isInvalidNumber(number))
            return;

        if (possibilities.isEmpty())
            notifyObservers(new EnabledEvent());
        possibilities.add(number);
    }

    /**
     * Removes the possibility of a given number. Notifies a DisabledEvent if the set of possibilities becomes empty.
     * Note that even (resp., odd) cells have only even (resp., odd) possibilities.
     *
     * @param number the number
     */
    public void removePossibility(int number) {
        possibilities.remove(number);
        if (possibilities.isEmpty())
            notifyObservers(new DisabledEvent());
    }

    private boolean isInvalidNumber(int n) {
        return !((n % 2 == 0 && type == Type.EVEN) || (n % 2 == 1 && type == Type.ODD));
    }
}
