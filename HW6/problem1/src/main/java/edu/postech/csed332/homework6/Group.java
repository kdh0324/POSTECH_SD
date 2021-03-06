package edu.postech.csed332.homework6;

import edu.postech.csed332.homework6.events.Event;
import edu.postech.csed332.homework6.events.SetNumberEvent;
import edu.postech.csed332.homework6.events.UnsetNumberEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A group that observes a set of cells, and maintains the invariant: if one of the members has a particular value,
 * none of its other members can have the value as a possibility.
 */
public class Group implements Observer {
    private final Set<Cell> cells = new HashSet<>();

    /**
     * Creates an empty group.
     */
    Group() {
    }

    /**
     * Adds a cell to this group. Use cell.addGroup to register this group.
     *
     * @param cell a cell to be added
     */
    void addCell(Cell cell) {
        cells.add(cell);
        cell.addGroup(this);
    }

    /**
     * Returns true if a given cell is belong to this group
     *
     * @param cell a cell
     * @return true if this group contains cell
     */
    @NotNull
    Boolean contains(@NotNull Cell cell) {
        return cells.contains(cell);
    }

    /**
     * Returns true if a given number is available in the group
     *
     * @param number a number
     * @return true if no cell in the group has a given number.
     */
    @NotNull
    public Boolean isAvailable(int number) {
        for (Cell cell : cells) {
            final Optional<Integer> cellNumber = cell.getNumber();
            if (cellNumber.isPresent() && cellNumber.get() == number)
                return false;
        }
        return true;
    }

    /**
     * Whenever a cell is changed, this function is called. Two kinds of events, SetNumberEvent and UnsetNumberEvent,
     * should be handled here.
     *
     * @param caller the subject
     * @param arg    an argument
     */
    @Override
    public void update(Subject caller, Event arg) {
        if (arg instanceof SetNumberEvent) {
            int number = ((SetNumberEvent) arg).getNumber();

            for (Cell cell : cells)
                if (cell != caller)
                    cell.removePossibility(number);
        } else if (arg instanceof UnsetNumberEvent) {
            int number = ((UnsetNumberEvent) arg).getNumber();

            for (Cell cell : cells)
                cell.addPossibility(number);
        }
    }
}
