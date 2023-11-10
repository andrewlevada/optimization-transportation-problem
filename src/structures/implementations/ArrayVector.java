package structures.implementations;

import structures.Vector;

import java.util.Iterator;

public class ArrayVector implements Vector {
    private final int length;
    private final int[] values;

    public ArrayVector(int length) {
        this.length = length;
        this.values = new int[length];

        for (int i = 0; i < length; i++)
            this.values[i] = 0;
    }

    @Override
    public Vector plus(Vector other) {
        if (this.length != other.getLength())
            throw new IllegalArgumentException();

        Vector result = new ArrayVector(this.length);

        for (int i = 0; i < this.length; i++)
            result.set(i, this.values[i] + other.get(i));

        return result;
    }

    @Override
    public Vector minus(Vector other) {
        if (this.length != other.getLength())
            throw new IllegalArgumentException();

        return this.plus(scalarMultiply(other, -1));
    }

    @Override
    public int get(int index) {
        if (index < 0 || index >= length)
            throw new IndexOutOfBoundsException();

        return values[index];
    }

    @Override
    public void set(int index, int value) {
        if (index < 0 || index >= length)
            throw new IndexOutOfBoundsException();

        values[index] = value;
    }

    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    public int getMinValueIndex() {
        int minIndex = 0;

        for (int i = 1; i < length; i++)
            if (values[i] < values[minIndex])
                minIndex = i;

        return minIndex;
    }

    @Override
    public int getMaxValueIndex() {
        int maxIndex = 0;

        for (int i = 1; i < length; i++)
            if (values[i] > values[maxIndex])
                maxIndex = i;

        return maxIndex;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < length;
            }

            @Override
            public Integer next() {
                return values[index++];
            }
        };
    }

    private static Vector scalarMultiply(Vector vector, int scalar) {
        Vector result = new ArrayVector(vector.getLength());

        for (int i = 0; i < vector.getLength(); i++)
            result.set(i, vector.get(i) * scalar);

        return result;
    }

}
