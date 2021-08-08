package dev.beni.utils.Tests.BinaryTree;

public class Tree {

    static class Node{
        int value;
        Node left, right;
        Node(int value){
            this.value = value;
            left = null;
            right = null;
        }
    }

    static class Savings{
        String key;
        String value;

        public Savings(String key, String value){
            this.key = key;
            this.value = value;
        }

        public void setValue(String newValue){
            value = newValue;
        }
    }

    public void insert(Node node, int value){
        if(value < node.value){

            if(node.left != null){
                insert(node.left, value);
            } else {
                System.out.println("Inserted " + value + " to left of " + node.value);
                node.left = new Node(value);
            }

        } else if (value > node.value){

            if(node.right != null){
                insert(node.right, value);
            } else {
                System.out.println("Inserted " + value + " to right of " + node.value);
                node.right = new Node(value);
            }
        }
    }

    public void traverseInOrder(Node node){
        if(node != null){
            traverseInOrder(node.left);
            System.out.println(node.value);
            traverseInOrder(node.right);
        }
    }

    public Node find(int number, Node node){
        while (node != null) {
            if (number > node.value)
                node = node.right;
            else if (number < node.value)
                node = node.left;
            else
                //number == node.value
                return node;
        }
        return null;
    }

}
