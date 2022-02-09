package dev.beni.utils;

import java.util.ArrayList;

public class Node<K, V>{

    private V value;
    private K key;
    public Node left, right;

    public Node(K key, V value){
        this.key = key;
        this.value = value;
        right = null;
        left = null;
    }

    //getters
    public String getKey(){
        return key.toString();
    }

    public String getValue(){
        return value.toString();
    }

    public String getValueType(){
        return value.getClass().getName();
    }

    public String getKeyType(){
        return key.getClass().getName();
    }

    public Node getLeft(){
        return left;
    }

    public Node getRight(){
        return right;
    }

    //setters
    public void setKey(K key){
        this.value = value;
    }

    public void setValue(V value){
        this.value = value;
    }

    public void setLeft(Node left){
        this.left = left;
    }

    public void setRight(Node right){
        this.right = right;
    }

    //functions
    public void insert(Node newNode){
        if(newNode.getKey().equals(getKey())){

        } else if(newNode.getKey().compareToIgnoreCase(getKey()) < 0){

            if(left == null){
                left = newNode;
            } else {
                left.insert(newNode);
            }

        } else if(newNode.getKey().compareToIgnoreCase(getKey()) > 0){

            if(right == null){
                right = newNode;
            } else {
                right.insert(newNode);
            }

        } else {

        }
    }

    @Override
    public String toString(){
        return getKey() + ", " + getValue();
    }

    public void output(){
        if(left != null){
            left.output();
        }
        System.out.print(toString() + "; ");
        if(right != null){
            right.output();
        }
    }

    public Node find(String keyToBeFound){
        if(getKey().equals(keyToBeFound)){
            return this;
        } else if(getKey().compareToIgnoreCase(keyToBeFound) < 0 && right != null){
            return right.find(keyToBeFound);
        } else if(getKey().compareToIgnoreCase(keyToBeFound) > 0 && left != null){
            return left.find(keyToBeFound);
        } else {
            return null;
        }
    }

    public ArrayList<Node> traversePreOrder(ArrayList<Node> list){
        list.add(this);
        if(left != null){
            list = left.traversePreOrder(list);
        }
        if(right != null){
            list = right.traversePreOrder(list);
        }

        return list;
    }

    public ArrayList<Node> traverseInOrder(ArrayList<Node> list){
        if(left != null){
            list = left.traverseInOrder(list);
        }
        list.add(this);
        if(right != null){
            list = right.traverseInOrder(list);
        }

        return list;
    }

    public Node predecessorTo(String key){
        if(getLeft().getKey().equals(key) || getRight().getKey().equals(key)){
            return this;
        } else if(getKey().compareToIgnoreCase(key) < 0){
            if(left != null){
                return left.predecessorTo(key);
            }
        } else if(getKey().compareToIgnoreCase(key) > 0){
            if(right != null){
                return right.predecessorTo(key);
            }
        }
        return null;
    }

    public Node remove(String key){

        if(getKey().equals(key)){
            return getRight();
        } else {
            if(getKey().compareToIgnoreCase(key) < 0 && right != null){
                right = right.remove(key);
            } else if(getKey().compareToIgnoreCase(key) > 0 && left != null){
                left = left.remove(key);
            }
            return this;
        }

    }

}
