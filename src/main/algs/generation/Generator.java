package main.algs.generation;

import main.ui.MazePanel;
import main.util.Maze;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Generator {
    protected MazePanel mazePanel;
    protected Maze maze;
    private Timer timer;

    protected abstract void init();

    protected abstract void iterate();

    public void generate(Timer timer) {
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
        mazePanel.setGenerated(true);
    }
}
