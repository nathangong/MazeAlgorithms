package main.algs.traversal;

import main.position.AStarPosition;
import main.ui.MazePanel;
import main.util.Cell;
import main.util.ColorHelper;
import main.util.Maze;

import javax.swing.*;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static main.util.Constants.*;

public class AStar {
    public static void traverse(Timer timer) {
        MazePanel mazePanel = MazePanel.getInstance();
        Maze maze = MazePanel.getInstance().getMaze();

        PriorityQueue<AStarPosition> queue = new PriorityQueue<>();
        queue.add(new AStarPosition(0,0,null));

        AtomicInteger iterations = new AtomicInteger();
        timer.addActionListener(evt -> {
            AStarPosition pos = queue.remove();

            maze.getCell(pos.getI(), pos.getJ()).traverse(ColorHelper.getTraversalColor(pos));

            if (pos.getI() == ROWS - 1 && pos.getJ() == COLUMNS - 1) {
                AStarPosition curr = pos;
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
                    queue.add(new AStarPosition(cell.getI(), cell.getJ(), pos));
                }
            }

            iterations.incrementAndGet();
        });
        timer.start();
    }
}
