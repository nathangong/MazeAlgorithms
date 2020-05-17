import java.util.HashMap;
import java.util.Map;

public class Cell {
    private Map<Direction, Boolean> walls;

    public Cell() {
        walls = new HashMap<>();
        walls.put(Direction.top, true);
        walls.put(Direction.right, true);
        walls.put(Direction.bottom, true);
        walls.put(Direction.left, true);
    }

    public Map<Direction, Boolean> getWalls() {
        return walls;
    }
}
