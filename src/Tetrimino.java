import java.awt.Color;

public class Tetrimino {
    private final int[][] shape;
    private final Color color;

    public Tetrimino(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public int[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public Tetrimino rotateRight() {
        int size = shape.length;
        int[][] newShape = new int[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                newShape[y][x] = shape[size - x - 1][y];
            }
        }
        return new Tetrimino(newShape, color);
    }

    public Tetrimino rotateLeft() {
        int size = shape.length;
        int[][] newShape = new int[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                newShape[size - y - 1][x] = shape[x][y];
            }
        }
        return new Tetrimino(newShape, color);
    }
    
}
