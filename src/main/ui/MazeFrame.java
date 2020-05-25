package main.ui;

import javax.swing.*;

public class MazeFrame extends JFrame {
    public MazeFrame() {
        initUI();
    }

    private void initUI() {
        add(MazePanel.getInstance());
        pack();

        setTitle("Maze Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
