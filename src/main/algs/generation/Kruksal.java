package main.algs.generation;

import java.util.ArrayList;

import main.position.Position;
import main.util.Cell;
import main.util.Wall;

import static main.util.Constants.COLUMNS;
import static main.util.Constants.ROWS;


public class Kruksal extends Generator {
    private ArrayList<Wall> walls;

    @Override
    protected void init() {
        walls = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                for (Cell cell : maze.getUnvisitedNeighbors(i, j)) {
                    walls.add(new Wall(cell.getI(), cell.getJ(), new Position(i, j).getRelativeDirection(new Position(cell.getI(), cell.getJ()))));
                }
            }
        }
    }

    @Override
    protected void iterate() {
        if (walls.size() == 0) {
            stop();
            return;
        }

        int randomIndex = (int)(Math.random()*walls.size());
        Wall wall = walls.remove(randomIndex);
        Position pos1 = new Position(wall.getI(), wall.getJ());
        Position pos2 = pos1.getAdjacentPosition(wall.getDirection());

        maze.getCell(pos1.getI(), pos1.getJ()).setFinalized();
        maze.getCell(pos2.getI(), pos2.getJ()).setFinalized();
        if (!maze.connected(pos1.getI(), pos1.getJ(), pos2.getI(), pos2.getJ())) {
            maze.connectCells(pos1.getI(), pos1.getJ(), pos2.getI(), pos2.getJ());
        }
    }
}
