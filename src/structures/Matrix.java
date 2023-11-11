package structures;

import java.util.List;

public interface Matrix {
    Matrix plus(Matrix other);
    Matrix minus(Matrix other);

    List<Vector> getRows();
    List<Vector> getColumns();

    int getItem(int row, int col);
    Vector getRow(int row);
    Vector getColumn(int col);

    int getNumberOfRows();
    int getNumberOfColumns();

    void setItem(int row, int col, int value);
    void setRow(int row, Vector vector);
    void setColumn(int col, Vector vector);

    int[] getCoordsOfMostNegative();

    Matrix buildClone();
}
