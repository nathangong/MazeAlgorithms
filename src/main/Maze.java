package main;

import static main.Direction.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Maze extends JPanel {
    private static Maze mazeInstance = null;

    private Cell[][] cells;
    private Timer timer;
    private int currI;
    private int currJ;
    private int delay;
    private boolean inProgress = false;

    private Maze() {
        initBoard();
    }

    public static Maze getInstance() {
        if (mazeInstance == null) {
            mazeInstance = new Maze();
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
        currI = 0;
        currJ = 0;
    }

    private void connectCells(Cell c1, Cell c2) {
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
            traverse();
        }
    }

    private void traverse() {
        Stack<Position> stack = new Stack<>();
        Stack<Position> history = new Stack<>();
        stack.add(new Position(currI, currJ, null));

        AtomicInteger mod = new AtomicInteger();
        timer.addActionListener(evt -> {
            Position pos;
            if (stack.isEmpty()) {
                if (!history.isEmpty()) {
                    pos = history.pop();
                }
                else {
                    repaint();
                    timer.stop();
                    inProgress = false;
                    return;
                }
            }
            else {
                pos = stack.pop();
            }
            if (pos.getI() < 0 || pos.getI() >= cells.length || pos.getJ() < 0 || pos.getJ() >= cells.length) {
                return;
            }
            currI = pos.getI();
            currJ = pos.getJ();
            cells[pos.getI()][pos.getJ()].visit();
            if (pos.getPrevPosition() != null) {
                connectCells(cells[pos.getI()][pos.getJ()], cells[pos.getPrevPosition().getI()][pos.getPrevPosition().getJ()]);
            }
            if (delay == 0) {
                if (mod.get() % 25 == 0) {
                    repaint();
                }
            }
            else {
                repaint();
            }

            List<Cell> neighbors = getNeighbors(pos.getI(), pos.getJ());

            int randomIndex = (int)(Math.random()*neighbors.size());
            if (neighbors.size() > 0) {
                history.add(new Position(pos.getI(), pos.getJ(), null));
                stack.push(new Position(neighbors.get(randomIndex).getI(), neighbors.get(randomIndex).getJ(), pos));
            }
            mod.incrementAndGet();
        });
        timer.start();
    }

    private List<Cell> getNeighbors(int posI, int posJ) {
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

                if (i == currI && j == currJ) {
                    g.setColor(Color.red);
                    g.fillRect(j*Constants.CELL_LENGTH, i*Constants.CELL_LENGTH, Constants.CELL_LENGTH, Constants.CELL_LENGTH);
                }

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

class Position {
    private int i, j;
    private Position prevPosition;

    public Position(int i, int j, Position prevPosition) {
        this.i = i;
        this.j = j;
        this.prevPosition = prevPosition;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public Position getPrevPosition() {
        return prevPosition;
    }
}
