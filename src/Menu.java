import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Menu extends JPanel {
    private static Menu menuInstance = null;

    private Menu() {
        setPreferredSize(new Dimension(200, 150));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel fpsLabel = new JLabel("Frames Per Second");
        fpsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(fpsLabel);

        JSlider fpsSlider = new JSlider(1, 105);
        fpsSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        fpsSlider.setValue(Constants.INITIAL_FPS);
        fpsSlider.setMajorTickSpacing(20);
        fpsSlider.setMinorTickSpacing(5);
        fpsSlider.setPaintTicks(true);
        fpsSlider.setPaintLabels(true);
        fpsSlider.addChangeListener(e -> Maze.getInstance().setDelay(fpsSlider.getValue()));
        add(fpsSlider);
        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton generateButton = new JButton("Generate Maze");
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