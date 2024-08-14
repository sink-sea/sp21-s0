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

    private void checkCapacity(boolean isAdd) {
        if (isAdd && size + 1 >= capacity) {
            resize(capacity * 2);
        } else if ((!isAdd) && size - 1 < capacity / 2) {
            resize(capacity / 2);
        }
    }

    private void resize(int newCapacity) {
        T[] newItems = (T[]) new Object[newCapacity];
        for (int i = head; i < tail; i += 1) {
            newItems[i - head] = items[(i + capacity) % capacity];
        }
        capacity = newCapacity;
        items = newItems;
        head = 0;
        tail = size;
    }

//    public boolean isEmpty() {
//        return size == 0;
//    }

    @Override
    public void addFirst(T item) {
        checkCapacity(true);
        head -= 1;
        size += 1;
        if (head == -capacity) {
            head = 0;
            tail = size;
        }
        items[(head + capacity) % capacity] = item;
    }

    @Override
    public void addLast(T item) {
        checkCapacity(true);
        if (tail == capacity) {
            tail = 0;
            head = -size;
        }
        items[(tail + capacity) % capacity] = item;
        tail += 1;
        size += 1;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        checkCapacity(false);
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

        checkCapacity(false);
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
        if (o == this) {
            return true;
        }
        if (o instanceof Deque) {
            Deque<T> that = (Deque<T>) o;
            if (this.size() != that.size()) {
                return false;
            }
            for (int i = 0; i < size; i += 1) {
                if (!this.get(i).equals(that.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
