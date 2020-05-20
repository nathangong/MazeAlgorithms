package main;

import javax.swing.JFrame;

public class MenuFrame extends JFrame {
    public MenuFrame() {
        initUI();
    }

    private void initUI() {
        add(Menu.getInstance());
        pack();

        setTitle("main.Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
