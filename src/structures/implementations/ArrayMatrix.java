package structures.implementations;

import structures.Matrix;
import structures.Vector;

/**
 * Class of Matrices
 */
public class ArrayMatrix implements Matrix {
    private final int rows;
    private final int cols;
    private final int[][] values;

    /**
     * Matrix constructor (sets all elements to 0)
     * @param rows number of rows
     * @param cols number of columns
     */
    public ArrayMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.values = new int[rows][cols];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                this.values[i][j] = 0;
    }

    /**
     * Function for adding two matrices
     * @param other is a matrix which is to be added to source matrix
     * @return new matrix which is a result of addition
     */
    @Override
    public Matrix plus(Matrix other) {
        if (this.rows != other.getNumberOfRows() || this.cols != other.getNumberOfColumns())
            throw new IllegalArgumentException();

        Matrix result = new ArrayMatrix(this.rows, this.cols);

        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                result.setItem(i, j, this.values[i][j] + other.getItem(i, j));

        return result;
    }

    /**
     * Function for subtracting one matrix for another
     * @param other is a matrix which is to be subtracted from source matrix
     * @return new matrix which is a result of subtraction
     */
    @Override
    public Matrix minus(Matrix other) {
        if (this.rows != other.getNumberOfRows() || this.cols != other.getNumberOfColumns())
            throw new IllegalArgumentException();

        return this.plus(scalarMultiply(other, -1));
    }

    /**
     * Method for accessing a specific element of a matrix
     * @param row is a row position of the element
     * @param col is a column position of the element
     * @return the specific element
     */
    @Override
    public int getItem(int row, int col) {
        if (row >= this.rows || col >= this.cols || row < 0 || col < 0)
            throw new IndexOutOfBoundsException();

        return values[row][col];
    }

    @Override
    public int getNumberOfRows() {
        return this.rows;
    }

    @Override
    public int getNumberOfColumns() {
        return this.cols;
    }

    @Override
    public Vector getRow(int row) {
        if (row >= this.rows || row < 0)
            throw new IndexOutOfBoundsException();

        Vector v = new ArrayVector(this.cols);

        for (int i = 0; i < this.cols; i++)
            v.set(i, this.values[row][i]);

        return v;
    }

    @Override
    public Vector getColumn(int col) {
        if (col >= this.cols || col < 0)
            throw new IndexOutOfBoundsException();

        Vector v = new ArrayVector(this.rows);

        for (int i = 0; i < this.rows; i++)
            v.set(i, this.values[i][col]);

        return v;
    }

    @Override
    public void setItem(int row, int col, int value) {
        if (row >= this.rows || col >= this.cols || row < 0 || col < 0)
            throw new IndexOutOfBoundsException();

        this.values[row][col] = value;
    }

    @Override
    public void setRow(int row, Vector vector) {
        if (vector.getLength() != this.cols)
            throw new IllegalArgumentException();

        if (row >= this.rows || row < 0)
            throw new IndexOutOfBoundsException();

        for (int i = 0; i < this.cols; i++)
            this.values[row][i] = vector.get(i);
    }

    @Override
    public void setColumn(int col, Vector vector) {
        if (vector.getLength() != this.rows)
            throw new IllegalArgumentException();

        if (col >= this.cols || col < 0)
            throw new IndexOutOfBoundsException();

        for (int i = 0; i < this.rows; i++)
            this.values[i][col] = vector.get(i);
    }

    @Override
    public int getIndexOfMinInRow(int row) {
        int minIndex = 0;

        for (int i = 1; i < this.cols; i++)
            if (this.values[row][i] < this.values[row][minIndex])
                minIndex = i;

        return minIndex;
    }

    @Override
    public int getIndexOfMaxInRow(int row) {
        int maxIndex = 0;

        for (int i = 1; i < this.cols; i++)
            if (this.values[row][i] > this.values[row][maxIndex])
                maxIndex = i;

        return maxIndex;
    }

    @Override
    public int getIndexOfMinInColumn(int col) {
        int minIndex = 0;

        for (int i = 1; i < this.rows; i++)
            if (this.values[i][col] < this.values[minIndex][col])
                minIndex = i;

        return minIndex;
    }

    @Override
    public int getIndexOfMaxInColumn(int col) {
        int maxIndex = 0;

        for (int i = 1; i < this.rows; i++)
            if (this.values[i][col] > this.values[maxIndex][col])
                maxIndex = i;

        return maxIndex;
    }

    @Override
    public int[] getCoordsOfMostNegative() {
        int[] coords = new int[2];
        int min = this.values[0][0];

        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                if (this.values[i][j] < min) {
                    min = this.values[i][j];
                    coords[0] = i;
                    coords[1] = j;
                }

        return coords;
    }

    private static Matrix scalarMultiply(Matrix matrix, int scalar) {
        Matrix result = new ArrayMatrix(matrix.getNumberOfRows(), matrix.getNumberOfColumns());

        for (int i = 0; i < matrix.getNumberOfRows(); i++)
            for (int j = 0; j < matrix.getNumberOfColumns(); j++)
                result.setItem(i, j, matrix.getItem(i, j) * scalar);

        return result;
    }
}
