package structures;

public interface Matrix {
    Matrix plus(Matrix other);
    Matrix minus(Matrix other);

    int getItem(int row, int col);
    Vector getRow(int row);
    Vector getColumn(int col);

    int getNumberOfRows();
    int getNumberOfColumns();

    void setItem(int row, int col, int value);
    void setRow(int row, Vector vector);
    void setColumn(int col, Vector vector);

    int getIndexOfMinInRow(int row);
    int getIndexOfMaxInRow(int row);
    int getIndexOfMinInColumn(int col);
    int getIndexOfMaxInColumn(int col);

    int[] getCoordsOfMostNegative();
}
