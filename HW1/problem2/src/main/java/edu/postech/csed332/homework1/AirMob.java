package edu.postech.csed332.homework1;

/**
 * An air monster that moves towards to the goal position of the board, while
 * satisfying the game board invariants. The class AirMob implements the interface
 * Monster.
 * Feel free to modify this file, e.g., adding new fields or methods. If needed,
 * you can define a new (abstract) super class that this class inherits.
 *
 * @see GameBoard#isValid
 */
public class AirMob extends MobClass {

    public AirMob(GameBoard board) {
        super(board);
        isGround = false;
    }
}
