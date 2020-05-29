package main.position;

import static main.util.Constants.*;

public class AlphaStarPosition extends Position implements Comparable<AlphaStarPosition> {
    private final AlphaStarPosition parent;
    private final int prevLength;

    public AlphaStarPosition(int i, int j, AlphaStarPosition parent) {
        super(i, j);

        if (parent == null) this.prevLength = 0;
        else this.prevLength = parent.prevLength;

        this.parent = parent;
    }

    public AlphaStarPosition getParent() {
        return parent;
    }

    @Override
    public int compareTo(AlphaStarPosition other) {
        return (prevLength + ROWS - i + COLUMNS - j) - (other.prevLength + ROWS - other.i + COLUMNS - other.j);
    }
}
