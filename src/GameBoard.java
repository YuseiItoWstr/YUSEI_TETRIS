import java.util.Arrays;

public class GameBoard {
    private final int rows;
    private final int cols;
    private final int[][] board;

    public GameBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new int[rows][cols];
        clear();
    }

    public void clear() {
        for (int[] row : board) {
            Arrays.fill(row, 0);
        }
    }

    public int getValue(int x, int y) {
        return board[y][x];
    }

    public void setValue(int x, int y, int value) {
        board[y][x] = value;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean isRowFull(int row) {
        for (int cell : board[row]) {
            if (cell == 0) {
                return false;
            }
        }
        return true;
    }

    public void removeRow(int row) {
        for (int currentRow = row; currentRow > 0; currentRow--) {
            board[currentRow] = Arrays.copyOf(board[currentRow - 1], cols);
        }
        Arrays.fill(board[0], 0);
    }

    public boolean isCellOccupied(int x, int y) {
        return board[y][x] != 0;
    }
}
