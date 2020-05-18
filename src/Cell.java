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
        this.color = Color.white;
        this.visited = false;

        walls = new HashMap<>();
        walls.put(Direction.top, true);
        walls.put(Direction.right, true);
        walls.put(Direction.bottom, true);
        walls.put(Direction.left, true);
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
        color = Color.green;
    }

    public boolean getVisited() {
        return visited;
    }

    public Color getColor() {
        return color;
    }
}
