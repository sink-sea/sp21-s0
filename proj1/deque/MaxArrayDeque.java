package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> compare;

    public MaxArrayDeque(Comparator<T> c) {
        compare = c;
    }

    public T max() {
        if (isEmpty()) {
            return null;
        }
        T max = this.get(0);
        for (T val : this) {
            if (compare.compare(val, max) > 0) {
                max = val;
            }
        }
        return max;
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T max = this.get(0);
        for (T val : this) {
            if (c.compare(val, max) > 0) {
                max = val;
            }
        }
        return max;
    }

}
