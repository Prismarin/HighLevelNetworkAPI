package dev.kelvin.utils.bintree;

class EndNode<K, V> extends Node<K, V> {

    public EndNode() {
        super(null, null);
    }

    @Override
    public Node<K, V> add(TreeInfo<K> treeInfo , K key, V value) {
        return new BinTreeNode<>(treeInfo, key, value);
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public Node<K, V> remove(K key) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean setKeyByValue(K key, V newValue) {
        return false;
    }

}
