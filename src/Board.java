import javax.swing.JPanel;
import java.awt.*;
import java.util.Map;

public class Board extends JPanel {
    private Cell[][] maze;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        maze = new Cell[Constants.CELLS][Constants.CELLS];
        setPreferredSize(new Dimension(Constants.CELLS*Constants.CELL_LENGTH, Constants.CELLS*Constants.CELL_LENGTH));
    }

    @Override
    public void paintComponent(Graphics g) {}
}
