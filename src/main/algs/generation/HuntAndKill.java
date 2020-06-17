package main.algs.generation;

import main.position.GenerationPosition;
import main.util.Cell;

import java.util.List;
import java.util.Stack;

import static main.util.Constants.COLUMNS;
import static main.util.Constants.ROWS;

public class HuntAndKill extends Generator {
    private Stack<GenerationPosition> stack;

    @Override
    protected void init() {
        stack = new Stack<>();
        stack.add(new GenerationPosition(ROWS - ROWS / 2, COLUMNS - COLUMNS / 2, null));
    }

    @Override
    protected void iterate() {
        GenerationPosition pos = null;
        if (stack.isEmpty()) {
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    if (maze.getCell(i, j).getVisited() && maze.getUnvisitedNeighbors(i, j).size() > 0) {
                        pos = new GenerationPosition(i, j, null);
                    }
                }
            }
            if (pos == null) {
                stop();
                return;
            }
        } else {
            pos = stack.pop();
        }

        if (!maze.getCell(pos.getI(), pos.getJ()).getVisited()) {
            maze.getCell(pos.getI(), pos.getJ()).visit();
            maze.getCell(pos.getI(), pos.getJ()).setFinalized();
        }

        if (pos.getPrevPosition() != null) {
            maze.connectCells(pos.getI(), pos.getJ(), pos.getPrevPosition().getI(), pos.getPrevPosition().getJ());
        }

        List<Cell> neighbors = maze.getUnvisitedNeighbors(pos.getI(), pos.getJ());
        int randomIndex = (int) (Math.random() * neighbors.size());
        if (neighbors.size() > 0) {
            stack.push(new GenerationPosition(neighbors.get(randomIndex).getI(), neighbors.get(randomIndex).getJ(), pos));
        }
    }
}
