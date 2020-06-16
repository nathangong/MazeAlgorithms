package main.algs.traversal;

import main.position.BestFirstSearchPosition;
import main.util.Cell;
import main.util.ColorHelper;

import java.util.PriorityQueue;

import static main.util.Constants.COLUMNS;
import static main.util.Constants.ROWS;

public class BestFirstSearch extends Traverser {
    PriorityQueue<BestFirstSearchPosition> queue;

    @Override
    protected void init() {
        queue = new PriorityQueue<>();
        queue.add(new BestFirstSearchPosition(0, 0, null));
    }

    @Override
    protected void iterate() {
         BestFirstSearchPosition pos = queue.remove();

        maze.getCell(pos.getI(), pos.getJ()).traverse(ColorHelper.getTraversalColor(pos));

        if (pos.getI() == ROWS - 1 && pos.getJ() == COLUMNS - 1) {
            BestFirstSearchPosition curr = pos;
            while (curr != null) {
                maze.addTraversalPosition(curr);
                curr = curr.getParent();
            }

            stop();
            return;
        }

        java.util.List<Cell> connected = maze.getCell(pos.getI(), pos.getJ()).getConnectedCells();
        for (Cell cell : connected) {
            if (!cell.getTraversed()) {
                queue.add(new BestFirstSearchPosition(cell.getI(), cell.getJ(), pos));
            }
        }
    }
}
