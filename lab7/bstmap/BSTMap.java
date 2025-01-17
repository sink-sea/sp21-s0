package bstmap;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;
        BSTNode parent;

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private BSTNode root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    private BSTNode getNode (K key) {
        BSTNode node = root;
        while (node != null) {
            if (node.key.compareTo(key) > 0) {
                node = node.left;
            } else if (node.key.compareTo(key) < 0) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("call containsKey with a null key");
        }
        return getNode(key) != null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("call get() with a null key");
        }
        BSTNode node = getNode(key);
        if (node != null) {
            return node.value;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("call put() with a null key");
        }

        if (root == null) {
            root = new BSTNode(key, value);
            size ++;
            return;
        }

        BSTNode node = root;
        while (true) {
            if (node.key.compareTo(key) > 0) {
                if (node.left == null) {
                    node.left = new BSTNode(key, value);
                    node.left.parent = node;
                    size ++;
                    return;
                }
                node = node.left;
            } else if (node.key.compareTo(key) < 0) {
                if (node.right == null) {
                    node.right = new BSTNode(key, value);
                    node.right.parent = node;
                    size ++;
                    return;
                }
                node = node.right;
            } else {
                return;
            }
        }
    }

    private void inOrderTraversal(BSTNode root, Set<K> kSet) {
        if (root == null) {
            return;
        }
        inOrderTraversal(root.left, kSet);
        kSet.add(root.key);
        inOrderTraversal(root.right, kSet);
    }

    @Override
    public Set<K> keySet() {
        Set<K> kSet = new TreeSet<>();
        inOrderTraversal(root, kSet);
        return kSet;
    }

    private V removeNode(BSTNode node) {
        if (node == null) {
            return null;
        }
        V nodeVal = node.value;
        BSTNode leaf = null;
        while (true) {
            if (node.left == null && node.right == null) {
                size --;
                if (node == root) {
                    root = null;
                } else if (node.parent.left == node) {
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
                return nodeVal;
            } else if (node.left != null) {
                leaf = node.left;
                while (leaf.right != null) {
                    leaf = leaf.right;
                }
            } else {
                leaf = node.right;
                while (leaf.left != null) {
                    leaf = leaf.left;
                }
            }

            node.key = leaf.key;
            node.value = leaf.value;
            node = leaf;
        }
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("call remove() with a null key");
        }
        return removeNode(getNode(key));
    }

    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("call remove() with a null key");
        }
        BSTNode node = getNode(key);
        if (node == null || node.value != value) {
            return null;
        }
        return removeNode(node);
    }

    private class BSTMapIter implements Iterator<K> {
        public BSTMapIter() {
            kIterator = keySet().iterator();
        }

        @Override
        public boolean hasNext() {
            return kIterator.hasNext();
        }

        @Override
        public K next() {
            return kIterator.next();
        }
        Iterator<K> kIterator;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIter();
    }

    public void printInOrder() {
        for (K key : this) {
            System.out.print("key: ");
            System.out.print(key);
            System.out.print(", value: ");
            System.out.println(get(key));
        }
    }
}
