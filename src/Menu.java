import javax.swing.*;
import java.awt.event.ActionEvent;

public class Menu extends JPanel {
    private static Menu menuInstance = null;

    private Menu() {
        JButton generateButton = new JButton("Generate Maze");
        generateButton.addActionListener((ActionEvent event) -> {
            System.out.println("Generating maze...");
        });
        add(generateButton);
    }

    public static Menu getInstance() {
        if (menuInstance == null) {
            menuInstance = new Menu();
        }
        return menuInstance;
    }
}