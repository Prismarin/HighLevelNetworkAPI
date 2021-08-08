package dev.kelvin.utils.bintree;

//import com.sun.istack.internal.Nullable;

public class BinaryTree<K, V> {

    private Node<K, V> start;
    private final TreeInfo<K> treeInfo;

    public BinaryTree(TreeInfo<K> treeInfo) {
        start = new EndNode<>();
        this.treeInfo = treeInfo;
    }

    public void add(K key, V value) {
        start = start.add(treeInfo, key, value);
    }

    public boolean set(K key, V newValue) {
        return start.setKeyByValue(key, newValue);
    }

    //@Nullable
    public V get(K key) {
        return start.get(key);
    }

    public int size() {
        return start.size();
    }

    public void remove(K key) {
        start = start.remove(key);
    }

    //contains key
    //contains value

}
