package main.algs.traversal;

import main.ui.MazePanel;
import main.util.Maze;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Traverser {
    protected MazePanel mazePanel;
    protected Maze maze;
    private Timer timer;

    public void traverse(Timer timer) {
        this.timer = timer;
        mazePanel = MazePanel.getInstance();
        maze = MazePanel.getInstance().getMaze();

        init();

        AtomicInteger iterations = new AtomicInteger();
        timer.addActionListener(evt -> {
            iterate();
            if (timer.getDelay() == 0) {
                if (iterations.incrementAndGet() % 100 == 0) {
                    mazePanel.repaint();
                }
            } else {
                mazePanel.repaint();
            }
        });
        timer.start();
    }

    protected void stop() {
        timer.stop();
        mazePanel.repaint();
        mazePanel.setProgress(false);
        mazePanel.setTraversed();
    }

    protected abstract void init();

    protected abstract void iterate();
}
