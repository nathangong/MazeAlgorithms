package main.position;

public class DFSTraversalPosition extends Position {
    private final DFSTraversalPosition parent;
    private final boolean isLastChild;

    public DFSTraversalPosition(int i, int j, DFSTraversalPosition parent, boolean isLastChild) {
        super(i, j);

        this.parent = parent;
        this.isLastChild = isLastChild;
    }

    public DFSTraversalPosition getParent() {
        return parent;
    }

    public boolean isLastChild() {
        return isLastChild;
    }
}
