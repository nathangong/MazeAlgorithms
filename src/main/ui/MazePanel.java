package main.ui;

import main.algs.generation.BFSGeneration;
import main.algs.generation.DFSGeneration;
import main.algs.traversal.AStar;
import main.algs.traversal.BFSTraversal;
import main.algs.traversal.BestFirstSearch;
import main.algs.traversal.DFSTraversal;
import main.position.Position;
import main.util.*;

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
        setPreferredSize(new Dimension(COLUMNS * CELL_LENGTH, ROWS * CELL_LENGTH));
    }

    private void refreshTimer() {
        timer = new Timer(delay, null);
    }

    public void generate(GenerationType type) {
        if (!inProgress && !maze.isGenerated()) {
            refreshTimer();
            inProgress = true;
            switch (type) {
                case DFS:
                    DFSGeneration.generate(timer);
                    break;
                case BFS:
                    BFSGeneration.generate(timer);
                    break;
            }
        }
    }

    public void traverse(TraversalType type) {
        refreshTimer();
        if (maze.getTraversed()) {
            maze.unTraverse();
            repaint();
        }
        if (maze.isGenerated() && !inProgress) {
            inProgress = true;
            switch (type) {
                case DFS:
                    DFSTraversal.traverse(timer);
                    break;
                case BFS:
                    BFSTraversal.traverse(timer);
                    break;
                case A_STAR:
                    AStar.traverse(timer);
                    break;
                case BEST_FIRST_SEARCH:
                    BestFirstSearch.traverse(timer);
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

    public void setTraversed() {
        maze.setTraversed(true);
    }

    public Maze getMaze() {
        return maze;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, COLUMNS*CELL_LENGTH, ROWS*CELL_LENGTH);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Cell cell = maze.getCell(i, j);
                if (cell.getColor() == Color.black) continue;

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
