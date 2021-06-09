package edu.postech.csed332.homework1;

/**
 * An air tower that can attack nearby air monsters within 1 tile of distance.
 * For example, an air tower at (x,y) can attack any air monsters at (x-1, y),
 * (x+1, y), (x, y-1), and (x, y+1). The class AirTower implements the interface
 * Tower. TODO: implement all unimplemented methods.
 * Feel free to modify this file, e.g., adding new fields or methods. If needed,
 * you can define a new (abstract) super class that this class inherits.
 */
public class AirTower extends TowerClass {

    public AirTower(GameBoard board) {
        super(board);
        isGround = false;
    }
}
