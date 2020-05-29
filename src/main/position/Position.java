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
            return LEFT;
        } else if (dx == -1) {
            return RIGHT;
        }

        int dy = this.i - other.i;
        if (dy == 1) {
            return BOTTOM;
        } else if (dy == -1) {
            return TOP;
        }

        throw new IllegalArgumentException();
    }
}
