package main;

import main.ui.MazeFrame;
import main.ui.MenuFrame;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MazeFrame mazeFrame = new MazeFrame();
            mazeFrame.setVisible(true);

            MenuFrame menuFrame = new MenuFrame();
            menuFrame.setVisible(true);
        });
    }
}
