import javax.swing.JFrame;

public class Main extends JFrame {
    public Main() {
        add(new Rules());
        setTitle("Yusei's TETRIS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Main::new);
        new StartMenu();
        AudioPlayer.loadBGMAudio();
    }
}

