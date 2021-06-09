package edu.postech.csed332.homework6;

import javax.swing.*;
import java.awt.*;

public class GameUI {
    private static final int unitSize = 10;
    private static final int width = 45;
    private static final int height = 45;

    final JFrame top;

    public GameUI(Board board) {
        top = new JFrame("Even/Odd Sudoku");
        top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        top.setLayout(new GridLayout(3, 3));

        Dimension dim = new Dimension(unitSize * width, unitSize * height);
        top.setMinimumSize(dim);
        top.setPreferredSize(dim);

        createCellUI(board);

        top.pack();
        top.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Board board = new Board(GameInstanceFactory.createGameInstance());
            new GameUI(board);
        });
    }

    /**
     * Create UI for cells
     * @param board
     */
    private void createCellUI(Board board) {
        CellUI[][] cells = new CellUI[9][9];
        JPanel[][] squares = new JPanel[3][3];

        squareLayoutInit(squares);

        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new CellUI(board.getCell(i, j));
                squares[i / 3][j / 3].add(cells[i][j]);
            }

        addSquaresToTop(squares);
    }

    private void addSquaresToTop(JPanel[][] squares) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                top.add(squares[i][j]);
    }

    private void squareLayoutInit(JPanel[][] squares) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                squares[i][j] = new JPanel();
                final JPanel jPanel = squares[i][j];
                jPanel.setLayout(new GridLayout(3, 3));
                jPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            }
    }
}