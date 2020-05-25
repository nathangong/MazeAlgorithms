package main.algs.traversal;

import main.ui.MazePanel;
import main.util.*;

import javax.swing.*;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class DFS {
    public static void traverse(Timer timer) {
        MazePanel mazePanel = MazePanel.getInstance();
        Maze maze = MazePanel.getInstance().getMaze();

        Stack<TraversalPosition> stack = new Stack<>();

        stack.add(new TraversalPosition(0, 0, null, true));

        AtomicInteger mod = new AtomicInteger();
        timer.addActionListener(evt -> {
            TraversalPosition pos;
            if (stack.isEmpty()) {
                mazePanel.repaint();
                timer.stop();
                mazePanel.setProgress(false);
                return;
            } else {
                pos = stack.pop();
            }

            maze.getCell(pos.getI(), pos.getJ()).setTraversed(true);
            if (pos.getI() == Constants.CELLS-1 && pos.getJ() == Constants.CELLS-1) {
                mazePanel.repaint();
                timer.stop();
                mazePanel.setProgress(false);
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
            for (int i = 0; i < connected.size(); i++) {
                Cell cell = connected.get(i);
                if (!cell.getTraversed()) {
                    if (i == connected.size()-1) {
                        stack.push(new TraversalPosition(cell.getI(), cell.getJ(), pos, true));
                    }
                    else {
                        stack.push(new TraversalPosition(cell.getI(), cell.getJ(), pos, false));
                    }
                }
            }
            mod.incrementAndGet();
        });
        timer.start();
    }
}
