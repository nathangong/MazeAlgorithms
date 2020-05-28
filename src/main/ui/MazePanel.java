package main.ui;

import main.algs.generation.DFSGeneration;
import main.algs.traversal.BFSTraversal;
import main.algs.traversal.DFSTraversal;
import main.position.Position;
import main.util.Cell;
import main.util.Direction;
import main.util.Maze;
import main.util.TraversalType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static main.util.Constants.*;
import static main.util.Direction.*;

public class MazePanel extends JPanel {
    private static MazePanel mazeInstance = null;

    private Maze maze;
    private int delay = INITIAL_DELAY;
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
        refreshTimer();

        setFocusable(true);
        setPreferredSize(new Dimension(CELLS * CELL_LENGTH, CELLS * CELL_LENGTH));
    }

    private void refreshTimer() {
        timer = new Timer(delay, null);
    }

    public void generate() {
        refreshTimer();
        if (!inProgress) {
            inProgress = true;
            DFSGeneration.generate(timer);
        }
    }

    public void traverse(TraversalType type) {
        refreshTimer();
        if (maze.isGenerated() && !maze.getTraversed() && !inProgress) {
            inProgress = true;
            switch (type) {
                case DFS:
                    DFSTraversal.traverse(timer);
                    break;
                case BFS:
                    BFSTraversal.traverse(timer);
                    break;
            }
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

    public void setGenerated(boolean val) {
        maze.setGenerated(val);
    }

    public void setTraversed(boolean val) {
        maze.setTraversed(val);
    }

    public Maze getMaze() {
        return maze;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.yellow);
        g.setColor(Color.black);
        for (int i = 0; i < CELLS; i++) {
            for (int j = 0; j < CELLS; j++) {
                Cell cell = maze.getCell(i, j);

                g.setColor(cell.getColor());
                g.fillRect(j * CELL_LENGTH, i * CELL_LENGTH, CELL_LENGTH, CELL_LENGTH);

                g.setColor(Color.black);
                if (cell.getWall(TOP)) {
                    g.drawLine(j * CELL_LENGTH, i * CELL_LENGTH, (j + 1) * CELL_LENGTH, i * CELL_LENGTH);
                }
                if (cell.getWall(BOTTOM)) {
                    g.drawLine(j * CELL_LENGTH, (i + 1) * CELL_LENGTH, (j + 1) * CELL_LENGTH, (i + 1) * CELL_LENGTH);
                }
                if (cell.getWall(LEFT)) {
                    g.drawLine(j * CELL_LENGTH, i * CELL_LENGTH, j * CELL_LENGTH, (i + 1) * CELL_LENGTH);
                }
                if (cell.getWall(RIGHT)) {
                    g.drawLine((j + 1) * CELL_LENGTH, i * CELL_LENGTH, (j + 1) * CELL_LENGTH, (i + 1) * CELL_LENGTH);
                }
            }
        }

        g.setColor(Color.red);
        List<Position> traversalPath = maze.getTraversalPath();
        for (int i = 0; i < traversalPath.size(); i++) {
            Position currPathSegment = traversalPath.get(i);
            int centerX = (currPathSegment.getJ() + 1) * CELL_LENGTH + (currPathSegment.getJ() * CELL_LENGTH - (currPathSegment.getJ() + 1) * CELL_LENGTH) / 2;
            int centerY = (currPathSegment.getI() + 1) * CELL_LENGTH + (currPathSegment.getI() * CELL_LENGTH - (currPathSegment.getI() + 1) * CELL_LENGTH) / 2;

            if (i > 0) {
                Direction relativeDirection = currPathSegment.getRelativeDirection(traversalPath.get(i - 1));
                drawPathSegment(g, currPathSegment, centerX, centerY, relativeDirection);
            }
            if (i < traversalPath.size() - 1) {
                Direction relativeDirection = currPathSegment.getRelativeDirection(traversalPath.get(i + 1));
                drawPathSegment(g, currPathSegment, centerX, centerY, relativeDirection);
            }
        }
    }

    private void drawPathSegment(Graphics g, Position currPath, int centerX, int centerY, Direction relativeDirection) {
        switch (relativeDirection) {
            case TOP:
                g.fillRect(centerX - PATH_THICKNESS / 2, centerY - PATH_THICKNESS / 2, PATH_THICKNESS, (currPath.getI() + 1) * CELL_LENGTH - centerY + PATH_THICKNESS / 2);
                break;
            case BOTTOM:
                g.fillRect(centerX - PATH_THICKNESS / 2, currPath.getI() * CELL_LENGTH, PATH_THICKNESS, (currPath.getI() + 1) * CELL_LENGTH - centerY + PATH_THICKNESS / 2);
                break;
            case LEFT:
                g.fillRect(currPath.getJ() * CELL_LENGTH, centerY - PATH_THICKNESS / 2, (centerX + PATH_THICKNESS / 2) - currPath.getJ() * CELL_LENGTH, PATH_THICKNESS);
                break;
            case RIGHT:
                g.fillRect(centerX - PATH_THICKNESS / 2, centerY - PATH_THICKNESS / 2, (currPath.getJ() + 1) * CELL_LENGTH - (centerX - PATH_THICKNESS / 2), PATH_THICKNESS);
                break;
        }
    }
}
