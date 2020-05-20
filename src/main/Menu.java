package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Menu extends JPanel {
    private static Menu menuInstance = null;

    private Menu() {
        setPreferredSize(new Dimension(200, 150));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel fpsLabel = new JLabel("Delay");
        fpsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(fpsLabel);

        JSlider delaySlider = new JSlider(0, 100);
        delaySlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        delaySlider.setValue(Constants.INITIAL_DELAY);
        delaySlider.setMajorTickSpacing(20);
        delaySlider.setMinorTickSpacing(5);
        delaySlider.setPaintTicks(true);
        delaySlider.setPaintLabels(true);
        delaySlider.addChangeListener(e -> Maze.getInstance().setDelay(delaySlider.getValue()));
        add(delaySlider);
        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton generateButton = new JButton("Generate main.Maze");
        generateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        generateButton.addActionListener((ActionEvent event) -> Maze.getInstance().generate());
        add(generateButton);
    }

    public static Menu getInstance() {
        if (menuInstance == null) {
            menuInstance = new Menu();
        }
        return menuInstance;
    }
}