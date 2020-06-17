package main.algs.generation;

import main.position.GenerationPosition;
import main.util.Cell;

import java.util.ArrayList;
import java.util.List;

import static main.util.Constants.COLUMNS;
import static main.util.Constants.ROWS;

public class BFSGeneration extends Generator {
    private List<GenerationPosition> randomizedQueue;

    @Override
    protected void init() {
        randomizedQueue = new ArrayList<>();
        randomizedQueue.add(new GenerationPosition(ROWS - ROWS / 2, COLUMNS - COLUMNS / 2, null));
    }

    @Override
    protected void iterate() {
        if (randomizedQueue.isEmpty()) {
            stop();
            return;
        }
        int randomIndex = (int) (Math.random()*randomizedQueue.size());
        GenerationPosition pos = randomizedQueue.remove(randomIndex);

        maze.getCell(pos.getI(), pos.getJ()).setFinalized();
        if (pos.getPrevPosition() != null) {
            maze.connectCells(pos.getI(), pos.getJ(), pos.getPrevPosition().getI(), pos.getPrevPosition().getJ());
        }

        List<Cell> neighbors = maze.getUnvisitedNeighbors(pos.getI(), pos.getJ());
        for (Cell cell : neighbors) {
            randomizedQueue.add(new GenerationPosition(cell.getI(), cell.getJ(), pos));
            cell.visit();
        }
    }
}
