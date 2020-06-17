package main.position;

import static main.util.Constants.COLUMNS;
import static main.util.Constants.ROWS;

public class AStarPosition extends Position implements Comparable<AStarPosition> {
    private final AStarPosition parent;
    private final int prevLength;

    public AStarPosition(int i, int j, AStarPosition parent) {
        super(i, j);

        if (parent == null) this.prevLength = 0;
        else this.prevLength = parent.prevLength + 1;

        this.parent = parent;
    }

    public AStarPosition getParent() {
        return parent;
    }

    @Override
    public int compareTo(AStarPosition other) {
        return (prevLength + ROWS - i + COLUMNS - j) - (other.prevLength + ROWS - other.i + COLUMNS - other.j);
    }
}
