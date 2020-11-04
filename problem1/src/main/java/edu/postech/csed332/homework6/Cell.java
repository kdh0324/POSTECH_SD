package edu.postech.csed332.homework6;

import edu.postech.csed332.homework6.events.DisabledEvent;
import edu.postech.csed332.homework6.events.EnabledEvent;
import edu.postech.csed332.homework6.events.SetNumberEvent;
import edu.postech.csed332.homework6.events.UnsetNumberEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A cell that has a number and a set of possibilities. Even cells can contain only even numbers, and odd cells can
 * contain only odd numbers. A cell may have a number of observers, and notifies events to the observers.
 */
public class Cell extends Subject {
    enum Type {EVEN, ODD}

    //TODO: add private member variables for Board
    private int number;
    private final Type type;
    private final List<Group> groups = new ArrayList<>();

    /**
     * Creates an empty cell with a given type. Initially, no number is assigned.
     *
     * @param type EVEN or ODD
     */
    public Cell(@NotNull Type type) {
        this.type = type;
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
        return Optional.of(number);
    }

    /**
     * Sets a number of this cell and notifies a SetNumberEvent, provided that the cell has previously no number
     * and the given number to be set is in the set of possibilities.
     *
     * @param number the number
     */
    public void setNumber(int number) {
        notifyObservers(new SetNumberEvent(number));

        if (containsPossibility(number)) {
            notifyObservers(new DisabledEvent());
            this.number = number;
        }
    }

    /**
     * Removes the number of this cell and notifies an UnsetNumberEvent, provided that the cell has a number.
     */
    public void unsetNumber() {
        notifyObservers(new UnsetNumberEvent(number));
        notifyObservers(new EnabledEvent());
    }

    /**
     * Adds a group for this cell. This methods should also call addObserver(group).
     *
     * @param group
     */
    public void addGroup(@NotNull Group group) {
        addObserver(group);
        groups.add(group);
    }

    /**
     * Returns true if a given number is in the set of possibilities
     *
     * @param n a number
     * @return true if n is in the set of possibilities
     */
    @NotNull
    public Boolean containsPossibility(int n) {
        if ((n % 2 == 1 && type == Type.EVEN) || (n % 2 == 0 && type == Type.ODD))
            return false;

        for (Group group: groups)
            if (!group.isAvailable(n))
                return false;
        return true;
    }

    /**
     * Returns true if the cell has no possibility
     *
     * @return true if the set of possibilities is empty
     */
    @NotNull
    public Boolean emptyPossibility() {
        int i = type == Type.EVEN? 2 : 1;

        while (i < 10) {
            if (containsPossibility(i))
                return true;
            i += 2;
        }
        return false;
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
        //TODO: implement this
    }

    /**
     * Removes the possibility of a given number. Notifies a DisabledEvent if the set of possibilities becomes empty.
     * Note that even (resp., odd) cells have only even (resp., odd) possibilities.
     *
     * @param number the number
     */
    public void removePossibility(int number) {
        //TODO: implement this
    }
}
