package dev.kelvin.utils.bintree;

abstract class Node<K, V> {

    public K key;
    public V value;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public abstract Node<K, V> add(TreeInfo<K> treeInfo, K key, V value);

    public abstract V get(K key);

    public abstract Node<K, V> remove(K key);

    public abstract int size();

    public abstract boolean setKeyByValue(K key, V newValue);

}
