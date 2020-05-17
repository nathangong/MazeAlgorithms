import javax.swing.JPanel;
import java.awt.*;

public class Board extends JPanel {
    private Cell[][] maze;
    private int currI;
    private int currJ;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        initMaze();

        setFocusable(true);
        setPreferredSize(new Dimension(Constants.CELLS*Constants.CELL_LENGTH, Constants.CELLS*Constants.CELL_LENGTH));
    }

    private void initMaze() {
        maze = new Cell[Constants.CELLS][Constants.CELLS];
        for (int i = 0; i < Constants.CELLS; i++) {
            for (int j = 0; j < Constants.CELLS; j++) {
                maze[i][j] = new Cell(i, j);
            }
        }
        currI = 0;
        currJ = 0;
    }

    private void connectCells(Cell c1, Cell c2) {
        int dx = c1.getJ() - c2.getJ();
        if (dx == 1) {
            c1.removeWall(Direction.left);
            c2.removeWall(Direction.right);
        } else if (dx == -1) {
            c1.removeWall(Direction.right);
            c2.removeWall(Direction.left);
        }

        int dy = c1.getI() - c2.getI();
        if (dy == 1) {
            c1.removeWall(Direction.top);
            c2.removeWall(Direction.bottom);
        } else if (dy == -1) {
            c1.removeWall(Direction.bottom);
            c2.removeWall(Direction.top);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.yellow);
        g.fillRect(currJ*Constants.CELL_LENGTH, currI*Constants.CELL_LENGTH, Constants.CELL_LENGTH, Constants.CELL_LENGTH);

        g.setColor(Color.black);
        for (int i = 0; i < Constants.CELLS; i++) {
            for (int j = 0; j < Constants.CELLS; j++) {
                Cell cell = maze[i][j];
                if (cell.getWall(Direction.top)) {
                    g.drawLine(j*Constants.CELL_LENGTH, i*Constants.CELL_LENGTH, (j+1)*Constants.CELL_LENGTH, i*Constants.CELL_LENGTH);
                }
                if (cell.getWall(Direction.bottom)) {
                    g.drawLine(j*Constants.CELL_LENGTH, (i+1)*Constants.CELL_LENGTH, (j+1)*Constants.CELL_LENGTH, (i+1)*Constants.CELL_LENGTH);
                }
                if (cell.getWall(Direction.left)) {
                    g.drawLine(j*Constants.CELL_LENGTH, i*Constants.CELL_LENGTH, j*Constants.CELL_LENGTH, (i+1)*Constants.CELL_LENGTH);
                }
                if (cell.getWall(Direction.right)) {
                    g.drawLine((j+1)*Constants.CELL_LENGTH, i*Constants.CELL_LENGTH, (j+1)*Constants.CELL_LENGTH, (i+1)*Constants.CELL_LENGTH);
                }
            }
        }
    }
}
