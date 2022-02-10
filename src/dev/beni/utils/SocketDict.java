package dev.beni.utils;

import java.util.ArrayList;

public class SocketDict {

    private static Dict dict;

    public SocketDict(String key, String value){
        dict = new Dict(new Node<String, String>(key, value));
    }

    public void add(String key, String value){
        dict.insert(new Node<String, String>(key, value));
    }

    public void printout(){
        dict.output();
    }

    public ArrayList<Node> traverseInorder(){
        return dict.traverseInorder();
    }

    public Node find(String key){
        return dict.find(key);
    }

    public String getValue(String key){
        return dict.find(key).getValue();
    }

    public Node findPredecessor(String key){
        return dict.predecessorTo(key);
    }

    public void setValue(String key, String newValue){
        dict.setValue(key, newValue);
    }

    public void remove(String key){
        dict.remove(key);
    }

    @Override
    public String toString(){
        return dict.toString();
    }

    public static SocketDict fromString(String string){
        Dict dict1 = dict.fromString(string);
        ArrayList<Node> list = dict1.traversePreorder();
        SocketDict socketDictBeta = new SocketDict(list.get(0).getKey(), list.get(0).getValue());
        for(int i = 1; i < list.size(); i++){
            socketDictBeta.add(list.get(i).getKey(), list.get(i).getValue());
        }
        return socketDictBeta;
    }

}
