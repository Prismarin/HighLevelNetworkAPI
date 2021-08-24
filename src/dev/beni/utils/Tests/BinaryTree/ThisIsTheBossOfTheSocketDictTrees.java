package dev.beni.utils.Tests.BinaryTree;

public class ThisIsTheBossOfTheSocketDictTrees {

    static class Node{
        String value;
        String key;
        Node left, right;
        Node(String key, String value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
        public void setValue(String newValue){
            value = newValue;
        }
    }

    public void insert(Node node, String key, String value){
        if(key.compareToIgnoreCase(node.key) < 0){

            if(node.left != null){
                insert(node.left, key, value);
            } else {
                System.out.println("Inserted " + value + " to left of " + node.value);
                node.left = new Node(key, value);
            }

        } else if (key.compareToIgnoreCase(node.key) > 0){

            if(node.right != null){
                insert(node.right, key, value);
            } else {
                System.out.println("Inserted " + value + " to right of " + node.value);
                node.right = new Node(key, value);
            }
        }
    }

    public void traverseInOrder(Node node){
        if(node != null){
            traverseInOrder(node.left);
            System.out.print("Key: ");
            System.out.print(node.key);
            System.out.print(" Value: ");
            System.out.println(node.value);
            traverseInOrder(node.right);
        }
    }

    public Node find_Node_with_key(String keyToBeFound, Node node){
        while (node != null) {
            if(keyToBeFound.compareToIgnoreCase(node.key) > 0)
                node = node.right;
            else if(keyToBeFound.compareToIgnoreCase(node.key) < 0)
                node = node.left;
            else
                //number == node.value
                return node;
        }
        return null;
    }

    public Node find_Node_containing_Value(String valueToBeFound, Node node){
        while (node != null) {
            if(valueToBeFound.compareToIgnoreCase(node.value) > 0)
                node = node.right;
            else if(valueToBeFound.compareToIgnoreCase(node.value) < 0)
                node = node.left;
            else
                //number == node.value
                return node;
        }
        return null;
    }



}
