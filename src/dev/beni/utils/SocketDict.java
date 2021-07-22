package dev.beni.utils;

import java.util.*;

public class SocketDict {

    private List<String> keys = new ArrayList<String>();
    private List<String> values = new ArrayList<String>();

    public SocketDict() {

    }

    public void printout() {
        for (int i = 0; i < keys.size(); i++) {
            System.out.print("(key:");
            System.out.print(keys.get(i));
            System.out.print(", value:");
            System.out.print(values.get(i) + ") ");
        }
    }

    public void add(String key, String value) {
        //adds new values to the Lists
        if(keys.contains(key)){
            //does nothing lol
        } else {
            keys.add(key);
            values.add(value);
        }
    }

    public void remove(String keyname){

    }

    public String toString() {
        //converts everything to a String in order to send it over sockets
        String stringified;
        stringified = "Hello nothing to see here rn, sry";
        return stringified;
    }

    public List fromString() {
        //converts the created String (in toString()) back to the two Lists
        return keys;
    }

}
