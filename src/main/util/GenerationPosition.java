package main.util;

public class GenerationPosition {
    private final int i;
    private final int j;
    private final GenerationPosition prevPosition;

    public GenerationPosition(int i, int j, GenerationPosition prevPosition) {
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

    public GenerationPosition getPrevPosition() {
        return prevPosition;
    }
}
