package edu.postech.csed332.homework6;

import org.jetbrains.annotations.NotNull;

/**
 * An even-odd Sudoku board
 */
public class Board {
    private final Cell[][] cells = new Cell[9][9];
    private final Group[] rowGroups = new Group[9];
    private final Group[] colGroups = new Group[9];
    private final Group[][] squareGroups = new Group[3][3];

    /**
     * Creates an even-odd Sudoku board
     *
     * @param game an initial configuration
     */
    Board(@NotNull GameInstance game) {
        for (int i = 0; i < 9; i++) {
            rowGroups[i] = new Group();
            colGroups[i] = new Group();
            squareGroups[i / 3][i % 3] = new Group();
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Cell.Type type = game.isEven(i, j) ? Cell.Type.EVEN : Cell.Type.ODD;
                cells[i][j] = new Cell(type);
                rowGroups[i].addCell(cells[i][j]);
                colGroups[j].addCell(cells[i][j]);
                squareGroups[i / 3][j / 3].addCell(cells[i][j]);
            }
        }
    }

    /**
     * Returns a cell in the (i+1)-th row of (j+1)-th column, where 0 <= i, j < 9.
     *
     * @param i a row index
     * @param j a column index
     * @return a cell in the (i+1)-th row of (j+1)-th column
     */
    @NotNull
    Cell getCell(int i, int j) {
        return cells[i][j];
    }

    /**
     * Returns a group for the (i+1)-th row, where 0 <= i < 9.
     *
     * @param i a row index
     * @return a group for the (i+1)-th row
     */
    @NotNull
    Group getRowGroup(int i) {
        return rowGroups[i];
    }

    /**
     * Returns a group for the (j+1)-th column, where 0 <= j < 9.
     *
     * @param j a column index
     * @return a group for the (j+1)-th column
     */
    @NotNull
    Group getColGroup(int j) {
        return colGroups[j];
    }

    /**
     * Returns a square group for the (n+1)-th row of (m+1)-th column, where 1 <= n, m <= 3
     *
     * @param n a square row index
     * @param m a square column index
     * @return a square group for the (n+1)-th row of (m+1)-th column
     */
    @NotNull
    Group getSquareGroup(int n, int m) {
        return squareGroups[n][m];
    }
}
