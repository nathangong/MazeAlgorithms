package main.util;

public class TraversalPosition {
    private int i, j;
    private TraversalPosition prevPosition;

    public TraversalPosition(int i, int j, TraversalPosition prevPosition) {
        this.i = i;
        this.j = j;
        this.prevPosition = prevPosition;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public TraversalPosition getPrevPosition() {
        return prevPosition;
    }
}
