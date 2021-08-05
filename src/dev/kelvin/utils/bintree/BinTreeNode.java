package dev.kelvin.utils.bintree;

class BinTreeNode<K, V> extends Node<K, V> {

    public TreeInfo<K> treeInfo;
    public Node<K, V> left, right;

    public BinTreeNode(TreeInfo<K> treeInfo, K key, V value) {
        super(key, value);
        this.treeInfo = treeInfo;
        left = new EndNode<>();
        right = new EndNode<>();
    }

    @Override
    public Node<K, V> add(TreeInfo<K> treeInfo, K key, V value) {
        if (key.equals(this.key))
            System.err.println("Key \"" + key + "\" is already used! Use method \"set\" to edit the value!");
        else if (treeInfo.isASmaller(key, this.key))
            left = left.add(treeInfo, key, value);
        else
            right = right.add(treeInfo, key, value);
        return this;
    }

    @Override
    public V get(K key) {
        if (key.equals(this.key))
            return value;
        else if (treeInfo.isASmaller(key, this.key))
            return left.get(key);
        return right.get(key);
    }

    @Override
    public Node<K, V> remove(K key) {
        return this;
    }

    @Override
    public int size() {
        int count = 1;
        return count + left.size() + right.size();
    }

    @Override
    public boolean setKeyByValue(K key, V newValue) {
        if (key.equals(this.key)) {
            this.value = newValue;
            return true;
        } else if (treeInfo.isASmaller(key, this.key))
            return left.setKeyByValue(key, newValue);
        return right.setKeyByValue(key, newValue);
    }

}
