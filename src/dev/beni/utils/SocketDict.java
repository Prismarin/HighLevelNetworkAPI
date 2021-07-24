package dev.beni.utils;

import java.util.*;

public class SocketDict {

    private final ArrayList<String> keys;
    private final ArrayList<String> values;

    public SocketDict() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
    }

    public void printout() {
        for (int i = 0; i < keys.size(); i++) {
            System.out.print("(key:");
            System.out.print(keys.get(i));
            System.out.print(", value:");
            System.out.print(values.get(i) + ") ");
        }
        System.out.println();
    }

    public void setValueByKey(String key, String new_value){
        for (int i = 0; i < keys.size(); i++) {
            if(keys.get(i).equals(key)){
                values.set(i, new_value);
                break;
            }
        }
    }

    public void setValueByPos(int pos, String new_value){
        values.set(pos, new_value);
    }

    public void add(String key, String value) {
        //adds new values to the Lists
        if(!keys.contains(key)) {
            keys.add(key);
            values.add(value);
        }
    }

    public void remove(String keyname){
        //removes values from the Lists
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).equals(keyname)){
                keys.remove(i);
                values.remove(i);
                break;
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

    public SocketDict fromString(String string) {
        //converts the created String (made in toString()) back to a SocketDict object

        SocketDict converted_from_String = new SocketDict();
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
        int end_of_last_key = 1;

        for (int i = 1; i < keys_end; i++) {
            char character = string.charAt(i);
            if(character == '\\'){
                if(string.charAt(i + 1) == 'd'){
                    converted_from_String.add(string.substring(end_of_last_key, i), "");
                    end_of_last_key = i + 2;
                }
            } else if(character == '}'){
                converted_from_String.add(string.substring(end_of_last_key, i), "");
            }
        }

        //gets all the values and puts them into the values_from_string List
        int values_beginning = keys_end + 1;
        int end_of_last_val = values_beginning;
        int pos = 0;

        for (int i = values_beginning; i < string.length() + 1; i++) {
            char character = string.charAt(i);
            if(character == '\\'){
                if(string.charAt(i + 1) == 'd'){
                    converted_from_String.setValueByPos(pos, string.substring(end_of_last_val, i));
                    end_of_last_val = i + 2;
                    pos++;
                }
            } else if(character == '}'){
                converted_from_String.setValueByPos(pos, string.substring(end_of_last_val, i));
                break;
            }
        }

        return converted_from_String;
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

    public boolean canBeConvertedToInteger(String key_for_value_to_be_checked) {
        if(keys.contains(key_for_value_to_be_checked)) {
            for (int i = 0; i < keys.size(); i++) {
                if (keys.get(i).equals(key_for_value_to_be_checked)) {
                    try {
                        Integer.parseInt(values.get(i));
                        return true;
                    } catch (Exception NumberFormatException) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean canBeConvertedToDouble(String key_for_value_to_be_checked) {
        if(keys.contains(key_for_value_to_be_checked)) {
            for (int i = 0; i < keys.size(); i++) {
                if (keys.get(i).equals(key_for_value_to_be_checked)) {
                    try {
                        Double.parseDouble(values.get(i));
                        return true;
                    } catch (Exception NumberFormatException) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public Integer convertToInteger(String key_for_value_to_be_converted){
        int integer = 0;
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).equals(key_for_value_to_be_converted)) {
                integer = Integer.parseInt(values.get(i));
            }
        }
        return integer;
    }

    public Double convertToDouble(String key_for_value_to_be_converted){
        Double integer = 0.0;
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).equals(key_for_value_to_be_converted)) {
                integer = Double.parseDouble(values.get(i));
            }
        }
        return integer;
    }
}
