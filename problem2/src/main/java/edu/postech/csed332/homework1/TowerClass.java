package edu.postech.csed332.homework1;

import java.util.HashSet;
import java.util.Set;

public abstract class TowerClass implements Tower {

    protected final GameBoard board;
    protected Position pos;
    protected boolean isGround;

    public TowerClass(GameBoard board) {
        this.board = board;
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Override
    public Set<Monster> attack() {
        Set<Monster> monsters = board.getMonsters();
        Set<Monster> attackedMonsters = new HashSet<>();
        for (Monster monster : monsters) {
            if (monster.getPosition().getDistance(getPosition()) <= 1 && monster.isGround() == isGround)
                attackedMonsters.add(monster);
        }

        return attackedMonsters;
    }

    @Override
    public boolean isGround() {
        return isGround;
    }

    @Override
    public GameBoard getBoard() {
        return board;
    }

    @Override
    public Position getPosition() {
        return pos;
    }

    public void setPos(Position p) {
        pos = p;
    }
}
