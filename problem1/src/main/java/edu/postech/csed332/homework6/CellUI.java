package edu.postech.csed332.homework6;

import edu.postech.csed332.homework6.events.DisabledEvent;
import edu.postech.csed332.homework6.events.EnabledEvent;
import edu.postech.csed332.homework6.events.Event;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
            setDocument(new PlainDocument() {
                @Override
                public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                    if (str == null)
                        return;

                    try {
                        Integer.parseInt(str);
                    } catch (NumberFormatException ex) {
                        return;
                    }

                    if (getLength() + str.length() <= 1)
                        super.insertString(offs, str, a);
                }
            });

            getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    int number;
                    try {
                        number = Integer.parseInt(e.getDocument().getText(0, 1));
                    } catch (NumberFormatException | BadLocationException ex) {
                        return;
                    }

                    Optional<Integer> prev = cell.getNumber();
                    // The same number
                    if (prev.isPresent() && number == prev.get())
                        return;

                    if (prev.isPresent())
                        cell.unsetNumber();
                    cell.setNumber(number);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    cell.unsetNumber();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    // no-op
                }
            });

            addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    // no-op
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    // no-op
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    int number;
                    try {
                        number = Integer.parseInt(String.valueOf(e.getKeyChar()));
                    } catch (NumberFormatException ex) {
                        return;
                    }
                    // Invalid number
                    if (!(1 <= number && number <= 9 && cell.containsPossibility(number)))
                        clearCell(cell);
                }
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
        if (arg instanceof EnabledEvent) setActivate();
        else if (arg instanceof DisabledEvent) setDeactivate();
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
