package dev.beni.utils;

import java.util.ArrayList;

public class SocketDict {
    // A "dictionary" made for being easy and fast to send over sockets. Get the superglue, dictionary and socket
    // aaaaaaaaand .... kawhooooooooom: "SocketDict" the perfect name for a binarytreestructure, where every Node contains
    // a key and value for storing all the Strings you want to later send over sockets (actually it should be "what we want", cuz we are most likely be
    // the only ones using it, but you're free to do so as well)

    //creates a root Node
    public Node root;

    public SocketDict(String key, String value){
        //gives the root Node key and value
        root = new Node(key, value);
    }

    //is the Node class (yes, there is nothing else to say about it, or wait ...) every Node has a key and a value
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

        //The name says, what it does
        public void setValue(String newValue){
            value = newValue;
        }
    }

    //adds in a new Node, if and only if the key does not already exist in the tree!!!!
    public void add(String key, String value){

        //inserts a new Node into the tree
        if(key.compareToIgnoreCase(root.key) < 0){

            if(root.left != null){
                insert2(root.left, key, value);
            } else {
                //System.out.println("Inserted " + value + " to left of " + root.value);
                root.left = new Node(key, value);
            }

        } else if (key.compareToIgnoreCase(root.key) > 0){

            if(root.right != null){
                insert2(root.right, key, value);
            } else {
                //System.out.println("Inserted " + value + " to right of " + root.value);
                root.right = new Node(key, value);
            }

        }

    }

    //intern shit, you don't have to mess with this. Simply use add()
    private void insert2(Node node, String key, String value){

        //inserts a new Node into the tree
        if(key.compareToIgnoreCase(node.key) < 0){

            if(node.left != null){
                insert2(node.left, key, value);
            } else {
                System.out.println("Inserted " + value + " to left of " + node.value);
                node.left = new Node(key, value);
            }

        } else if (key.compareToIgnoreCase(node.key) > 0){

            if(node.right != null){
                insert2(node.right, key, value);
            } else {
                System.out.println("Inserted " + value + " to right of " + node.value);
                node.right = new Node(key, value);
            }

        }

    }

    //prints the entire structure to the console
    public void printout(){

        //traverses through the tree and prints the keys in alphabetic order
        if(root != null){
            traverseInOrder2(root.left);
            System.out.print("(key:");
            System.out.print(root.key);
            System.out.print(", value:");
            System.out.print(root.value + ") ");
            traverseInOrder2(root.right);
        }

    }

    //intern shit, you don't have to mess with this. Simply use printout()
    private void traverseInOrder2(Node node){

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

    //traverses through the entire structure and returns an ArrayList with all the keys and values
    public ArrayList<Node> traverseInOrderWithPuttingEverythingInAnArraylist(ArrayList<Node> list){

        //traverses through the tree and puts the Nodes into an ArrayList sorted in alphabetical order
        if(root != null){
            traverseInOrderWithPuttingEverythingInAnArraylist2(root.left, list);
            list.add(root);
            traverseInOrderWithPuttingEverythingInAnArraylist2(root.right, list);
        }

        return list;

    }

    //intern shit, you don't have to mess with this. Simply use traverseInOrderWithPuttingEverythingInAnArraylist()
    private ArrayList<Node> traverseInOrderWithPuttingEverythingInAnArraylist2(Node node, ArrayList<Node> list){

        //traverses through the tree and puts the Nodes into an ArrayList sorted in alphabetical order
        if(node != null){
            traverseInOrderWithPuttingEverythingInAnArraylist2(node.left, list);
            list.add(node);
            traverseInOrderWithPuttingEverythingInAnArraylist2(node.right, list);
        }

        return list;

    }

    public Node find_Node_with_Key(String keyToBeFound){

        //finds the Node with the given key
        Node node = root;
        while (node != null) {

            if(keyToBeFound.compareToIgnoreCase(node.key) > 0)
                node = node.right;
            else if(keyToBeFound.compareToIgnoreCase(node.key) < 0)
                node = node.left;
            else
                //keyToBeFound == node.key
                return node;
        }

        return null;

    }

    public String get(String keyToBeFound){

        //finds the Node with the given key
        Node node = root;
        while (node != null) {

            if(keyToBeFound.compareToIgnoreCase(node.key) > 0)
                node = node.right;
            else if(keyToBeFound.compareToIgnoreCase(node.key) < 0)
                node = node.left;
            else
                //keyToBeFound == node.key
                return node.value;
        }

        return null;

    }

    public Node find_Predecessor_Node_of_Node_with_key(String keyToBeFound){

        //finds the predecessor of a Node with specific key
        Node node = root;
        Node node_before = null;
        while (node != null) {

            if(keyToBeFound.compareToIgnoreCase(node.key) > 0) {
                node_before = node;
                node = node.right;
            } else if(keyToBeFound.compareToIgnoreCase(node.key) < 0) {
                node_before = node;
                node = node.left;
            } else
                //keyToBeFound == node.key
                return node_before;

        }
        return null;

    }

    public Node find_Node_containing_Value(String valueToBeFound){

        //finds a Node, that contains a specific value
        Node node = root;
        while (node != null) {

            if(valueToBeFound.compareToIgnoreCase(node.value) > 0)
                node = node.right;
            else if(valueToBeFound.compareToIgnoreCase(node.value) < 0)
                node = node.left;
            else
                //valueToBeFound == node.value
                return node;

        }
        return null;

    }

    public void setValueByKey(String key, String new_value){

        //read the method name not my comment!!
        find_Node_with_Key(key).value = new_value;

    }

    public void remove(String keyOfNodeToBeDeleted) {

        //removes a Node with the specified key from the tree

        //creates all the necessary variables
        Node node = root;
        node = find_Node_with_Key(keyOfNodeToBeDeleted);
        Node node_before = find_Predecessor_Node_of_Node_with_key(keyOfNodeToBeDeleted);
        int direction; //left = 0; right = 1


        //checks which successor node is the one to be deleted
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

        //Node to be deleted is a leaf node
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
                ArrayList<Node> nodesOnTheRight = new ArrayList<>();
                nodesOnTheRight = traverseInOrderWithPuttingEverythingInAnArraylist2(node.right, nodesOnTheRight);

                //Moves the Node to the left of the Node to be deleted one level up and deletes the Node at the same time
                node_before.left = node.left;

                //Inserts all the Nodes from the ArrayList created earlier into the Tree again
                for(int i = 0; i < nodesOnTheRight.size(); i++) {
                    add(nodesOnTheRight.get(i).key, nodesOnTheRight.get(i).value);
                }

            } else {

                //Puts all the Nodes on the left of the Node to be deleted into an ArrayList
                ArrayList<Node> nodesOnTheLeft = new ArrayList<>();
                nodesOnTheLeft = traverseInOrderWithPuttingEverythingInAnArraylist2(node.left, nodesOnTheLeft);

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
                ArrayList<Node> nodesOnTheRight = new ArrayList<>();
                nodesOnTheRight = traverseInOrderWithPuttingEverythingInAnArraylist2(node.right, nodesOnTheRight);

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
                ArrayList<Node> nodesOnTheLeft = new ArrayList<>();
                nodesOnTheLeft = traverseInOrderWithPuttingEverythingInAnArraylist2(node.left, nodesOnTheLeft);

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

    //intern shit, you don't have to mess with this. Simply don't use it
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

    //intern shit, you don't have to mess with this. Simply don't use it
    private String traverseInOrder3(Node node, StringBuilder string){

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

    @Override
    public String toString(){

        //converts the tree to a String in order to send it over sockets efficiently
        String string = traverseToRight();
        return string;

    }

    //intern shit, you don't have to mess with this. Simply don't use it
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
    private static Node convertKeyAndValueFromString(String string){
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
        Node node = new Node(key, value);
        return node;
    }

    public static SocketDict fromString(String string){

        //converts a String created with the toString() method back to a SocketDict and returns it
        SocketDict converted_from_String;
        int end = findEnd(string);
        int beginning = 1;
        Node nextnode = null;


        Node firstnode = convertKeyAndValueFromString(string.substring(beginning, end));

        converted_from_String = new SocketDict(firstnode.key, firstnode.value);

        while(end <= string.length()) {

            beginning = end + 1;
            if(beginning < string.length()){
                string = string.substring(beginning);
            } else {
                break;
            }
            end = findEnd(string);
            nextnode = convertKeyAndValueFromString(string);
            converted_from_String.add(nextnode.key, nextnode.value);

        }

        return converted_from_String;
    }

    public boolean canBeConvertedToDouble(String key_for_value_to_be_checked){

        //checks if the value of a key can be converted to a Double
        if (find_Node_with_Key(key_for_value_to_be_checked).key.equals(key_for_value_to_be_checked)) {
            try {
                Double.parseDouble(find_Node_with_Key(key_for_value_to_be_checked).value);
                return true;
            } catch (Exception ignored) {
                return false;
            }
        }
        return false;
    }

    public boolean canBeConvertedToInteger(String key_for_value_to_be_checked){

        //checks if the value of a key can be converted to an Integer
        if (find_Node_with_Key(key_for_value_to_be_checked).key.equals(key_for_value_to_be_checked)) {
            try {
                Integer.parseInt(find_Node_with_Key(key_for_value_to_be_checked).value);
                return true;
            } catch (Exception ignored) {
                return false;
            }
        }
        return false;
    }

    public Double convertToDouble(String key_for_value_to_be_checked){

        //converts the value of a key to Double
        double integer = 0.0;
        if (find_Node_with_Key(key_for_value_to_be_checked).key.equals(key_for_value_to_be_checked)) {
            integer = Double.parseDouble(find_Node_with_Key(key_for_value_to_be_checked).value);
        }

        return integer;
    }

    public Integer convertToInteger(String key_for_value_to_be_checked){

        //converts the value of a key to Integer
        int integer = 0;
        if (find_Node_with_Key(key_for_value_to_be_checked).key.equals(key_for_value_to_be_checked)) {
            integer = Integer.parseInt(find_Node_with_Key(key_for_value_to_be_checked).value);
        }

        return integer;
    }
}
