package main.ui;

import static main.util.Direction.*;

import main.algs.generation.IterativeDFS;
import main.util.Cell;
import main.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MazePanel extends JPanel {
    private static MazePanel mazeInstance = null;

    private Cell[][] cells;
    private Timer timer;
    private int delay;
    private boolean inProgress = false;

    private MazePanel() {
        initBoard();
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

    private void initBoard() {
        initMaze();
        delay = Constants.INITIAL_DELAY;
        timer = new Timer(delay, null);

        setFocusable(true);
        setPreferredSize(new Dimension(Constants.CELLS*Constants.CELL_LENGTH, Constants.CELLS*Constants.CELL_LENGTH));
    }

    private void initMaze() {
        cells = new Cell[Constants.CELLS][Constants.CELLS];
        for (int i = 0; i < Constants.CELLS; i++) {
            for (int j = 0; j < Constants.CELLS; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public void connectCells(Cell c1, Cell c2) {
        int dx = c1.getJ() - c2.getJ();
        if (dx == 1) {
            c1.removeWall(LEFT);
            c2.removeWall(RIGHT);
        } else if (dx == -1) {
            c1.removeWall(RIGHT);
            c2.removeWall(LEFT);
        }

        int dy = c1.getI() - c2.getI();
        if (dy == 1) {
            c1.removeWall(TOP);
            c2.removeWall(BOTTOM);
        } else if (dy == -1) {
            c1.removeWall(BOTTOM);
            c2.removeWall(TOP);
        }
    }

    public void generate() {
        if (!inProgress) {
            inProgress = true;
            IterativeDFS.generate(timer);
        }
    }

    public void setProgress(boolean val) {
        inProgress = val;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public List<Cell> getNeighbors(int posI, int posJ) {
        List<Cell> neighbors = new ArrayList<>();

        Cell top = posI-1 < 0 ? null : cells[posI-1][posJ];
        Cell right = posJ+1 >= Constants.CELLS ? null : cells[posI][posJ+1];
        Cell bottom = posI+1 >= Constants.CELLS ? null : cells[posI+1][posJ];
        Cell left = posJ-1 < 0 ? null : cells[posI][posJ-1];

        if (top != null && !top.getVisited()){
            neighbors.add(top);
        }
        if (right != null && !right.getVisited()){
            neighbors.add(right);
        }
        if (bottom != null && !bottom.getVisited()){
            neighbors.add(bottom);
        }
        if (left != null && !left.getVisited()){
            neighbors.add(left);
        }

        return neighbors;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.yellow);

        g.setColor(Color.black);
        for (int i = 0; i < Constants.CELLS; i++) {
            for (int j = 0; j < Constants.CELLS; j++) {
                Cell cell = cells[i][j];

                g.setColor(cell.getColor());
                g.fillRect(j*Constants.CELL_LENGTH, i*Constants.CELL_LENGTH, Constants.CELL_LENGTH, Constants.CELL_LENGTH);

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
