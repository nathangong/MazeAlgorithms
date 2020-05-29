package main.algs.traversal;

import main.position.DFSTraversalPosition;
import main.ui.MazePanel;
import main.util.Cell;
import main.util.Maze;

import javax.swing.*;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import static main.util.Constants.*;

public class DFSTraversal {
    public static void traverse(Timer timer) {
        MazePanel mazePanel = MazePanel.getInstance();
        Maze maze = MazePanel.getInstance().getMaze();

        Stack<DFSTraversalPosition> stack = new Stack<>();
        stack.push(new DFSTraversalPosition(0, 0, null, true));

        AtomicInteger mod = new AtomicInteger();
        timer.addActionListener(evt -> {
            DFSTraversalPosition pos = stack.pop();

            maze.getCell(pos.getI(), pos.getJ()).traverse();
            maze.addTraversalPosition(pos);
            if (pos.getI() == ROWS - 1 && pos.getJ() == COLUMNS - 1) {
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

            List<Cell> connected = maze.getCell(pos.getI(), pos.getJ()).getConnectedCells();
            boolean successful = false;
            for (Cell cell : connected) {
                if (!cell.getTraversed()) {
                    if (!successful) {
                        stack.push(new DFSTraversalPosition(cell.getI(), cell.getJ(), pos, true));
                    } else {
                        stack.push(new DFSTraversalPosition(cell.getI(), cell.getJ(), pos, false));
                    }
                    successful = true;
                }
            }

            if (!successful) {
                DFSTraversalPosition curr = pos;
                while (curr.isLastChild()) {
                    maze.getCell(curr.getI(), curr.getJ()).leave();
                    maze.removeTraversalPosition();
                    curr = curr.getParent();
                }
                maze.getCell(curr.getI(), curr.getJ()).leave();
                maze.removeTraversalPosition();
            }
            mod.incrementAndGet();
        });
        timer.start();
    }
}
