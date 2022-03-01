package dev.beni.utils;

import java.util.ArrayList;

public class SocketDictStringsOnly {

    private SocketDict dict;

    /**The SocketDict implementation only allowing Strings for both Key and Value**/
    public SocketDictStringsOnly(String key, String value){
        dict = new SocketDict(new Node<String, String>(key, value));
    }

    /**adds a new Node to the tree with the given Key and Value**/
    public void add(String key, String value){
        dict.insert(new Node<String, String>(key, value));
    }

    /**prints the tree out to the console inorder**/
    public void printout(){
        dict.output();
    }

    /**traverses through the tree and stores all the Nodes in an ArrayList**/
    public ArrayList<Node> traverseInorder(){
        return dict.traverseInorder();
    }

    /**traverses through the tree preorder and stores all the values in an ArrayList**/
    public ArrayList<Node> traversePreorder(){
        return dict.traversePreorder();
    }

    /**finds the Node with the given Key**/
    public Node find(String key){
        return dict.find(key);
    }

    /**outputs the Value of the Node with given Key**/
    public String getValue(String key){
        return dict.find(key).getValue();
    }

    /**finds the predecessor to the Node with the given Key**/
    public Node findPredecessor(String key){
        return dict.predecessorTo(key);
    }

    /**sets the Value of the Node with the given Key to the given Value**/
    public void setValue(String key, String newValue){
        dict.setValue(key, newValue);
    }

    /**removes the Node with the given Key from the tree**/
    public void delete(String key){
        dict.delete(key);
    }

    /**converts the entire structure to a String**/
    @Override
    public String toString(){
        return dict.toString();
    }

    /**converts a String made with the toString method back to a SocketDictStringsOnly**/
    public static SocketDictStringsOnly fromString(String string){
        SocketDictStringsOnly dict1 = fromString(string);
        ArrayList<Node> list = dict1.traversePreorder();
        SocketDictStringsOnly socketDictBeta = new SocketDictStringsOnly(list.get(0).getKey(), list.get(0).getValue());
        for(int i = 1; i < list.size(); i++){
            socketDictBeta.add(list.get(i).getKey(), list.get(i).getValue());
        }
        return socketDictBeta;
    }

}
