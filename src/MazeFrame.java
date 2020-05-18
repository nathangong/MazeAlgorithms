import javax.swing.JFrame;
import java.awt.*;

public class MazeFrame extends JFrame {
    public MazeFrame() {
        initUI();
    }

    private void initUI() {
        add(Maze.getInstance());

        getContentPane().setPreferredSize(new Dimension(Constants.CELLS*Constants.CELL_LENGTH, Constants.CELLS*Constants.CELL_LENGTH));
        pack();

        setTitle("Maze Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
