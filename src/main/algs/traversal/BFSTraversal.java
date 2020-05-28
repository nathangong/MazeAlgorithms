package main.algs.traversal;

import main.position.BFSTraversalPosition;
import main.ui.MazePanel;
import main.util.Cell;
import main.util.Maze;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import static main.util.Constants.CELLS;

public class BFSTraversal {
    public static void traverse(Timer timer) {
        MazePanel mazePanel = MazePanel.getInstance();
        Maze maze = MazePanel.getInstance().getMaze();

        Queue<BFSTraversalPosition> queue = new LinkedList<>();
        queue.add(new BFSTraversalPosition(0,0, null));

        AtomicInteger mod = new AtomicInteger();
        timer.addActionListener(evt -> {
            BFSTraversalPosition pos = queue.remove();
            maze.getCell(pos.getI(), pos.getJ()).traverse(Color.cyan);

            if (pos.getI() == CELLS - 1 && pos.getJ() == CELLS - 1) {
                BFSTraversalPosition curr = pos;
                while (curr != null) {
                    maze.addTraversalPosition(curr);
                    curr = curr.getParent();
                }

                mazePanel.repaint();
                timer.stop();
                mazePanel.setProgress(false);
                mazePanel.setTraversed(true);
                return;
            }

            if (timer.getDelay() == 0) {
                if (mod.get() % 25 == 0) {
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

            mod.incrementAndGet();
        });
        timer.start();
    }
}
