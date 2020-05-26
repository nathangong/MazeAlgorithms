package main.util;

import java.util.ArrayList;
import java.util.List;

import static main.util.Direction.*;

public class Maze {
    Cell[][] cells;
    private boolean generated;
    private boolean traversed;

    public Maze() {
        cells = new Cell[Constants.CELLS][Constants.CELLS];
        for (int i = 0; i < Constants.CELLS; i++) {
            for (int j = 0; j < Constants.CELLS; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
        generated = false;
        traversed = false;
    }

    public Cell getCell(int i, int j) {
        return cells[i][j];
    }

    public List<Cell> getNeighbors(int i, int j) {
        List<Cell> neighbors = new ArrayList<>();

        Cell top = i - 1 < 0 ? null : cells[i - 1][j];
        Cell right = j + 1 >= Constants.CELLS ? null : cells[i][j + 1];
        Cell bottom = i + 1 >= Constants.CELLS ? null : cells[i + 1][j];
        Cell left = j - 1 < 0 ? null : cells[i][j - 1];

        if (top != null && !top.getVisited()) {
            neighbors.add(top);
        }
        if (right != null && !right.getVisited()) {
            neighbors.add(right);
        }
        if (bottom != null && !bottom.getVisited()) {
            neighbors.add(bottom);
        }
        if (left != null && !left.getVisited()) {
            neighbors.add(left);
        }

        return neighbors;
    }

    public void connectCells(int i1, int j1, int i2, int j2) {
        Cell c1 = cells[i1][j1];
        Cell c2 = cells[i2][j2];

        c1.addNeighbor(c2);
        c2.addNeighbor(c1);

        int dx = c1.getJ() - c2.getJ();
        if (dx == 1) {
            c1.removeWall(LEFT);
            c2.removeWall(RIGHT);
        } else if (dx == -1) {
            c1.removeWall(RIGHT);
            c2.removeWall(LEFT);
        }

        int dy = c1.getI() - c2.getI();
        if (dy == 1) {
            c1.removeWall(TOP);
            c2.removeWall(BOTTOM);
        } else if (dy == -1) {
            c1.removeWall(BOTTOM);
            c2.removeWall(TOP);
        }
    }

    public void setGenerated(boolean val) {
        generated = val;
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setTraversed(boolean val) {
        traversed = true;
    }

    public boolean getTraversed() {
        return traversed;
    }
}
