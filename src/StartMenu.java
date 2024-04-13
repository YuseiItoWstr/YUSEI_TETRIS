import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartMenu extends JFrame implements ActionListener {
    private JButton startButton, exitButton;
    private JLabel titleLabel;

    public StartMenu() {
        setTitle("Tetris");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // パネルの背景色を設定
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // グラデーション背景を描画
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                Color color1 = new Color(64, 164, 223);
                Color color2 = new Color(119, 210, 250);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        
        // タイトルラベルのスタイルを設定
        titleLabel = new JLabel("TETRIS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.BLACK);
        panel.add(titleLabel, createConstraints(0, 0, 1, 1, GridBagConstraints.BOTH, 20, 0, 20, 0));

        // スタートボタンのスタイルを設定
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setBackground(new Color(0, 153, 51));
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        panel.add(startButton, createConstraints(0, 1, 1, 1, GridBagConstraints.BOTH, 0, 0, 20, 0));

        // 終了ボタンのスタイルを設定
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setBackground(new Color(204, 0, 0));
        exitButton.setForeground(Color.BLACK);
        exitButton.setFocusPainted(false);
        panel.add(exitButton, createConstraints(0, 2, 1, 1, GridBagConstraints.BOTH, 0, 0, 0, 0));

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            dispose(); // スタートメニューを閉じる
            new Main(); // テトリスのメインゲームを開始する

        } else if (e.getSource() == exitButton) {
            System.exit(0); // アプリケーションを終了する
        }
    }

    // GridBagConstraintsを作成するヘルパーメソッド
    private GridBagConstraints createConstraints(int gridx, int gridy, int gridwidth, int gridheight, int fill, int top, int left, int bottom, int right) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.fill = fill;
        gbc.insets = new Insets(top, left, bottom, right);
        return gbc;
    }

    public static void main(String[] args) {
        new StartMenu();
    }
}
