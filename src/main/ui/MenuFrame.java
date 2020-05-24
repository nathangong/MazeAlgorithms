package main.ui;

import javax.swing.JFrame;

public class MenuFrame extends JFrame {
    public MenuFrame() {
        initUI();
    }

    private void initUI() {
        add(MenuPanel.getInstance());
        pack();

        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
