package main.util;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.util.Direction.*;

public class Cell {
    private final Map<Direction, Boolean> walls;
    private final int i;
    private final int j;
    private final List<Cell> connectedCells;
    private boolean visited, traversed;
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

    public void setTraversed(boolean val) {
        traversed = val;
    }

    public boolean getTraversed() {
        return traversed;
    }


    public boolean getVisited() {
        return visited;
    }

    public Color getColor() {
        return color;
    }
}
