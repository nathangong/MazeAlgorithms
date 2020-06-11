package main.algs.generation;

import main.position.GenerationPosition;
import main.ui.MazePanel;
import main.util.Cell;
import main.util.Maze;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static main.util.Constants.COLUMNS;
import static main.util.Constants.ROWS;

public class BFSGeneration {
    public static void generate(Timer timer) {
        MazePanel mazePanel = MazePanel.getInstance();
        Maze maze = MazePanel.getInstance().getMaze();

        List<GenerationPosition> randomizedQueue = new ArrayList<GenerationPosition>();
        randomizedQueue.add(new GenerationPosition(ROWS - ROWS / 2, COLUMNS - COLUMNS / 2, null));

        AtomicInteger mod = new AtomicInteger();
        timer.addActionListener(evt -> {
            if (randomizedQueue.isEmpty()) {
                mazePanel.repaint();
                timer.stop();
                mazePanel.setProgress(false);
                mazePanel.setGenerated(true);
                return;
            }
            int randomIndex = (int) (Math.random()*randomizedQueue.size());
            GenerationPosition pos = randomizedQueue.remove(randomIndex);

            maze.getCell(pos.getI(), pos.getJ()).setFinalized();
            if (pos.getPrevPosition() != null) {
                maze.connectCells(pos.getI(), pos.getJ(), pos.getPrevPosition().getI(), pos.getPrevPosition().getJ());
            }
            if (timer.getDelay() == 0) {
                if (mod.get() % 100 == 0) {
                    mazePanel.repaint();
                }
            } else {
                mazePanel.repaint();
            }
            List<Cell> neighbors = maze.getNeighbors(pos.getI(), pos.getJ());
            for (Cell cell : neighbors) {
                randomizedQueue.add(new GenerationPosition(cell.getI(), cell.getJ(), pos));
                cell.visit();
            }
            mod.incrementAndGet();
        });
        timer.start();
    }
}
