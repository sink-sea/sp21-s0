package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private T[] items;
    private int size;
    private int capacity;
    private int head;
    private int tail;

    public ArrayDeque() {
        capacity = 8;
        size = head = tail = 0;
        items = (T[]) new Object[capacity];
    }

    public void checkCapacity() {
        if (size + 1 >= capacity) {
            int newCapacity = capacity * 2;
            T[] newItems = (T[]) new Object[newCapacity];
            for (int i = head; i < tail; i += 1) {
                newItems[(i + newCapacity) % newCapacity] = items[(i + capacity) % capacity];
            }
            capacity *= 2;
            items = newItems;
        }
    }

//    public boolean isEmpty() {
//        return size == 0;
//    }

    @Override
    public void addFirst(T item) {
        checkCapacity();
        head -= 1;
        items[(head + capacity) % capacity] = item;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        checkCapacity();
        items[(tail + capacity) % capacity] = item;
        tail += 1;
        size += 1;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T item = items[(head + capacity) % capacity];
        head += 1;
        size -= 1;

        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        tail -= 1;
        T item = items[(tail + capacity) % capacity];
        size -= 1;

        return item;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return items[(head + index + capacity) % capacity];
    }

    @Override
    public void printDeque() {
        for (int i = head; i < tail; i += 1) {
            System.out.print(items[(i + capacity) % capacity] + " ");
        }
        System.out.println();
    }

    private class ArrayIterator implements Iterator<T> {
        int index;

        ArrayIterator() {
            index = 0;
        }

        public boolean hasNext() {
            return index < size;
        }

        public T next() {
            T val = items[(index + head + capacity) % capacity];
            index += 1;
            return val;
        }

        public Iterator<T> iterator() {
            return this;
        }
    }

    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    public boolean equals(Object o) {
        if (o instanceof Deque) {
            if (size != ((Deque<?>) o).size()) {
                return false;
            }
            for (int i = 0; i < size; i += 1) {
                if (get(i) != ((Deque<?>) o).get(i)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
