package dev.beni.utils.Tests.BinaryTree;

public class ThisIsTheBossOfTheSocketDictTree {

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
        if(key.compareToIgnoreCase(node.key) == 1){

            if(node.left != null){
                insert(node.left, key, value);
            } else {
                System.out.println("Inserted " + value + " to left of " + node.value);
                node.left = new Node(key, value);
            }

        } else if (key.compareToIgnoreCase(node.key) == 1){

            if(node.right != null){
                insert(node.right, key, value);
            } else {
                System.out.println("Inserted " + value + " to right of " + node.value);
                node.right = new Node(key, value);
            }
        }
    }

}
