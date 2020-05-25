package main.ui;

import main.algs.generation.IterativeDFS;
import main.util.Cell;
import main.util.Constants;
import main.util.Maze;

import javax.swing.*;
import java.awt.*;

import static main.util.Direction.*;

public class MazePanel extends JPanel {
    private static MazePanel mazeInstance = null;

    private Maze maze;
    private int delay = Constants.INITIAL_DELAY;
    private boolean inProgress = false;
    private Timer timer;

    private MazePanel() {
        initPanel();
    }

    public static MazePanel getInstance() {
        if (mazeInstance == null) {
            mazeInstance = new MazePanel();
        }
        return mazeInstance;
    }

    public void setDelay(int delay) {
        this.delay = delay;
        timer.setDelay(delay);
    }

    private void initPanel() {
        maze = new Maze();
        timer = new Timer(delay, null);

        setFocusable(true);
        setPreferredSize(new Dimension(Constants.CELLS * Constants.CELL_LENGTH, Constants.CELLS * Constants.CELL_LENGTH));
    }

    public void generate() {
        if (!inProgress) {
            inProgress = true;
            IterativeDFS.generate(timer);
        }
    }

    public void clear() {
        setProgress(false);
        initPanel();
        repaint();
    }

    public void setProgress(boolean val) {
        inProgress = val;
    }

    public Maze getMaze() {
        return maze;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.yellow);

        g.setColor(Color.black);
        for (int i = 0; i < Constants.CELLS; i++) {
            for (int j = 0; j < Constants.CELLS; j++) {
                Cell cell = maze.getCell(i, j);

                g.setColor(cell.getColor());
                g.fillRect(j * Constants.CELL_LENGTH, i * Constants.CELL_LENGTH, Constants.CELL_LENGTH, Constants.CELL_LENGTH);

                g.setColor(Color.black);
                if (cell.getWall(TOP)) {
                    g.drawLine(j * Constants.CELL_LENGTH, i * Constants.CELL_LENGTH, (j + 1) * Constants.CELL_LENGTH, i * Constants.CELL_LENGTH);
                }
                if (cell.getWall(BOTTOM)) {
                    g.drawLine(j * Constants.CELL_LENGTH, (i + 1) * Constants.CELL_LENGTH, (j + 1) * Constants.CELL_LENGTH, (i + 1) * Constants.CELL_LENGTH);
                }
                if (cell.getWall(LEFT)) {
                    g.drawLine(j * Constants.CELL_LENGTH, i * Constants.CELL_LENGTH, j * Constants.CELL_LENGTH, (i + 1) * Constants.CELL_LENGTH);
                }
                if (cell.getWall(RIGHT)) {
                    g.drawLine((j + 1) * Constants.CELL_LENGTH, i * Constants.CELL_LENGTH, (j + 1) * Constants.CELL_LENGTH, (i + 1) * Constants.CELL_LENGTH);
                }
            }
        }
    }
}
