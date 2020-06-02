package main.position;

import static main.util.Constants.COLUMNS;
import static main.util.Constants.ROWS;

public class BestFirstSearchPosition extends Position implements Comparable<BestFirstSearchPosition> {
    private final BestFirstSearchPosition parent;

    public BestFirstSearchPosition(int i, int j, BestFirstSearchPosition parent) {
        super(i, j);

        this.parent = parent;
    }

    public BestFirstSearchPosition getParent() {
        return parent;
    }

    @Override
    public int compareTo(BestFirstSearchPosition other) {
        return (ROWS - i + COLUMNS - j) - (ROWS - other.i + COLUMNS - other.j);
    }
}

