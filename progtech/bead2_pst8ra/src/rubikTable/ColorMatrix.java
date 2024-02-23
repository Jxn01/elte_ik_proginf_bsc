package rubikTable;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class ColorMatrix
 *
 * @author jxn
 */
public class ColorMatrix {

    /**
     * An ArrayList storing the colors of the matrix, in a list format.
     */
    private ArrayList<Color> colors;
    /**
     * An int, stores the matrix's size.
     */
    private int n;

    /**
     * A Color[][] matrix, stores the colors in a matrix format.
     */
    private Color[][] matrix;

    /**
     * Constructs a ColorMatrix Object, initializes the class variables, then
     * depending on the size of the matrix, generates 2, 4 or 6 types of colors,
     * and then puts n of each type of color into the colors ArrayList. Then
     * shuffles the ArrayList, and copies the colors into the matrix.
     *
     * @param n The size of the matrix.
     */
    public ColorMatrix(int n) {
        this.colors = new ArrayList();
        this.matrix = new Color[n][n];
        this.n = n;

        for (int i = 0; i < n; i++) {
            colors.add(Color.RED);
            colors.add(Color.BLUE);
            if (n >= 4) {
                colors.add(Color.GREEN);
                colors.add(Color.ORANGE);
            }
            if (n == 6) {
                colors.add(Color.CYAN);
                colors.add(Color.YELLOW);
            }
        }

        Collections.shuffle(colors);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = colors.remove(0);
            }
        }
    }

    /**
     *
     * @return n
     */
    public int getN() {
        return n;
    }

    /**
     *
     * @return the Color[][]
     */
    public Color[][] getMatrix() {
        return matrix;
    }

    /**
     * Shifts the elements of the specified column upwards (cyclically).
     *
     * @param col the index of the column which needs to be shifted
     */
    public void shiftUp(int col) {
        Color temp = matrix[0][col];
        for (int i = 0; i < n - 1; i++) {
            matrix[i][col] = matrix[i + 1][col];
        }
        matrix[n - 1][col] = temp;
    }

    /**
     * Shifts the elements of the specified column downwards (cyclically).
     *
     * @param col the index of the column which needs to be shifted
     */
    public void shiftDown(int col) {
        Color temp = matrix[n - 1][col];
        for (int i = n - 1; i > 0; i--) {
            matrix[i][col] = matrix[i - 1][col];
        }
        matrix[0][col] = temp;
    }

    /**
     * Shifts the elements of the specified row rightwards (cyclically).
     *
     * @param row the index of the row which needs to be shifted
     */
    public void shiftRight(int row) {
        Color temp = matrix[row][n - 1];
        for (int i = n - 1; i > 0; i--) {
            matrix[row][i] = matrix[row][i - 1];
        }
        matrix[row][0] = temp;
    }

    /**
     * Shifts the elements of the specified row leftwards (cyclically).
     *
     * @param row the index of the row which needs to be shifted
     */
    public void shiftLeft(int row) {
        Color temp = matrix[row][0];
        for (int i = 0; i < n - 1; i++) {
            matrix[row][i] = matrix[row][i + 1];
        }
        matrix[row][n - 1] = temp;
    }

    /**
     * Checks the matrix to determine if the game has been completed or not.
     *
     * @return a boolean, true if the game is completed, false if not.
     */
    public boolean isComplete() {
        boolean result = true;
        for (int i = 0; i < n && result; i++) {
            for (int j = 0; j < n && result; j++) {
                if (matrix[0][i] != matrix[j][i]) {
                    result = false;
                }
            }
        }

        if (!result) {
            result = true;
            for (int i = 0; i < n && result; i++) {
                for (int j = 0; j < n && result; j++) {
                    if (matrix[i][0] != matrix[i][j]) {
                        result = false;
                    }
                }
            }
        }
        return result;
    }
}
