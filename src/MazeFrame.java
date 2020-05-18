import javax.swing.JFrame;

public class MazeFrame extends JFrame {
    public MazeFrame() {
        initUI();
    }

    private void initUI() {
        add(Maze.getInstance());

        setSize(Constants.CELLS*Constants.CELL_LENGTH, Constants.CELLS*Constants.CELL_LENGTH);

        setTitle("Maze Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
