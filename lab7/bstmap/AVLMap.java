package bstmap;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class AVLMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    private class AVLNode {
        K key;
        V value;
        AVLNode left;
        AVLNode right;
        AVLNode parent;
        int height;
        boolean modified;

        public AVLNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 1;
            this.modified = false;
        }
    }

    private int getHeight(AVLNode root) {
        if (root == null) {
            return 0;
        }
        if (root.modified) {
            root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
            root.modified = false;
        }
        return root.height;
    }

    private int getBF(AVLNode root) {
        if (root == null) {
            return 0;
        }
        return getHeight(root.left) - getHeight(root.right);
    }

    private AVLNode root;
    private int size;

    public AVLMap() {
        root = null;
        size = 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    private AVLNode getNode(K key) {
        AVLNode node = root;
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
            throw new IllegalArgumentException("call containsKey() with null key");
        }
        return getNode(key) != null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("call get() with null key");
        }
        AVLNode node = getNode(key);
        if (node != null) {
            return node.value;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    private void LeftRotate(AVLNode A) {
        if (A == null || A.right == null) {
            return;
        }
        AVLNode B = A.right;
        A.right = B.left;
        if (A.right != null) {
            A.right.parent = A;
        }
        B.left = A;
        B.parent = A.parent;

        if (A.parent == null) {
            root = B;
        } else if (A.parent.left == A) {
            A.parent.left = B;
        } else {
            A.parent.right = B;
        }
        A.parent = B;

        A.modified = true;
        B.modified = true;
    }

    private void RightRotate(AVLNode B) {
        if (B == null || B.left == null) {
            return;
        }
        AVLNode A = B.left;
        B.left = A.right;
        if (B.left != null) {
            B.left.parent = B;
        }
        A.right = B;
        A.parent = B.parent;

        if (B.parent == null) {
            root = A;
        } else if (B.parent.left == B) {
            B.parent.left = A;
        } else {
            B.parent.right = A;
        }
        B.parent = A;

        A.modified = true;
        B.modified = true;
    }

    // LL-type
    private void LLRotate(AVLNode node) {
        if (node == null) {
            throw new IllegalArgumentException("call LLRotate() with null node");
        }
        RightRotate(node);
    }

    // RR-type
    private void RRRotate(AVLNode node) {
        if (node == null) {
            throw new IllegalArgumentException("call RRRotate() with null node");
        }
        LeftRotate(node);
    }

    // LR-type
    private void LRRotate(AVLNode node) {
        if (node == null) {
            throw new IllegalArgumentException("call LRRotate() with null node");
        }
        LeftRotate(node.left);
        RightRotate(node);
    }

    // RL-type
    private void RLRotate(AVLNode node) {
        if (node == null) {
            throw new IllegalArgumentException("call RLRotate() with null node");
        }
        RightRotate(node.right);
        LeftRotate(node);
    }

    private void AVLAdjust(AVLNode node) {
        if (node == null) {
            throw new IllegalArgumentException("call Adjust() with null node");
        }
        while (node != null) {
            if (getBF(node) == 2 && getBF(node.left) == 1) {
                LLRotate(node);
            } else if (getBF(node) == 2 && getBF(node.left) == -1) {
                LRRotate(node);
            } else if (getBF(node) == -2 && getBF(node.right) == -1) {
                RRRotate(node);
            } else if (getBF(node) == -2 && getBF(node.right) == 1) {
                RLRotate(node);
            } else {
                node.modified = true;
                if (node.height == getHeight(node)) {
                    return;
                }
                node = node.parent;
            }
        }
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("call containsKey with null key");
        }

        if (root == null) {
            root = new AVLNode(key, value);
            size ++;
            return;
        }

        AVLNode node = root;
        while (true) {
            if (node.key.compareTo(key) > 0) {
                if (node.left == null) {
                    node.left = new AVLNode(key, value);
                    node.left.parent = node;
                    size ++;
                    AVLAdjust(node);
                    return;
                }
                node = node.left;
            } else if (node.key.compareTo(key) < 0) {
                if (node.right == null) {
                    node.right = new AVLNode(key, value);
                    node.right.parent = node;
                    size ++;
                    AVLAdjust(node);
                    return;
                }
                node = node.right;
            } else {
                return;
            }
        }
    }

    private void inOrderTraversal(AVLNode root, Set<K> kSet) {
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

    private V removeNode(AVLNode node) {
        if (node == null) {
            return null;
        }
        V nodeVal = node.value;
        AVLNode leaf = null;
        while (true) {
            if (node.left == null && node.right == null) {
                size --;
                if (node.parent == null) {
                    root = null;
                } else if (node == node.parent.left) {
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
                if (node.parent != null) {
                    AVLAdjust(node.parent);
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
            throw new IllegalArgumentException("call containsKey with null key");
        }
        return removeNode(getNode(key));
    }

    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("call containsKey with null key");
        }
        AVLNode node = getNode(key);
        if (node == null || node.value != value) {
            return null;
        }
        return removeNode(node);
    }

    private class AVLNodeIter implements Iterator<K> {
        public AVLNodeIter() {
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
        return new AVLNodeIter();
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
