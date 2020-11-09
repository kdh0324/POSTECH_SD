package edu.postech.csed332.homework6;

import edu.postech.csed332.homework6.events.DisabledEvent;
import edu.postech.csed332.homework6.events.EnabledEvent;
import edu.postech.csed332.homework6.events.Event;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class CellUI extends JTextField implements Observer {

    /**
     * create a cell UI
     *
     * @param cell a cell model
     */
    CellUI(Cell cell) {
        cell.addObserver(this);
        initCellUI(cell);

        if (cell.getNumber().isEmpty()) {
            addActionListener(e -> {
                int number;
                try {
                    number = Integer.parseInt(getText());
                } catch (NumberFormatException ex) {
                    // Not a number
                    clearCell(cell);
                    return;
                }
                Optional<Integer> prev = cell.getNumber();
                // The same number
                if (prev.isPresent() && number == prev.get())
                    return;
                // Invalid number
                if (!(1 <= number && number <= 9 && cell.containsPossibility(number))) {
                    clearCell(cell);
                    return;
                }
                if (prev.isPresent())
                    cell.unsetNumber();
                cell.setNumber(number);
            });
        }
    }

    private void clearCell(Cell cell) {
        setText("");
        cell.unsetNumber();
    }

    /**
     * Mark this cell UI as active
     */
    public void setActivate() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setEditable(true);
    }

    /**
     * Mark this cell UI as inactive
     */
    public void setDeactivate() {
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setEditable(false);
    }

    /**
     * Whenever a cell is changed, this function is called. EnabledEvent and DisabledEvent are handled here.
     * If the underlying cell is enabled, mark this cell UI as active. If the underlying cell is disabled, mark
     * this cell UI as inactive.
     *
     * @param caller the subject
     * @param arg    an argument passed to caller
     */
    @Override
    public void update(Subject caller, Event arg) {
        if(arg instanceof EnabledEvent) setActivate();
        else if(arg instanceof DisabledEvent) setDeactivate();
    }

    /**
     * Initialize this cell UI
     *
     * @param cell a cell model
     */
    private void initCellUI(Cell cell) {
        setFont(new Font("Times", Font.BOLD, 30));
        setHorizontalAlignment(JTextField.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        if (cell.getType() == Cell.Type.EVEN)
            setBackground(Color.LIGHT_GRAY);

        if (cell.getNumber().isPresent()) {
            setForeground(Color.BLUE);
            setText(cell.getNumber().get().toString());
            setEditable(false);
        }
    }
}
