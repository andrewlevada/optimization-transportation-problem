package structures;

public interface Vector extends Iterable<Integer> {
    Vector plus(Vector other);
    Vector minus(Vector other);

    int get(int index);
    void set(int index, int value);

    int getLength();
    int getMinValueIndex();
    int getMaxValueIndex();
}
