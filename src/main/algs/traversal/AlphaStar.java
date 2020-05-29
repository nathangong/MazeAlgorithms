package main.algs.traversal;

import main.position.AlphaStarPosition;
import main.ui.MazePanel;
import main.util.Cell;
import main.util.Maze;

import javax.swing.*;
import java.awt.*;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static main.util.Constants.*;

public class AlphaStar {
    public static void traverse(Timer timer) {
        MazePanel mazePanel = MazePanel.getInstance();
        Maze maze = MazePanel.getInstance().getMaze();

        PriorityQueue<AlphaStarPosition> queue = new PriorityQueue<>();
        queue.add(new AlphaStarPosition(0,0,null));

        AtomicInteger mod = new AtomicInteger();
        timer.addActionListener(evt -> {
            AlphaStarPosition pos = queue.remove();
            maze.getCell(pos.getI(), pos.getJ()).traverse(Color.cyan);

            if (pos.getI() == ROWS - 1 && pos.getJ() == COLUMNS - 1) {
                AlphaStarPosition curr = pos;
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
                if (mod.get() % 25 == 0) {
                    mazePanel.repaint();
                }
            } else {
                mazePanel.repaint();
            }

            java.util.List<Cell> connected = maze.getCell(pos.getI(), pos.getJ()).getConnectedCells();
            for (Cell cell : connected) {
                if (!cell.getTraversed()) {
                    queue.add(new AlphaStarPosition(cell.getI(), cell.getJ(), pos));
                }
            }

            mod.incrementAndGet();
        });
        timer.start();
    }
}
