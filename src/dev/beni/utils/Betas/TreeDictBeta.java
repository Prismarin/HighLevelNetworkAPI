package dev.beni.utils.Betas;

import dev.beni.utils.SocketDict;

import java.util.ArrayList;

public class TreeDictBeta<K, V> {

    static class Node<K, V>{

        V value;
        K key;
        Node left, right;

        Node(K key, V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    public Node<K, V> root;

    // Constructor (creates root Node)
    public TreeDictBeta(K key, V value){
        root = new Node(key, value);
    }

    // Adds a new Node to the tree (adding two Nodes with the same key DOES NOT WORK!)
    public void add(K key, V value) {
        String keyAsString = key.toString();

        //inserts a new Node into the tree
        if(keyAsString.compareToIgnoreCase(root.key.toString()) < 0){

            if(root.left != null){
                addWithStartingNode(root.left, key, value);
            } else {
                //System.out.println("Inserted " + value + " to left of " + root.value);
                root.left = new TreeDictBeta.Node(key, value);
            }

        } else if (keyAsString.compareToIgnoreCase(root.key.toString()) > 0){

            if(root.right != null){
                addWithStartingNode(root.right, key, value);
            } else {
                //System.out.println("Inserted " + value + " to right of " + root.value);
                root.right = new TreeDictBeta.Node(key, value);
            }

        }

    }

    // Adds a new Node to the leftover tree with the startNode as its root
    public void addWithStartingNode(TreeDictBeta.Node startNode, K key, V value){
        String keyAsString = key.toString();

        if(keyAsString.compareToIgnoreCase(startNode.key.toString()) < 0){

            if(startNode.left != null){
                addWithStartingNode(startNode.left, key, value);
            } else {
                System.out.println("Inserted " + value + " to left of " + startNode.value);
                startNode.left = new TreeDictBeta.Node(key, value);
            }

        } else if (keyAsString.compareToIgnoreCase(startNode.key.toString()) > 0){

            if(startNode.right != null){
                addWithStartingNode(startNode.right, key, value);
            } else {
                System.out.println("Inserted " + value + " to right of " + startNode.value);
                startNode.right = new TreeDictBeta.Node(key, value);
            }

        }

    }

    // intern shit, you don't have to mess with this. It's only for debugging purposes
    public void traverseInOrder2(TreeDictBeta.Node node){

        //traverses through the tree and prints the keys in alphabetic order
        if(node != null){
            traverseInOrder2(node.left);
            System.out.print("(key:");
            System.out.print(node.key);
            System.out.print(", value:");
            System.out.print(node.value + ") ");
            traverseInOrder2(node.right);
        }

    }

    // traverses through the entire tree structure and returns an ArrayList with all the keys and values
    public ArrayList<TreeDictBeta.Node<K, V>> traverseInOrder(ArrayList<TreeDictBeta.Node<K, V>> list){

        //traverses through the tree and puts the Nodes into an ArrayList sorted in alphabetical order
        if(root != null){
            traverseInOrderWithStartingNode(root.left, list);
            list.add(root);
            traverseInOrderWithStartingNode(root.right, list);
        }

        return list;

    }

    // traverses through the leftover tree structure beginning at the specified startNode and returns an ArrayList with all the keys and values
    public ArrayList<TreeDictBeta.Node<K, V>> traverseInOrderWithStartingNode(TreeDictBeta.Node startingNode, ArrayList<TreeDictBeta.Node<K, V>> list){

        //traverses through the tree and puts the Nodes into an ArrayList sorted in alphabetical order
        if(startingNode != null){
            traverseInOrderWithStartingNode(startingNode.left, list);
            list.add(startingNode);
            traverseInOrderWithStartingNode(startingNode.right, list);
        }

        return list;

    }

    // returns the Node with the given keyToBeFound (there is only one, cuz two Nodes with the same key can't exist)
    public TreeDictBeta.Node findNodeWithKey(K keyToBeFound){

        TreeDictBeta.Node node = root;
        String keyToBeFoundAsString = keyToBeFound.toString();
        while (node != null) {

            if(keyToBeFoundAsString.compareToIgnoreCase(node.key.toString()) > 0)
                node = node.right;
            else if(keyToBeFoundAsString.compareToIgnoreCase(node.key.toString()) < 0)
                node = node.left;
            else
                //keyToBeFound == node.key
                return node;
        }

        return null;

    }

    public TreeDictBeta.Node find_Predecessor_Node_of_Node_with_key(K keyToBeFound){

        TreeDictBeta.Node node = root;
        TreeDictBeta.Node node_before = null;
        String keyToBeFoundAsString = keyToBeFound.toString();

        while (node != null) {

            if(keyToBeFoundAsString.compareToIgnoreCase(node.key.toString()) > 0) {
                node_before = node;
                node = node.right;
            } else if(keyToBeFoundAsString.compareToIgnoreCase(node.key.toString()) < 0) {
                node_before = node;
                node = node.left;
            } else
                //keyToBeFound == node.key
                return node_before;

        }
        return null;

    }

    // updates the value of a Node with the specified key as its key
    public void setValueByKey(K key, V new_value){

        findNodeWithKey(key).value = new_value;

    }

    // returns the value of the Node with the specified key
    public V get(K keyToBeFound){

        TreeDictBeta.Node node = root;
        String keyToBeFoundAsString = keyToBeFound.toString();
        while (node != null) {

            if(keyToBeFoundAsString.compareToIgnoreCase(node.key.toString()) > 0)
                node = node.right;
            else if(keyToBeFoundAsString.compareToIgnoreCase(node.key.toString()) < 0)
                node = node.left;
            else
                //keyToBeFound == node.key
                return (V) node.value;
        }

        return null;

    }

    // removes a Node with the specified key from the tree
    public void remove(K keyOfNodeToBeDeleted) {

        // creates all the necessary variables
        TreeDictBeta.Node node = root;
        node = findNodeWithKey(keyOfNodeToBeDeleted);
        TreeDictBeta.Node node_before = find_Predecessor_Node_of_Node_with_key(keyOfNodeToBeDeleted);
        int direction; // left = 0; right = 1


        // checks which is the successor node of the one to be deleted
        if(node_before.left != null) {

            if (keyOfNodeToBeDeleted.equals(node_before.left.key)) {
                direction = 0;
            } else {
                direction = 1;
            }

        } else if (node_before.right != null){
            direction = 1;
        } else{
            direction = 2;
        }

        // if else structure checks what type of Node the Node to be deleted is (e.g. leaf)

        // Node to be deleted is a leaf node
        if(node.left == null && node.right == null && direction != 2){

            //checks which direction the Node to be deleted to its predecessor Node is
            if(direction == 0){
                //"deletes" it when on left side of successor node (more like sets successors left value to null)
                node_before.left = null;
            } else {
                //"deletes" it when on right side of successor node (aka sets successors right value to null)
                node_before.right = null;
            }

        }

        //if the node to be deleted has successor nodes on its left and right
        else if(node.left != null && node.right != null && direction != 2){

            //checks which direction the Node to be deleted to its predecessor Node is
            if(direction == 0){

                //Puts all the Nodes on the right of the Node to be deleted into an ArrayList
                ArrayList<TreeDictBeta.Node<K, V>> nodesOnTheRight = new ArrayList<>();
                nodesOnTheRight = traverseInOrderWithStartingNode(node.right, nodesOnTheRight);

                //Moves the Node to the left of the Node to be deleted one level up and deletes the Node at the same time
                node_before.left = node.left;

                //Inserts all the Nodes from the ArrayList created earlier into the Tree again
                for(int i = 0; i < nodesOnTheRight.size(); i++) {
                    add(nodesOnTheRight.get(i).key, nodesOnTheRight.get(i).value);
                }

            } else {

                //Puts all the Nodes on the left of the Node to be deleted into an ArrayList
                ArrayList<TreeDictBeta.Node<K, V>> nodesOnTheLeft = new ArrayList<>();
                nodesOnTheLeft = traverseInOrderWithStartingNode(node.left, nodesOnTheLeft);

                //Moves the Node to the right of the Node to be deleted one level up and deletes the Node at the same time
                node_before.right = node.right;

                //Inserts all the Nodes from the ArrayList created earlier into the Tree again
                for(int i = 0; i < nodesOnTheLeft.size(); i++) {
                    add(nodesOnTheLeft.get(i).key, nodesOnTheLeft.get(i).value);
                }

            }

        }

        //if the node to be deleted has successor nodes only on its right
        else if(node.left == null && node.right != null  && direction != 2){

            //checks which direction the Node to be deleted to its predecessor Node is
            if(direction == 0){

                //Puts all the Nodes on the right of the Node to be deleted into an ArrayList
                ArrayList<TreeDictBeta.Node<K, V>> nodesOnTheRight = new ArrayList<>();
                nodesOnTheRight = traverseInOrderWithStartingNode(node.right, nodesOnTheRight);

                //Removes the Node by setting its predecessors left value to null
                node_before.left = null;

                //Inserts all the Nodes from the ArrayList created earlier into the Tree again
                for(int i = 0; i < nodesOnTheRight.size(); i++) {
                    add(nodesOnTheRight.get(i).key, nodesOnTheRight.get(i).value);
                }

            } else {

                //Moves the Node to the right of the Node to be deleted one level up and deletes the Node at the same time
                //Removes the Node by setting its predecessors left value to its own left values
                node_before.right = node.right;

            }

        }

        //if the node to be deleted has successor nodes only on its left
        else if(node.left != null && node.right == null && direction != 2){

            //checks which direction the Node to be deleted to its predecessor Node is
            if(direction == 0){

                //Moves the Node to the right of the Node to be deleted one level up and deletes the Node at the same time
                //Removes the Node by setting its predecessors left value to its left values
                node_before.left = node.left;

            } else {

                //Puts all the Nodes on the left of the Node to be deleted into an ArrayList
                ArrayList<TreeDictBeta.Node<K, V>> nodesOnTheLeft = new ArrayList<>();
                nodesOnTheLeft = traverseInOrderWithStartingNode(node.left, nodesOnTheLeft);

                //Removes the Node by setting its predecessors left value to null
                node_before.right = null;

                //Inserts all the Nodes from the ArrayList created earlier into the Tree again
                for(int i = 0; i < nodesOnTheLeft.size(); i++) {
                    add(nodesOnTheLeft.get(i).key, nodesOnTheLeft.get(i).value);
                }

            }

        }
        else {
            //just for catching stupid errors
        }

    }

    // intern shit, you don't have to mess with this. Simply don't use it
    private String traverseToRight(){

        //traverses through the tree, starting with the root Node and continues with the right Node of the root Node
        StringBuilder string = new StringBuilder();
        if(root != null){
            string.append("{");
            string.append(root.key);
            string.append("\\d");
            string.append(root.value);
            string.append("}");
            traverseInOrder3(root.right, string);
            traverseInOrder3(root.left, string);
        }
        return string.toString();

    }

    // intern shit, you don't have to mess with this. Simply don't use it
    private String traverseInOrder3(TreeDictBeta.Node node, StringBuilder string){

        //traverses through the tree
        if(node != null){
            string.append("{");
            string.append(node.key);
            string.append("\\d");
            string.append(node.value);
            string.append("}");
            traverseInOrder3(node.left, string);
            traverseInOrder3(node.right, string);
        }
        return string.toString();

    }

    // converts the tree to a String in order to send it over sockets efficiently
    @Override
    public String toString(){

        String string = traverseToRight();
        return string;

    }

    // intern shit, you don't have to mess with this. Simply don't use it
    private static int findEnd(String string){
        int end = 0;
        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);
            if(character == '}'){
                end = i + 1;
                break;
            }
        }
        return end;
    }

    //intern shit, you don't have to mess with this. Simply don't use it
    private static TreeDictBeta.Node convertKeyAndValueFromString(String string){
        int beginnigOfValue = 0;
        String key = null;
        String value = null;
        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);
            if(character == '\\'){
                if(string.charAt(i + 1) == 'd'){
                    key = string.substring(0, i);
                    beginnigOfValue = i + 2;
                    break;
                }
            }
        }
        for (int i = beginnigOfValue; i < string.length(); i++) {
            char character = string.charAt(i);
            if(character == '}'){
                value = string.substring(beginnigOfValue, i);
                break;
            }
        }
        TreeDictBeta.Node node = new TreeDictBeta.Node(key, value);
        return node;
    }

}
