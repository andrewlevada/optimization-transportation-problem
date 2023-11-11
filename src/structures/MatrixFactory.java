package structures;

import structures.implementations.ArrayMatrix;

import java.util.Scanner;

public class MatrixFactory {
    public static Matrix createEmptyMatrix(int rows, int cols) {
        return new ArrayMatrix(rows, cols);
    }

    public static Matrix createMatrixFromInput(int rows, int cols, Scanner scanner) {
        Matrix matrix = new ArrayMatrix(rows, cols);

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                matrix.setItem(i, j, scanner.nextInt());

        return matrix;
    }
}
