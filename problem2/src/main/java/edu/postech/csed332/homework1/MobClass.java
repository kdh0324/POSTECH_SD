package edu.postech.csed332.homework1;

import java.util.Iterator;
import java.util.Set;

public class MobClass implements Monster {

    protected final GameBoard board;
    protected Position pos;
    protected boolean isGround;

    public MobClass(GameBoard board) {
        this.board = board;
    }

    @Override
    public Position move() {
        boolean flag = false;
        Position pos = getPosition();
        int[][] dirs;
        if (pos.getY() < board.getGoalPosition().getY())
            dirs = new int[][]{{1, 0}, {0, 1}, {0, -1}, {-1, 0}};
        else
            dirs = new int[][]{{1, 0}, {0, -1}, {0, 1}, {-1, 0}};

        for (int i = 0; i < 4; i++) {
            int[] dir = dirs[i];
            Position relativePos = pos.getRelativePosition(dir[0], dir[1]);
            if (board.isOut(relativePos))
                continue;

            Set<Unit> units = board.getUnitsAt(relativePos);
            if (units.size() != 0) {
                Iterator<Unit> iter = units.iterator();
                if (!(units.size() == 1 && iter.next().isGround() != isGround))
                    continue;
            }

            for (int j = 0; j < 4; j++) {
                int[] adj = dirs[j];
                Position nextPos = relativePos.getRelativePosition(adj[0], adj[1]);
                Set<Unit> nextUnits = board.getUnitsAt(nextPos);
                if (nextUnits.size() == 0)
                    continue;
                for (Unit unit : nextUnits)
                    if (!unit.equals(this) && unit instanceof Tower && unit.isGround() == isGround) {
                        flag = true;
                        break;
                    }
                if (flag)
                    break;
            }
            if (!flag)
                return relativePos;
        }

        return null;
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
