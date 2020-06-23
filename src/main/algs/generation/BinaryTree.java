package main.algs.generation;

import main.position.Position;
import main.util.Cell;

import java.util.ArrayList;

import static main.util.Constants.ROWS;
import static main.util.Constants.COLUMNS;

public class BinaryTree extends Generator{
    Position pos;

    @Override
    protected void init() {
        pos = new Position(0, 0);
    }

    @Override
    protected void iterate() {
        if (pos.getJ() == COLUMNS) {
            pos = new Position(pos.getI()+1, 0);
        }
        if (pos.getI() == ROWS) {
            stop();
            return;
        }

        ArrayList<Cell> candidates = new ArrayList<>();
        if (pos.getI() > 0) {
            candidates.add(maze.getCell(pos.getI()-1, pos.getJ()));
        }
        if (pos.getJ() > 0) {
            candidates.add(maze.getCell(pos.getI(), pos.getJ()-1));
        }

        if (candidates.size() > 0) {
            int randomIndex = (int) (Math.random() * candidates.size());
            Cell adjacentCell = candidates.get(randomIndex);
            maze.connectCells(maze.getCell(pos.getI(), pos.getJ()), adjacentCell);
        }
        maze.getCell(pos.getI(), pos.getJ()).setFinalized();

        pos = new Position(pos.getI(), pos.getJ()+1);
    }
}
