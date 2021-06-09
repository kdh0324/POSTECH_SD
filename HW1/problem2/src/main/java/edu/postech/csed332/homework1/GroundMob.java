package edu.postech.csed332.homework1;

/**
 * A ground monster that moves towards to the goal position of the board, while
 * satisfying the game board invariants. The class GroundMob implements the interface
 * Monster.
 * Feel free to modify this file, e.g., adding new fields or methods. If needed,
 * you can define a new (abstract) super class that this class inherits.
 *
 * @see GameBoard#isValid
 */
public class GroundMob extends MobClass {

    public GroundMob(GameBoard board) {
        super(board);
        isGround = true;
    }
}
