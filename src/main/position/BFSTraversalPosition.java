package main.position;

public class BFSTraversalPosition extends Position {
    private final BFSTraversalPosition parent;

    public BFSTraversalPosition(int i, int j, BFSTraversalPosition parent) {
        super(i, j);

        this.parent = parent;
    }

    public BFSTraversalPosition getParent() {
        return parent;
    }
}