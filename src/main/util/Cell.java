package main.util;

import static main.util.Direction.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Cell {
    private Map<Direction, Boolean> walls;
    private int i, j;
    private boolean visited;
    private Color color;

    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
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