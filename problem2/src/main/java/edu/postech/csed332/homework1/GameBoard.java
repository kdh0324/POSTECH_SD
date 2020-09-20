package edu.postech.csed332.homework1;

import java.util.HashSet;
import java.util.Set;

/**
 * A game board contains a set of units and a goal position. A game consists
 * of a number of rounds. For each round, all units perform their actions:
 * a monster can escape or move, and a tower can attack nearby monsters.
 * The following invariant conditions must be maintained throught the game:
 * <p>
 * (a) The position of each unit is inside the boundaries.
 * (b) Different ground units cannot be on the same tile.
 * (c) Different air units cannot be on the same tile.
 * <p>
 * NOTE: do not modify the signature of public methods (which will be
 * used for GUI and grading). But you can feel free to add new fields
 * or new private methods if needed.
 */
public class GameBoard {
    private final Position goal;
    private final int width, height;

    private Set<Monster> monsters;
    private Set<Tower> towers;
    private int killed = 0;
    private int escaped = 0;

    private Set<Position> killedPos;

    /**
     * Creates a game board with a given width and height. The goal position
     * is set to the middle of the end column.
     *
     * @param width  of this board
     * @param height of this board
     */
    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        goal = new Position(width - 1, height / 2);

        monsters = new HashSet<>();
        towers = new HashSet<>();
        killedPos = new HashSet<>();
    }

    /**
     * Places a unit at a given position into this board.
     *
     * @param obj a unit to be placed
     * @param p   the position of obj
     * @throws IllegalArgumentException if p is outside the bounds of the board.
     */
    public void placeUnit(Unit obj, Position p) throws IllegalArgumentException {
        if (isOut(p))
            throw new IllegalArgumentException();

        if (obj instanceof Tower) {
            towers.add((Tower) obj);
            if (obj.isGround())
                ((GroundTower) obj).setPos(p);
            else
                ((AirTower) obj).setPos(p);
        } else {
            monsters.add((Monster) obj);
            if (obj.isGround())
                ((GroundMob) obj).setPos(p);
            else
                ((AirMob) obj).setPos(p);
        }

        if (!isValid()) {
            if (obj instanceof Tower)
                towers.remove(obj);
            else
                monsters.remove(obj);
        }
    }

    /**
     * Clears this game board. That is, all units are removed, and all numbers
     * for game statistics are reset to 0.
     */
    public void clear() {
        monsters = null;
        monsters = new HashSet<>();
        towers = null;
        towers = new HashSet<>();
        killed = 0;
        escaped = 0;
        killedPos = new HashSet<>();
    }

    /**
     * Returns the set of units at a given position in the board. Note that
     * multiple units can exist at the same position (e.g., ground and air)
     *
     * @param p a position
     * @return the set of units at position p
     */
    public Set<Unit> getUnitsAt(Position p) {
        Set<Unit> units = new HashSet<>();

        for (Monster monster : monsters)
            if (monster.getPosition().getDistance(p) == 0)
                units.add(monster);
        for (Tower tower : towers)
            if (tower.getPosition().getDistance(p) == 0)
                units.add(tower);

        return units;
    }

    /**
     * Returns the set of all monsters in this board.
     *
     * @return the set of all monsters
     */
    public Set<Monster> getMonsters() {
        return monsters;
    }

    /**
     * Returns the set of all towers in this board.
     *
     * @return the set of all towers
     */
    public Set<Tower> getTowers() {
        return towers;
    }

    /**
     * Returns the position of a given unit
     *
     * @param obj a unit
     * @return the position of obj
     */
    public Position getPosition(Unit obj) {
        return obj.getPosition();
    }

    /**
     * Proceeds one round of a game. The behavior of this method is as follows:
     * (1) Every monster at the goal position escapes from the game.
     * (2) Each tower attacks nearby remaining monsters (using the attack method).
     * (3) All remaining monsters (neither escaped nor attacked) moves (using the goal method).
     */
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public void step() {
        Set<Monster> removed = new HashSet<>();
        for (Monster monster : monsters) {
            Position pos = monster.move();
            if (pos == null) {
                removed.add(monster);
                killedPos.add(monster.getPosition());
                killed++;
            }
            else if (pos.equals(goal)) {
                removed.add(monster);
                escaped++;
            } else
                ((MobClass) monster).setPos(pos);
        }
        monsters.removeAll(removed);
        for (Tower tower : towers) {
            Set<Monster> attacked = tower.attack();
            monsters.removeAll(attacked);
            killed += attacked.size();
        }
    }

    /**
     * Checks whether the following invariants hold in the current game board:
     * (a) All units are in the boundaries.
     * (b) Different ground units cannot be on the same tile.
     * (c) Different air units cannot be on the same tile.
     *
     * @return true if there is no problem. false otherwise.
     */
    public boolean isValid() {
        Set<Position> positions = new HashSet<>();

        for (Monster monster : monsters) {
            Position pos = monster.getPosition();
            if (isOut(pos)) {
                System.out.println("Out mob");
                return false;
            }

            if (positions.contains(pos)) {
                Set<Unit> units = getUnitsAt(pos);
                for (Unit unit : units)
                    if (!unit.equals(monster) && unit.isGround() == monster.isGround()) {
                        System.out.println("Same type mob");
                        return false;
                    }
            }
            positions.add(pos);
        }
        for (Tower tower : towers) {
            Position pos = tower.getPosition();
            if (isOut(pos)) {
                System.out.println("Out tower");
                return false;
            }

            if (positions.contains(pos)) {
                Set<Unit> units = getUnitsAt(pos);
                for (Unit unit : units)
                    if (!unit.equals(tower) && unit.isGround() == tower.isGround()) {
                        System.out.println("Same type tower");
                        return false;
                    }
            }
            positions.add(pos);
        }
        return true;
    }

    public boolean isOut(Position p) {
        int x = p.getX();
        int y = p.getY();
        return 0 > x || x >= width || 0 > y || y >= height;
    }

    /**
     * Returns the number of the monsters in this board.
     *
     * @return the number of the monsters
     */
    public int getNumMobs() {
        return monsters.size();
    }

    /**
     * Returns the number of the towers in this board.
     *
     * @return the number of the towers
     */
    public int getNumTowers() {
        return towers.size();
    }

    /**
     * Returns the number of the monsters removed so far in this game.
     * This number will be reset to 0 by the clear method.
     *
     * @return the number of the monsters removed
     */
    public int getNumMobsKilled() {
        return killed;
    }

    /**
     * Returns the number of the monsters escaped so far in this game
     * This number will be reset to 0 by the clear method.
     *
     * @return the number of the monsters escaped
     */
    public int getNumMobsEscaped() {
        return escaped;
    }

    /**
     * Returns the width of this board.
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of this board.
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the goal position where the monster can escape.
     *
     * @return the goal position of this game
     */
    public Position getGoalPosition() {
        return goal;
    }

    public Set<Position> getKilledPos() {
        return killedPos;
    }
}
