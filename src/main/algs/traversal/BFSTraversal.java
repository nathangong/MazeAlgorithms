package main.algs.traversal;

import main.position.BFSTraversalPosition;
import main.ui.MazePanel;
import main.util.Cell;
import main.util.ColorHelper;
import main.util.Maze;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import static main.util.Constants.COLUMNS;
import static main.util.Constants.ROWS;

public class BFSTraversal {
    public static void traverse(Timer timer) {
        MazePanel mazePanel = MazePanel.getInstance();
        Maze maze = MazePanel.getInstance().getMaze();

        Queue<BFSTraversalPosition> queue = new LinkedList<>();
        queue.add(new BFSTraversalPosition(0, 0, null));

        AtomicInteger iterations = new AtomicInteger();
        timer.addActionListener(evt -> {
            BFSTraversalPosition pos = queue.remove();
            maze.getCell(pos.getI(), pos.getJ()).traverse(ColorHelper.getTraversalColor(pos));

            if (pos.getI() == ROWS - 1 && pos.getJ() == COLUMNS - 1) {
                BFSTraversalPosition curr = pos;
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

            List<Cell> connected = maze.getCell(pos.getI(), pos.getJ()).getConnectedCells();
            for (Cell cell : connected) {
                if (!cell.getTraversed()) {
                    queue.add(new BFSTraversalPosition(cell.getI(), cell.getJ(), pos));
                }
            }

            iterations.incrementAndGet();
        });
        timer.start();
    }
}
