package main.algs.traversal;

import main.position.AStarPosition;
import main.util.Cell;
import main.util.ColorHelper;

import java.util.PriorityQueue;

import static main.util.Constants.COLUMNS;
import static main.util.Constants.ROWS;

public class AStar extends Traverser {
    private PriorityQueue<AStarPosition> queue;

    @Override
    protected void init() {
        queue = new PriorityQueue<>();
        queue.add(new AStarPosition(0, 0, null));
    }

    @Override
    protected void iterate() {
        AStarPosition pos = queue.remove();

        maze.getCell(pos.getI(), pos.getJ()).traverse(ColorHelper.getTraversalColor(pos));

        if (pos.getI() == ROWS - 1 && pos.getJ() == COLUMNS - 1) {
            AStarPosition curr = pos;
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
                queue.add(new AStarPosition(cell.getI(), cell.getJ(), pos));
            }
        }
    }
}
