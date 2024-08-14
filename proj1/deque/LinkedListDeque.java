package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    public LinkedListDeque() {
        head = new LinkedNode<>();
        tail = new LinkedNode<>();
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    private static class LinkedNode<K> {
        K value;
        LinkedNode<K> next;
        LinkedNode<K> prev;

        public LinkedNode() {
            next = prev = null;
        }
        public LinkedNode(K item) {
            value = item;
            next = prev =  null;
        }
    }

//    public boolean isEmpty() {
//        return size == 0;
//    }

    @Override
    public void addFirst(T item) {
        LinkedNode<T> node = new LinkedNode<>(item);
        head.next.prev = node;
        node.next = head.next;
        head.next = node;
        node.prev = head;

        size += 1;
    }

    @Override
    public void addLast(T item) {
        LinkedNode<T> node = new LinkedNode<>(item);
        tail.prev.next = node;
        node.prev = tail.prev;
        node.next = tail;
        tail.prev = node;

        size += 1;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        LinkedNode<T> node = head.next;
        node.next.prev = head;
        head.next = node.next;
        size -= 1;

        return node.value;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        LinkedNode<T> node = tail.prev;
        node.prev.next = tail;
        tail.prev = node.prev;
        size -= 1;

        return node.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (LinkedNode<T> pNode = head.next; pNode != tail; pNode = pNode.next) {
            System.out.print(pNode.value + " ");
        }
        System.out.println();
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }

        LinkedNode<T> pNode = head.next;
        for (int i = 0; i < index; i += 1) {
            pNode = pNode.next;
        }

        return pNode.value;
    }


    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }

        LinkedNode<T> node = head.next;
        if (index == 0) {
            return node.value;
        }
        head = head.next;
        T val = getRecursive(index - 1);
        head = node.prev;
        return val;
    }

    private class ListIterator implements Iterator<T> {
        LinkedNode<T> node;

        ListIterator() {
            node = head.next;
        }

        public boolean hasNext() {
            return node != tail;
        }

        public T next() {
            T val = node.value;
            node = node.next;
            return val;
        }

        public Iterator<T> iterator() {
            return this;
        }
    }

    public Iterator<T> iterator() {
        return new ListIterator();
    }

    public boolean equals(Object o) {
        if (o instanceof LinkedListDeque) {
            if (size != ((LinkedListDeque<?>) o).size()) {
                return false;
            }
            for (int i = 0; i < size; i += 1) {
                if (get(i) != ((LinkedListDeque<?>) o).get(i)) {
                    return false;
                }
            }
            return true;
        } else {
          return false;
        }
    }

}
