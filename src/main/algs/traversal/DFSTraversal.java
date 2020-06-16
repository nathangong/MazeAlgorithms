package main.algs.traversal;

import main.position.DFSTraversalPosition;
import main.util.Cell;

import java.util.List;
import java.util.Stack;

import static main.util.Constants.COLUMNS;
import static main.util.Constants.ROWS;

public class DFSTraversal extends Traverser{
    private Stack<DFSTraversalPosition> stack;

    @Override
    protected void init() {
        stack = new Stack<>();
        stack.push(new DFSTraversalPosition(0, 0, null, true));
    }

    @Override
    protected void iterate() {
        DFSTraversalPosition pos = stack.pop();

        maze.getCell(pos.getI(), pos.getJ()).traverse();
        maze.addTraversalPosition(pos);
        if (pos.getI() == ROWS - 1 && pos.getJ() == COLUMNS - 1) {
            stop();
            return;
        }

        List<Cell> connected = maze.getCell(pos.getI(), pos.getJ()).getConnectedCells();
        boolean successful = false;
        for (Cell cell : connected) {
            if (!cell.getTraversed()) {
                if (!successful) {
                    stack.push(new DFSTraversalPosition(cell.getI(), cell.getJ(), pos, true));
                } else {
                    stack.push(new DFSTraversalPosition(cell.getI(), cell.getJ(), pos, false));
                }
                successful = true;
            }
        }

        if (!successful) {
            DFSTraversalPosition curr = pos;
            while (curr.isLastChild()) {
                maze.getCell(curr.getI(), curr.getJ()).leave();
                maze.removeTraversalPosition();
                curr = curr.getParent();
            }
            maze.getCell(curr.getI(), curr.getJ()).leave();
            maze.removeTraversalPosition();
        }
    }
}
