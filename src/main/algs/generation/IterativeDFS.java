package main.algs.generation;

import main.ui.MazePanel;
import main.util.Cell;
import main.util.Constants;
import main.util.Maze;
import main.util.TraversalPosition;

import javax.swing.*;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class IterativeDFS {
    public static void generate(Timer timer) {
        MazePanel mazePanel = MazePanel.getInstance();
        Maze maze = MazePanel.getInstance().getMaze();

        Stack<TraversalPosition> stack = new Stack<>();
        Stack<TraversalPosition> history = new Stack<>();
        stack.add(new TraversalPosition(0, 0, null));

        AtomicInteger mod = new AtomicInteger();
        timer.addActionListener(evt -> {
            TraversalPosition pos;
            if (stack.isEmpty()) {
                if (!history.isEmpty()) {
                    pos = history.pop();
                } else {
                    mazePanel.repaint();
                    timer.stop();
                    mazePanel.setProgress(false);
                    return;
                }
            } else {
                pos = stack.pop();
            }
            if (pos.getI() < 0 || pos.getI() >= Constants.CELLS || pos.getJ() < 0 || pos.getJ() >= Constants.CELLS) {
                return;
            }
            maze.getCell(pos.getI(), pos.getJ()).visit();
            if (pos.getPrevPosition() != null) {
                maze.connectCells(pos.getI(), pos.getJ(), pos.getPrevPosition().getI(), pos.getPrevPosition().getJ());
            }
            if (timer.getDelay() == 0) {
                if (mod.get() % 25 == 0) {
                    mazePanel.repaint();
                }
            } else {
                mazePanel.repaint();
            }
            List<Cell> neighbors = maze.getNeighbors(pos.getI(), pos.getJ());

            int randomIndex = (int) (Math.random() * neighbors.size());
            if (neighbors.size() > 0) {
                history.add(new TraversalPosition(pos.getI(), pos.getJ(), null));
                stack.push(new TraversalPosition(neighbors.get(randomIndex).getI(), neighbors.get(randomIndex).getJ(), pos));
            }
            mod.incrementAndGet();
        });
        timer.start();
    }
}
