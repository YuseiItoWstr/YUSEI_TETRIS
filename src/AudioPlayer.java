import javax.sound.sampled.*;
import java.io.InputStream;

public class AudioPlayer {
    public static void playAudio(String filePath) {
        try {
            // ファイルパスを指定して音声ファイルを開く
            InputStream audioInputStream = AudioPlayer.class.getResourceAsStream(filePath);

            // オーディオ形式を取得
            AudioInputStream ais = AudioSystem.getAudioInputStream(audioInputStream);
            AudioFormat format = ais.getFormat();

            // オーディオデータを読み込む
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesRead = 0;
            while ((bytesRead = ais.read(buffer, 0, buffer.length)) != -1) {
                line.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadBGMAudio() {
        playAudio("audio/BGM.wav");
    }

    public static void loadMoveSE() {
        playAudio("audio/MoveSE.wav");
    }

    public static void loadRotateSE() {
        playAudio("audio/RotateSE.wav");
    }

    public static void loadHardDropSE() {
        playAudio("audio/HardDropSE.wav"); 
    }

    public static void loadSwitchSE() {
        playAudio("audio/SwitchSE.wav"); 
    }

    public static void loadTetrisSE() {
        playAudio("audio/TetrisSE.wav");
    }

    public static void load123lineSE() {
        playAudio("audio/123lineSE.wav");
    }

    public static void loadGameOverSE() {
        playAudio("audio/GameOver.wav");
    }
}
