import java.util.HashMap;
import java.util.Map;

public class Cell {
    private Map<Direction, Boolean> walls;
    private int i, j;

    public Cell(int i, int j) {
        this.i = i;
        this.j = j;

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

    public void addWall(Direction direction) {
        walls.replace(direction, true);
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
