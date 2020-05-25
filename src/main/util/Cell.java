package main.util;

import static main.util.Direction.*;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cell {
    private Map<Direction, Boolean> walls;
    private int i, j;
    private List<Cell> connectedCells;
    private boolean visited;
    private Color color;

    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
        this.connectedCells = new ArrayList<Cell>();
        this.color = Color.black;
        this.visited = false;

        walls = new HashMap<>();
        walls.put(TOP, true);
        walls.put(RIGHT, true);
        walls.put(BOTTOM, true);
        walls.put(LEFT, true);
    }

    public boolean getWall(Direction direction) {
        return walls.get(direction);
    }

    public void removeWall(Direction direction) {
        walls.replace(direction, false);
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public List<Cell> getConnectedCells() {
        return connectedCells;
    }

    public void addNeighbor(Cell that) {
        this.connectedCells.add(that);
    }

    public void visit() {
        visited = true;
        color = Color.white;
    }

    public boolean getVisited() {
        return visited;
    }

    public Color getColor() {
        return color;
    }
}
