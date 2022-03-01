package dev.beni.utils;

import java.util.ArrayList;

public class SocketDict {

    private Node root;

    /**<h3>Creates a binary tree structure, which is good for storing Key and Value together and being able to access all the information fast and easy.
     * The similarity with a dictionary and the focus on being easy and fast inorder to send the entire structure over websockets lead to the name SocketDict.
     * If you'd like to learn more about binary trees just google and you will find soooo many good articles about them, trust me, by the way you can't go wrong with learning about them.</h3>
     *
     * In order to create a new SocketDict you have to create a new Node ( e.g. new Node< datatypeKey, datatyeValue >(key, value) or new Node(key, value) ) and pass it to the constructor as a parameter**/
    public SocketDict(Node root){
        this.root = root;
    }

    /**inserts the given Node into the tree**/
    public void insert(Node newNode){
        if(root != null){
            root.insert(newNode);
        }
    }

    /**prints the tree to the console inorder**/
    public void output(){
        if(root != null){
            root.output();
        }
    }

    /**finds the Node with the given Key**/
    public Node find(String keyToBeFound){
        if(root != null){
            if(root.getKey().equals(keyToBeFound)){
                return root;
            } else {
                return root.find(keyToBeFound);
            }
        }
        return null;
    }

    /**traverses through the tree preorder and stores all the values in an ArrayList**/
    public ArrayList<Node> traversePreorder(){
        ArrayList<Node> list = new ArrayList<>();
        if(root != null){
            list = root.traversePreOrder(list);
        }
        return list;
    }

    /**traverses through the tree inorder and stores all the values in an ArrayList**/
    public ArrayList<Node> traverseInorder(){
        ArrayList<Node> list = new ArrayList<>();
        if(root != null){
            list = root.traverseInOrder(list);
        }
        return list;
    }

    /**finds the predecessor to the Node with the given Key**/
    public Node predecessorTo(String key){
        if(root != null){
            if(root.getKey().equals(key)){
                return root;
            } else {
                return root.predecessorTo(key);
            }
        }
        return null;
    }

    /**traverses through the tree inorder, starting form the given Node, and stores all the values in an ArrayList**/
    private ArrayList<Node> traversePreorderFromNode(Node node){
        ArrayList<Node> list = new ArrayList<>();
        if(node != null){
            list = node.traversePreOrder(list);
        }
        return list;
    }

    /**removes the Node with the given Key from the tree**/
    public void delete(String key){
        if(root != null){
            root = root.delete(key);
        }
    }

    /**converts the entire structure to a string**/
    @Override
    public String toString(){
        if(root != null) {
            ArrayList<Node> list = traversePreorder();
            StringBuilder string = new StringBuilder();

            for(int i = 0; i < list.size(); i++){
                string.append("{");
                string.append(list.get(i).getKey());
                string.append("\\d");
                string.append(list.get(i).getValue());
                string.append("}");
            }
            return string.toString();

        } else {
            return null;
        }
    }

    /**intern stuff for the fromSting method**/
    private static int findEnd(String string){
        int end = 0;
        for(int i = 0; i < string.length(); i++){
            char character = string.charAt(i);
            if(character == '}'){
                end = i + 1;
                break;
            }
        }
        return end;
    }

    /**intern stuff for the fromSting method**/
    private static Node convertKeyAndValueFromString(String string){
        int beginningOfValue = 0;
        String key = null;
        String value = null;
        for(int i = 0; i < string.length(); i++){
            char character = string.charAt(i);
            if(character == '\\'){
                if(string.charAt(i + 1) == 'd'){
                    key = string.substring(0, i);
                    beginningOfValue = i + 2;
                    break;
                }
            }
        }
        for(int i = beginningOfValue; i < string.length(); i++){
            char character = string.charAt(i);
            if(character == '}'){
                value = string.substring(beginningOfValue, i);
                break;
            }
        }
        Node node = new Node(key, value);
        return node;
    }

    /**converts a string made with the toString method back to a usable SocketDict**/
    public static SocketDict fromString(String string){

        SocketDict convertedFromString;
        int end = findEnd(string);
        int beginning = 1;
        Node nextNode = null;

        Node firstNode = convertKeyAndValueFromString(string.substring(beginning, end));
        convertedFromString = new SocketDict(firstNode);

        while(end <= string.length()){
            beginning = end + 1;
            if(beginning < string.length()){
                string = string.substring(beginning);
            } else {
                break;
            }
            end = findEnd(string);
            nextNode = convertKeyAndValueFromString(string);
            convertedFromString.insert(nextNode);
        }

        return convertedFromString;
    }

    /**sets the value of a Node with the given Key to the given Value**/
    public void setValue(String key, String newValue){
        find(key).setValue(newValue);
    }

}
