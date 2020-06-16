package main.algs.generation;

import main.position.GenerationPosition;
import main.util.Cell;

import java.util.List;
import java.util.Stack;

import static main.util.Constants.COLUMNS;
import static main.util.Constants.ROWS;

public class DFSGeneration extends Generator {
    private Stack<GenerationPosition> stack;
    private Stack<GenerationPosition> history;

    protected void init() {
        stack = new Stack<>();
        history = new Stack<>();
        stack.add(new GenerationPosition(ROWS - ROWS / 2, COLUMNS - COLUMNS / 2, null));
    }

    protected void iterate() {
        GenerationPosition pos;
        if (stack.isEmpty()) {
            if (!history.isEmpty()) {
                pos = history.pop();
            } else {
                stop();
                return;
            }
        } else {
            pos = stack.pop();
        }
        if (!maze.getCell(pos.getI(), pos.getJ()).getVisited()) {
            maze.getCell(pos.getI(), pos.getJ()).visit();
            history.add(new GenerationPosition(pos.getI(), pos.getJ(), null));
        } else {
            maze.getCell(pos.getI(), pos.getJ()).setFinalized();
        }

        if (pos.getPrevPosition() != null) {
            maze.connectCells(pos.getI(), pos.getJ(), pos.getPrevPosition().getI(), pos.getPrevPosition().getJ());
        }

        List<Cell> neighbors = maze.getNeighbors(pos.getI(), pos.getJ());
        int randomIndex = (int) (Math.random() * neighbors.size());
        if (neighbors.size() > 0) {
            stack.push(new GenerationPosition(neighbors.get(randomIndex).getI(), neighbors.get(randomIndex).getJ(), pos));
        }
    }
}
