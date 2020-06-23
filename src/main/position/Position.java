package main.position;

import main.util.Direction;

import static main.util.Direction.*;

public class Position {
    protected final int i;
    protected final int j;

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public Direction getRelativeDirection(Position other) {
        int dx = this.j - other.j;
        if (dx == 1) {
            return RIGHT;
        } else if (dx == -1) {
            return LEFT;
        }

        int dy = this.i - other.i;
        if (dy == 1) {
            return BOTTOM;
        } else if (dy == -1) {
            return TOP;
        }

        throw new IllegalArgumentException();
    }

    public Position getAdjacentPosition(Direction direction) {
        if (direction == LEFT) {
            return new Position(i, j-1);
        } else if (direction == RIGHT) {
            return new Position(i, j+1);
        } else if (direction == TOP) {
            return new Position(i-1, j);
        } else {
            return new Position(i+1, j);
        }
    }
}
