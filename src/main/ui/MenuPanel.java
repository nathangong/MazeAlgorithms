package main.ui;

import main.util.GenerationType;
import main.util.TraversalType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static main.util.Constants.INITIAL_DELAY;

public class MenuPanel extends JPanel {
    private static MenuPanel menuInstance = null;

    public static MenuPanel getInstance() {
        if (menuInstance == null) {
            menuInstance = new MenuPanel();
        }
        return menuInstance;
    }

    private MenuPanel() {
        setPreferredSize(new Dimension(200, 300));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel fpsLabel = new JLabel("Delay");
        fpsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(fpsLabel);

        JSlider delaySlider = new JSlider(0, 100);
        delaySlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        delaySlider.setValue(INITIAL_DELAY);
        delaySlider.setMajorTickSpacing(20);
        delaySlider.setMinorTickSpacing(5);
        delaySlider.setPaintTicks(true);
        delaySlider.setPaintLabels(true);
        delaySlider.setToolTipText("Sets the delay between each frame (ms)");
        delaySlider.addChangeListener(e -> MazePanel.getInstance().setDelay(delaySlider.getValue()));
        add(delaySlider);
        add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel generationAlgorithmLabel = new JLabel("Generation Algorithm");
        generationAlgorithmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(generationAlgorithmLabel);

        String[] generationAlgorithms = {"DFS", "BFS", "Hunt and Kill"};
        JComboBox<String> generationDropdown = new JComboBox<>(generationAlgorithms);
        generationDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        generationDropdown.setMaximumSize(new Dimension(200, 30));
        add(generationDropdown);
        add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel traversalAlgorithmLabel = new JLabel("Traversal Algorithm");
        traversalAlgorithmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(traversalAlgorithmLabel);

        String[] traversalAlgorithms = {"DFS", "BFS", "A*", "Best First Search"};
        JComboBox<String> traversalDropdown = new JComboBox<>(traversalAlgorithms);
        traversalDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        traversalDropdown.setMaximumSize(new Dimension(200, 30));
        add(traversalDropdown);
        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton generateButton = new JButton("Generate Maze");
        generateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        generateButton.addActionListener((ActionEvent event) -> {
            switch(generationAlgorithms[generationDropdown.getSelectedIndex()]) {
                case "DFS":
                    MazePanel.getInstance().generate(GenerationType.DFS);
                    break;
                case "BFS":
                    MazePanel.getInstance().generate(GenerationType.BFS);
                    break;
                case "Hunt and Kill":
                    MazePanel.getInstance().generate(GenerationType.HUNT_AND_KILL);
                    break;
            }
        });
        add(generateButton);
        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton traverseButton = new JButton("Traverse Maze");
        traverseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        traverseButton.addActionListener((ActionEvent event) -> {
            switch(traversalAlgorithms[traversalDropdown.getSelectedIndex()]) {
                case "DFS":
                    MazePanel.getInstance().traverse(TraversalType.DFS);
                    break;
                case "BFS":
                    MazePanel.getInstance().traverse(TraversalType.BFS);
                    break;
                case "A*":
                    MazePanel.getInstance().traverse(TraversalType.A_STAR);
                    break;
                case "Best First Search":
                    MazePanel.getInstance().traverse(TraversalType.BEST_FIRST_SEARCH);
                    break;
            }
        });
        add(traverseButton);
        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton clearButton = new JButton("Clear Maze");
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearButton.addActionListener((ActionEvent event) -> MazePanel.getInstance().clear());
        add(clearButton);
    }
}