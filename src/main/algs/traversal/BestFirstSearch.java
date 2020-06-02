package main.algs.traversal;

import main.position.BestFirstSearchPosition;
import main.ui.MazePanel;
import main.util.Cell;
import main.util.ColorHelper;
import main.util.Maze;

import javax.swing.*;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static main.util.Constants.COLUMNS;
import static main.util.Constants.ROWS;

public class BestFirstSearch {
    public static void traverse(Timer timer) {
        MazePanel mazePanel = MazePanel.getInstance();
        Maze maze = MazePanel.getInstance().getMaze();

        PriorityQueue<BestFirstSearchPosition> queue = new PriorityQueue<>();
        queue.add(new BestFirstSearchPosition(0, 0, null));

        AtomicInteger iterations = new AtomicInteger();
        timer.addActionListener(evt -> {
            BestFirstSearchPosition pos = queue.remove();

            maze.getCell(pos.getI(), pos.getJ()).traverse(ColorHelper.getTraversalColor(pos));

            if (pos.getI() == ROWS - 1 && pos.getJ() == COLUMNS - 1) {
                BestFirstSearchPosition curr = pos;
                while (curr != null) {
                    maze.addTraversalPosition(curr);
                    curr = curr.getParent();
                }

                mazePanel.repaint();
                timer.stop();
                mazePanel.setProgress(false);
                mazePanel.setTraversed();
                return;
            }

            if (timer.getDelay() == 0) {
                if (iterations.get() % 25 == 0) {
                    mazePanel.repaint();
                }
            } else {
                mazePanel.repaint();
            }

            java.util.List<Cell> connected = maze.getCell(pos.getI(), pos.getJ()).getConnectedCells();
            for (Cell cell : connected) {
                if (!cell.getTraversed()) {
                    queue.add(new BestFirstSearchPosition(cell.getI(), cell.getJ(), pos));
                }
            }

            iterations.incrementAndGet();
        });
        timer.start();
    }
}