package main.util;

public class Wall {
    private int i, j;
    private Direction direction;

    public Wall(int i, int j, Direction direction) {
        this.i = i;
        this.j = j;
        this.direction = direction;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public Direction getDirection() {
        return direction;
    }
}
