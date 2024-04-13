import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rules extends JPanel {
    public static final int EASY = 1;
    public static final int NORMAL = 2;
    public static final int HARD = 3;
    public static final int CRAZY = 4;
    private GameBoard board;
    private Tetrimino currentTetrimino;
    private List<Tetrimino> onecycletetriminoList;
    private List<Tetrimino> tetriminoList;
    private int nextTetriminoIndex;
    private int tetriX, tetriY;
    private boolean gameOver;
    private boolean gameOverSEPlayed = false;
    private boolean pause = false;
    private Timer gameTimer;
    private final int gameSpeed = 700;
    private int score;
    private Tetrimino holdedTetrimino;
    private boolean isHolded;
    private final Color[] tetriminoColors = {
        Color.black, Color.cyan, Color.orange, Color.blue,
        Color.magenta, Color.yellow, Color.red, Color.green
    };
    
    private final int[][][] tetriminoShapes = {
        {},
        {{0, 0, 0, 0}, {1, 1, 1, 1}, {0, 0, 0, 0}, {0, 0, 0, 0}}, // Iミノ
        {{0, 0, 0, 0}, {0, 0, 1, 0}, {1, 1, 1, 0}, {0, 0, 0, 0}}, // Lミノ
        {{0, 0, 0, 0}, {0, 1, 0, 0}, {0, 1, 1, 1}, {0, 0, 0, 0}}, // Jミノ
        {{0, 0, 0, 0}, {0, 1, 0, 0}, {1, 1, 1, 0}, {0, 0, 0, 0}}, // Tミノ
        {{0, 0, 0, 0}, {0, 1, 1, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}}, // Oミノ
        {{0, 0, 0, 0}, {1, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}}, // Zミノ
        {{0, 0, 0, 0}, {0, 1, 1, 0}, {1, 1, 0, 0}, {0, 0, 0, 0}}  // Sミノ
    };

    public Rules() {
        board = new GameBoard(20, 10);
        setPreferredSize(new Dimension(500, 600));
        setBackground(Color.black);
        addKeyListener(new TAdapter());
        setFocusable(true);
        // テトリミノのリストを初期化し、ランダムにシャッフルする
        tetriminoList = new ArrayList<>();
        onecycletetriminoList = new ArrayList<>();
        for (int i = 1; i < tetriminoShapes.length; i++) {
            onecycletetriminoList.add(new Tetrimino(tetriminoShapes[i], tetriminoColors[i]));
        }
        // とりあえず100巡くらい作っておけば十分でしょ
        for (int i = 0; i < 100; i++) {
            Collections.shuffle(onecycletetriminoList);
            for (int j = 0; j < 7; j++) {
                tetriminoList.add(onecycletetriminoList.get(j));
            }
        }  
        nextTetriminoIndex = 0;
        initGame();
        repaint();
    }

    private void initGame() {
        spawnTetrimino();
        gameOver = false;
        score = 0;
        isHolded = false; // ゲーム開始時に isHolded をリセット
        gameTimer = new Timer(gameSpeed, new GameCycle());
        gameTimer.start();
    }

    private void spawnTetrimino() {
        currentTetrimino = getRandomTetrimino();
        tetriX = board.getCols() / 2 - 2;
        tetriY = 0;
    
        if (!isMoveValid(currentTetrimino, tetriX, tetriY)) {
            gameOver = true;
            gameTimer.stop();
        } else {
            isHolded = false;
            holdedTetrimino = null;
        }
    }
    
    // テトリミノをランダムに選択するメソッド
    private Tetrimino getRandomTetrimino() {
        Tetrimino nextTetrimino = tetriminoList.get(nextTetriminoIndex);
        nextTetriminoIndex++;
        if (nextTetriminoIndex >= tetriminoList.size()) {
            nextTetriminoIndex = 0;
            Collections.shuffle(tetriminoList);
        }
        return nextTetrimino;
    }

    private void gameCycle() {
        if (isMoveValid(currentTetrimino, tetriX, tetriY + 1)) {
            tetriY++;
        } else {
            fixTetrimino();
            removeFullLines(getGraphics());
            spawnTetrimino();
        }
        repaint();
    }

    private boolean isMoveValid(Tetrimino tetrimino, int newX, int newY) {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (tetrimino.getShape()[y][x] != 0) {
                    int toX = newX + x;
                    int toY = newY + y;

                    if (toX < 0 || toX >= board.getCols() || toY < 0 || toY >= board.getRows() || board.isCellOccupied(toX, toY)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void fixTetrimino() {
    for (int y = 0; y < 4; y++) {
        for (int x = 0; x < 4; x++) {
            if (currentTetrimino.getShape()[y][x] != 0) {
                // テトロミノの色のインデックス（またはユニークな番号）を盤面に設定
                board.setValue(tetriX + x, tetriY + y, Arrays.asList(tetriminoColors).indexOf(currentTetrimino.getColor()) + 1);
            }
        }
    }
}


    private void removeFullLines(Graphics g) {
        int linesRemoved = 0;
        for (int y = 0; y < board.getRows(); y++) {
            if (board.isRowFull(y)) {
                board.removeRow(y);
                linesRemoved++;
            }
        }
        score += linesRemoved * 100;
        if (linesRemoved >= 1 && linesRemoved <= 3) {
            AudioPlayer.load123lineSE();
        }
        if (linesRemoved == 4) {
            drawTetris(g);
            AudioPlayer.loadTetrisSE();
        }
    }

    private int[] getShadowPosition(Tetrimino tetrimino, int tetriX, int tetriY) {
        int shadowX = tetriX;
        int shadowY = tetriY;
    
        while (isMoveValid(tetrimino, shadowX, shadowY + 1)) {
            shadowY++;
        }
    
        return new int[] { shadowX, shadowY };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        if (gameOver) {
            drawGameOver(g);
        }
        if (pause) {
            drawPause(g);
        }
        // 「NEXT」の文字列を描画
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("NEXT", board.getCols() * 30 + 10, 20); // 右上に描画
    }

    private void drawBoard(Graphics g) {
        // ボード全体を描画
        for (int y = 0; y < board.getRows(); y++) {
            for (int x = 0; x < board.getCols(); x++) {
                int blockValue = board.getValue(x, y);
                if (blockValue != 0) {
                    g.setColor(tetriminoColors[blockValue - 1]);
                    g.fillRect(x * 30, y * 30, 30, 30);
                }
                // マス目を描画
                g.setColor(Color.DARK_GRAY);
                g.drawRect(x * 30, y * 30, 30, 30);
            }
        }
    
        // 現在のテトリミノを描画
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (currentTetrimino.getShape()[y][x] != 0) {
                    g.setColor(currentTetrimino.getColor());
                    g.fillRect((tetriX + x) * 30, (tetriY + y) * 30, 30, 30);
                }
            }
        }

        // シャドーを描画
        int[] shadowPosition = getShadowPosition(currentTetrimino, tetriX, tetriY);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (currentTetrimino.getShape()[y][x] != 0) {
                    g.setColor(new Color(currentTetrimino.getColor().getRed(), currentTetrimino.getColor().getGreen(), currentTetrimino.getColor().getBlue(), 100));
                    g.fillRect((shadowPosition[0] + x) * 30, (shadowPosition[1] + y) * 30, 30, 30);
                }
            }
        }
    
        // ホールドしたテトリミノを描画
        drawHoldedTetrimino(g);
    
        // 次の5つのテトリミノを描画
        drawNextTetriminos(g);
    }

    private void drawTetris(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 48));
        FontMetrics fm = g.getFontMetrics();
        String tetrisText = "T E T R I S !";
        long startTime = System.currentTimeMillis();
        long duration = 500; // 表示時間（ミリ秒）
        
        while (System.currentTimeMillis() - startTime < duration) {
            g.drawString(tetrisText, (getWidth() - 195 - fm.stringWidth(tetrisText)) / 2, getHeight() / 2);
        }
    }

    private class GameCycle implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            gameCycle();
        }
    }

    private void drawHoldedTetrimino(Graphics g) {
        if (holdedTetrimino != null) {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    if (holdedTetrimino.getShape()[y][x] != 0) {
                        g.setColor(holdedTetrimino.getColor());
                        g.fillRect(x * 30 - 30, y * 30, 30, 30);
                    }
                }
            }
        }
    }

    private void drawPause(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        FontMetrics fm = g.getFontMetrics();
        String PauseText = "Pause";
        g.drawString(PauseText, (getWidth() - 195 - fm.stringWidth(PauseText)) / 2, getHeight() / 2);
    }

    private void drawNextTetriminos(Graphics g) {
        int startX = board.getCols() * 30 + 50;
        int startY = 30;
    
        // 次の5つのテトリミノを描画
        for (int i = 0; i < 5; i++) {
            int nextIndex = (nextTetriminoIndex + i) % tetriminoList.size();
            Tetrimino nextTetrimino = tetriminoList.get(nextIndex);
    
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    if (nextTetrimino.getShape()[y][x] != 0) {
                        g.setColor(nextTetrimino.getColor());
                        g.fillRect(startX + x * 30, startY + i * 120 + y * 30, 30, 30);
                    }
                }
            }
        }
    }
    
    private void drawGameOver(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        FontMetrics fm = g.getFontMetrics();
        String gameOverText = "Game Over";
        g.drawString(gameOverText, (getWidth() - fm.stringWidth(gameOverText)) / 2, getHeight() / 2);

        g.drawString("Score: " + score, (getWidth() - fm.stringWidth("Score: " + score)) / 2, getHeight() / 2 + fm.getHeight());
        if (!gameOverSEPlayed) { // ゲームオーバーSEが再生されていない場合にのみ再生する
            AudioPlayer.loadGameOverSE();
            gameOverSEPlayed = true; // ゲームオーバーSEが再生されたことを記録
        }
        gameOver = true;
        gameTimer.stop();
        repaint();
    }

    private void hold() {
        if (!isHolded) {
            if (holdedTetrimino == null) {
                holdedTetrimino = currentTetrimino;
                spawnTetrimino();
            } else {
                Tetrimino temp = currentTetrimino;
                currentTetrimino = holdedTetrimino;
                holdedTetrimino = temp;
                tetriX = board.getCols() / 2 - 2;
                tetriY = 0;
            }
            isHolded = true;
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (gameOver) {
                return;
            }

            int keycode = e.getKeyCode();

            switch (keycode) {
                case KeyEvent.VK_LEFT:
                // テトリミノを左に移動
                    if (isMoveValid(currentTetrimino, tetriX - 1, tetriY)) {
                        tetriX--;
                    }
                    AudioPlayer.loadMoveSE();
                    break;

                case KeyEvent.VK_RIGHT:
                // テトリミノを右に移動
                    if (isMoveValid(currentTetrimino, tetriX + 1, tetriY)) {
                        tetriX++;
                    }
                    AudioPlayer.loadMoveSE();
                    break;

                case KeyEvent.VK_UP:
                // ハードドロップ
                    while (isMoveValid(currentTetrimino, tetriX, tetriY + 1)) {
                        tetriY++;
                    }
                    AudioPlayer.loadHardDropSE();
                    break;

                case KeyEvent.VK_DOWN:
                // テトリミノを下に移動
                if (isMoveValid(currentTetrimino, tetriX, tetriY - 1)) {
                    tetriY++;
                    }
                    break;
    
                case KeyEvent.VK_X:
                // テトリミノを右回転
                    Tetrimino rotatedRight = currentTetrimino.rotateRight();
                    if (isMoveValid(rotatedRight, tetriX, tetriY)) {
                        currentTetrimino = rotatedRight;
                    }
                    AudioPlayer.loadRotateSE();
                    break;

                case KeyEvent.VK_Z:
                // テトリミノを左回転
                    Tetrimino rotatedLeft = currentTetrimino.rotateLeft();
                    if (isMoveValid(rotatedLeft, tetriX, tetriY)) {
                        currentTetrimino = rotatedLeft;
                    }
                    AudioPlayer.loadRotateSE();
                    break;

                case KeyEvent.VK_SPACE:
                // ホールド(現在はただのテトリミノのランダムチェンジ)
                hold();
                AudioPlayer.loadSwitchSE();
                break;

                case KeyEvent.VK_P:
                // ゲームの一時停止
                if (gameTimer.isRunning()) {
                    gameTimer.stop();
                } else {
                    gameTimer.start();
                }
                pause = !pause;
                break;

                case KeyEvent.VK_R:
                // ゲームのリスタート
                initGame();
                break;
                }
            repaint();
        }
    }
}
