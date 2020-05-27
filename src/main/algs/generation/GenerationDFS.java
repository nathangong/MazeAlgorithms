package main.algs.generation;

import main.position.GenerationPosition;
import main.ui.MazePanel;
import main.util.Cell;
import main.util.Maze;

import javax.swing.*;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import static main.util.Constants.CELLS;

public class GenerationDFS {
    public static void generate(Timer timer) {
        MazePanel mazePanel = MazePanel.getInstance();
        Maze maze = MazePanel.getInstance().getMaze();

        Stack<GenerationPosition> stack = new Stack<>();
        Stack<GenerationPosition> history = new Stack<>();
        stack.add(new GenerationPosition(0, 0, null));

        AtomicInteger mod = new AtomicInteger();
        timer.addActionListener(evt -> {
            GenerationPosition pos;
            if (stack.isEmpty()) {
                if (!history.isEmpty()) {
                    pos = history.pop();
                } else {
                    mazePanel.repaint();
                    timer.stop();
                    mazePanel.setProgress(false);
                    mazePanel.setGenerated(true);
                    return;
                }
            } else {
                pos = stack.pop();
            }
            if (pos.getI() < 0 || pos.getI() >= CELLS || pos.getJ() < 0 || pos.getJ() >= CELLS) {
                return;
            }
            if (!maze.getCell(pos.getI(), pos.getJ()).getVisited()) {
                maze.getCell(pos.getI(), pos.getJ()).visit();
                history.add(new GenerationPosition(pos.getI(), pos.getJ(), null));
            }
            else {
                maze.getCell(pos.getI(), pos.getJ()).finalize();
            }

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
                stack.push(new GenerationPosition(neighbors.get(randomIndex).getI(), neighbors.get(randomIndex).getJ(), pos));
            }
            mod.incrementAndGet();
        });
        timer.start();
    }
}
