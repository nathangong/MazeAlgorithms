package main.ui;

import main.util.TraversalType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static main.util.Constants.INITIAL_DELAY;

public class MenuPanel extends JPanel {
    private static MenuPanel menuInstance = null;

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

        JButton generateButton = new JButton("Generate Maze");
        generateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        generateButton.addActionListener((ActionEvent event) -> MazePanel.getInstance().generate());
        add(generateButton);
        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton dfsTraverseButton = new JButton("Traverse Maze (DFS)");
        dfsTraverseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        dfsTraverseButton.addActionListener((ActionEvent event) -> MazePanel.getInstance().traverse(TraversalType.DFS));
        add(dfsTraverseButton);
        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton bfsTraverseButton = new JButton("Traverse Maze (BFS)");
        bfsTraverseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bfsTraverseButton.addActionListener((ActionEvent event) -> MazePanel.getInstance().traverse(TraversalType.BFS));
        add(bfsTraverseButton);
        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton aStarButton = new JButton("Traverse Maze (A*)");
        aStarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        aStarButton.addActionListener((ActionEvent event) -> MazePanel.getInstance().traverse(TraversalType.A_STAR));
        add(aStarButton);
        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton clearButton = new JButton("Clear Maze");
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearButton.addActionListener((ActionEvent event) -> MazePanel.getInstance().clear());
        add(clearButton);
    }

    public static MenuPanel getInstance() {
        if (menuInstance == null) {
            menuInstance = new MenuPanel();
        }
        return menuInstance;
    }
}