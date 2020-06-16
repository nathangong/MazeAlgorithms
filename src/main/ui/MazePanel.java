package main.ui;

import main.algs.generation.BFSGeneration;
import main.algs.generation.DFSGeneration;
import main.algs.generation.Generator;
import main.algs.traversal.*;
import main.position.Position;
import main.util.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.util.Constants.*;
import static main.util.Direction.*;

public class MazePanel extends JPanel {
    private static MazePanel mazeInstance = null;

    private Maze maze;
    private int delay = INITIAL_DELAY;
    private boolean inProgress = false;
    private Timer timer;
    private Map<TraversalType, Traverser> traversers;
    private Map<GenerationType, Generator> generators;

    private MazePanel() {
        initPanel();
    }

    public static MazePanel getInstance() {
        if (mazeInstance == null) {
            mazeInstance = new MazePanel();
        }
        return mazeInstance;
    }

    void setDelay(int delay) {
        this.delay = delay;
        timer.setDelay(delay);
    }

    private void initPanel() {
        initMaps();
        maze = new Maze();
        refreshTimer();

        setFocusable(true);
        setPreferredSize(new Dimension(COLUMNS * CELL_LENGTH, ROWS * CELL_LENGTH));
    }

    private void initMaps() {
        generators = new HashMap<>();
        generators.put(GenerationType.DFS, new DFSGeneration());
        generators.put(GenerationType.BFS, new BFSGeneration());

        traversers = new HashMap<>();
        traversers.put(TraversalType.DFS, new DFSTraversal());
        traversers.put(TraversalType.BFS, new BFSTraversal());
        traversers.put(TraversalType.A_STAR, new AStar());
        traversers.put(TraversalType.BEST_FIRST_SEARCH, new BestFirstSearch());
    }

    private void refreshTimer() {
        timer = new Timer(delay, null);
    }

    public void generate(GenerationType type) {
        if (!inProgress && !maze.isGenerated()) {
            refreshTimer();
            inProgress = true;
            generators.get(type).generate(timer);
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
            traversers.get(type).traverse(timer);
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
