package dev.beni.utils;

import java.util.ArrayList;

public class Node<K, V>{

    private V value;
    private K key;
    public Node left, right;

    /**creates a new Node*/
    public Node(K key, V value){
        this.key = key;
        this.value = value;
        right = null;
        left = null;
    }

    //getters
    /**outputs the Key as a String**/
    public String getKey(){
        return key.toString();
    }

    /**outputs the Value as a String**/
    public String getValue(){
        return value.toString();
    }

    /**outputs the data type of the Key as a String (method not in use, but it works if you need it)**/
    public String getValueType(){
        return value.getClass().getName();
    }

    /**outputs the data type of the Value as a String (method not in use, but it works if you need it)**/
    public String getKeyType(){
        return key.getClass().getName();
    }

    /**outputs the left successor**/
    public Node getLeft(){
        return left;
    }

    /**outputs the right successor**/
    public Node getRight(){
        return right;
    }

    /**outputs the minimum (it is only used internally) P.S. you can declare it as public if you need to use it**/
    private Node getMinimum(){
        if(left == null){
            return this;
        }
        return left.getMinimum();
    }

    //setters
    /**sets the Key to the given string (not used)*/
    public void setKey(K key){
        this.key = key;
    }

    /**sets the Value to the given string**/
    public void setValue(V value){
        this.value = value;
    }

    /**sets the left successor to the given Node**/
    public void setLeft(Node left){
        this.left = left;
    }

    /**sets the right successor to the given Node**/
    public void setRight(Node right){
        this.right = right;
    }

    //functions
    /**inserts a new Node to the tree**/
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

    /**converts the stored values to a string and outputs it**/
    @Override
    public String toString(){
        return getKey() + ", " + getValue();
    }

    /**prints the tree inorder to the console**/
    public void output(){
        if(left != null){
            left.output();
        }
        System.out.print(toString() + "; ");
        if(right != null){
            right.output();
        }
    }

    /**finds the Node with the given Key**/
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

    /**traverses preorder through the tree and adds all the Nodes to an ArrayList**/
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

    /**traverses inorder through the tree and adds all the Nodes to an ArrayList**/
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

    /**outputs the predecessor of the Node with the given Key**/
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

    /**removes the Node with the given Key**/
    public Node delete(String key){

        if(getKey().compareToIgnoreCase(key) > 0){
            left = left.delete(key);
            return this;
        } else if(getKey().compareToIgnoreCase(key) < 0){
            right = right.delete(key);
            return this;
        } else {
            if(left == null && right == null){
                return null;
            } else if(left == null){
                return right;
            } else if(right == null){
                return left;
            } else {
                Node newThis = right.getMinimum();
                right = right.delete(newThis.getKey());
                newThis.setRight(right);
                newThis.setLeft(left);
                return newThis;
            }

        }
    }

}
