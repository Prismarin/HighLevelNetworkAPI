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
        System.out.println("");
    }

    public void setValue(String key, String new_value){
        for (int i = 0; i < keys.size(); i++) {
            if(keys.get(i) == key){
                values.set(i, new_value);
            }
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
        //removes values from the Lists
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i) == keyname){
                keys.remove(i);
                values.remove(i);
            }
        }
    }

    @Override
    public String toString() {
        //converts everything to a String in order to send it over sockets
        String stringified = "{";
        for (int i = 0; i < keys.size(); i++) {
            int len = keys.size() - 1;
            if(i == len){
                stringified += keys.get(i);
            } else {
                stringified += keys.get(i);
                stringified += "\\d";
            }
        }
        stringified += "}{";
        for (int i = 0; i < keys.size(); i++) {
            int len = keys.size() - 1;
            if(i == len){
                stringified += values.get(i);
            } else {
                stringified += values.get(i);
                stringified += "\\d";
            }
        }
        stringified += "}";
        return stringified;
    }

    public List[] fromString(String string) {
        //converts the created String (in toString()) back to the two Lists

        List[] listarray = new List[2];
        //finds where "keys-part" ends
        int keys_end = 0;
        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);
            if(character == '}'){
                keys_end = i + 1;
                break;
            }
        }

        //gets all the keys and puts them into the keys_from_string List
        List<String> keys_from_string = new ArrayList<String>();
        int end_of_last_key = 1;

        for (int i = 1; i < keys_end; i++) {
            char character = string.charAt(i);
            if(character == '\\'){
                if(string.charAt(i + 1) == 'd'){
                    keys_from_string.add(string.substring(end_of_last_key, i));
                    //System.out.println(string.substring(end_of_last_key, i));
                    end_of_last_key = i + 2;
                }
            } else if(character == '}'){
                keys_from_string.add(string.substring(end_of_last_key, i));
                //System.out.println(string.substring(end_of_last_key, i));
            }
        }


        //gets all the values and puts them into the values_from_string List
        List<String> values_from_string = new ArrayList<String>();
        int values_beginning = keys_end + 1;
        int end_of_last_val = values_beginning;

        for (int i = values_beginning; i < string.length(); i++) {
            char character = string.charAt(i);
            if(character == '\\'){
                if(string.charAt(i + 1) == 'd'){
                    values_from_string.add(string.substring(end_of_last_val, i));
                    //System.out.println(string.substring(end_of_last_val, i));
                    end_of_last_val = i + 2;
                }
            } else if(character == '}'){
                values_from_string.add(string.substring(end_of_last_val, i));
                //System.out.println(string.substring(end_of_last_val, i));
            }
        }

        listarray[0] = keys_from_string;
        listarray[1] = values_from_string;
        return listarray;
    }

    public List ValuesFromString(String string){
        //converts the created String (in toString()) back to the values List only!!

        //finds where "keys-part" ends
        int keys_end = 0;
        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);
            if(character == '}'){
                keys_end = i + 1;
                break;
            }
        }

        //gets all the values and puts them into the values_from_string List
        List<String> values_from_string = new ArrayList<String>();
        int values_beginning = keys_end + 1;
        int end_of_last_val = values_beginning;

        for (int i = values_beginning; i < string.length(); i++) {
            char character = string.charAt(i);
            if(character == '\\'){
                if(string.charAt(i + 1) == 'd'){
                    values_from_string.add(string.substring(end_of_last_val, i));
                    //System.out.println(string.substring(end_of_last_val, i));
                    end_of_last_val = i + 2;
                }
            } else if(character == '}'){
                values_from_string.add(string.substring(end_of_last_val, i));
                //System.out.println(string.substring(end_of_last_val, i));
            }
        }

        return values_from_string;
    }

    public List KeysfromString(String string) {
        //converts the created String (in toString()) back to the keys List only!!

        //finds where "keys-part" ends
        int keys_end = 0;
        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);
            if(character == '}'){
                keys_end = i + 1;
                break;
            }
        }

        //gets all the keys and puts them into the keys_from_string List
        List<String> keys_from_string = new ArrayList<String>();
        int end_of_last_key = 1;

        for (int i = 1; i < keys_end; i++) {
            char character = string.charAt(i);
            if(character == '\\'){
                if(string.charAt(i + 1) == 'd'){
                    keys_from_string.add(string.substring(end_of_last_key, i));
                    //System.out.println(string.substring(end_of_last_key, i));
                    end_of_last_key = i + 2;
                }
            } else if(character == '}'){
                keys_from_string.add(string.substring(end_of_last_key, i));
                //System.out.println(string.substring(end_of_last_key, i));
            }
        }

        return keys_from_string;
    }

}
