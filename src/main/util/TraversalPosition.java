package main.util;

public class TraversalPosition {
    private final int i;
    private final int j;
    private final TraversalPosition parent;
    private final boolean isLastChild;
    private boolean isSuccessful;

    public TraversalPosition(int i, int j, TraversalPosition parent, boolean isLastChild) {
        this.i = i;
        this.j = j;
        this.parent = parent;
        this.isLastChild = isLastChild;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void setSuccessful(boolean val) {
        isSuccessful = val;
    }

    public TraversalPosition getParent() {
        return parent;
    }

    public boolean isLastChild() {
        return isLastChild;
    }
}
