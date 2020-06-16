package main.algs.traversal;

import main.position.BFSTraversalPosition;
import main.util.Cell;
import main.util.ColorHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static main.util.Constants.COLUMNS;
import static main.util.Constants.ROWS;

public class BFSTraversal extends Traverser{
    private Queue<BFSTraversalPosition> queue = new LinkedList<>();

    @Override
    protected void init() {
        queue = new LinkedList<>();
        queue.add(new BFSTraversalPosition(0, 0, null));
    }

    @Override
    protected void iterate() {
        BFSTraversalPosition pos = queue.remove();
        maze.getCell(pos.getI(), pos.getJ()).traverse(ColorHelper.getTraversalColor(pos));

        if (pos.getI() == ROWS - 1 && pos.getJ() == COLUMNS - 1) {
            BFSTraversalPosition curr = pos;
            while (curr != null) {
                maze.addTraversalPosition(curr);
                curr = curr.getParent();
            }

            stop();
            return;
        }

        List<Cell> connected = maze.getCell(pos.getI(), pos.getJ()).getConnectedCells();
        for (Cell cell : connected) {
            if (!cell.getTraversed()) {
                queue.add(new BFSTraversalPosition(cell.getI(), cell.getJ(), pos));
            }
        }
    }
}
