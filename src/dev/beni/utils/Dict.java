package dev.beni.utils;

import java.util.ArrayList;

public class Dict {

    private Node root;

    public Dict(Node root){
        this.root = root;
    }

    public void insert(Node newNode){
        if(root != null){
            root.insert(newNode);
        }
    }

    public void output(){
        if(root != null){
            root.output();
        }
    }

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

    public ArrayList<Node> traversePreorder(){
        ArrayList<Node> list = new ArrayList<>();
        if(root != null){
            list = root.traversePreOrder(list);
        }
        return list;
    }

    public ArrayList<Node> traverseInorder(){
        ArrayList<Node> list = new ArrayList<>();
        if(root != null){
            list = root.traverseInOrder(list);
        }
        return list;
    }

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

    private ArrayList<Node> traversePreorderFromNode(Node node){
        ArrayList<Node> list = new ArrayList<>();
        if(node != null){
            list = node.traversePreOrder(list);
        }
        return list;
    }

    public void remove(String key){
        Node node = find(key);

        if(node != null && node.getLeft() != null){
            ArrayList<Node> list = traversePreorderFromNode(node.getLeft());
            if(root.getKey().equals(key)){
                root = root.getRight();
            } else {
                root.remove(key);
            }

            for(int i = 0; i < list.size(); i++){
                Node node2 = list.get(i);
                root.insert(new Node(node2.getKey(), node2.getValue()));
            }
        } else if(node != null && node.getLeft() == null){
            if(root.getKey().equals(key)){
                root = root.getRight();
            } else {
                root.remove(key);
            }
        }

    }

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

    public static Dict fromString(String string){

        Dict convertedFromString;
        int end = findEnd(string);
        int beginning = 1;
        Node nextNode = null;

        Node firstNode = convertKeyAndValueFromString(string.substring(beginning, end));
        convertedFromString = new Dict(firstNode);

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

}
