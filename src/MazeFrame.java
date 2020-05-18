import javax.swing.JFrame;

public class MazeFrame extends JFrame {
    public MazeFrame() {
        initUI();
    }

    private void initUI() {
        add(Maze.getInstance());
        pack();

        setTitle("Maze Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
