package dev.beni.utils.Tests.Streams;

import java.util.*;
import java.util.stream.Collectors;

public class SocketDictStreams {

    private final ArrayList<String> keys;
    private final ArrayList<String> values;

    public SocketDictStreams() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
    }

    public void printout() {
        //prints the keys and corresponding values to the console
        for (int i = 0; i < keys.size(); i++) {
            System.out.print("(key:");
            System.out.print(keys.get(i));
            System.out.print(", value:");
            System.out.print(values.get(i) + ") ");
        }
        System.out.println();

    }

    public void setValueByKey(String key, String new_value){
        //sets the value of the above specified key to new_value
        values.set(
                keys.stream().collect(Collectors.toList()).indexOf(key),
                new_value
        );
    }

    public void setValueByPos(int pos, String new_value){
        //sets the value at the position pos to new_value
        values.set(pos, new_value);
    }

    public void add(String key, String value) {
        //adds a new value to the List with the above defined key
        if(!keys.contains(key)) {
            keys.add(key);
            values.add(value);
        }
    }

    public void remove(String key){
        //removes a key from the List with its linked value
        int i = keys.stream().collect(Collectors.toList()).indexOf(key);
        keys.remove(i);
        values.remove(i);
    }

    public String get(String key) {
        //returns the value of a key
        return values.get(
                keys.stream().collect(Collectors.toList()).indexOf(key)
        );
    }

    @Override
    public String toString() {
        //converts the SocketDict to a String in order to send it via sockets or store it more compact
        StringBuilder stringified = new StringBuilder("{");
        toStringListBuilder(stringified, keys);
        stringified.append("}{");
        toStringListBuilder(stringified, values);
        stringified.append("}");
        return stringified.toString();
    }

    private void toStringListBuilder(StringBuilder stringified, ArrayList<String> list) {
        //converts the List list to a String
        for (int i = 0; i < keys.size(); i++) {
            int len = keys.size() - 1;
            if(i == len){
                stringified.append(list.get(i));
            } else {
                stringified.append(list.get(i));
                stringified.append("\\d");
            }
        }
    }

    public static SocketDictStreams fromString(String string) {
        //converts the created String (made with toString()) back to a SocketDict object
        SocketDictStreams converted_from_String = new SocketDictStreams();

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
                break;
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

    public boolean canBeConvertedToInteger(String key) {
        //checks if value of key can be converted to int
        try {
            Integer.parseInt(values.get(keys.stream().collect(Collectors.toList()).indexOf(key)));
            return true;
        } catch (Exception NumberFormatException) {
            return false;
        }
    }

    public boolean canBeConvertedToDouble(String key) {
        //checks if value of key can be converted to Double
        try {
            Double.parseDouble(values.get(keys.stream().collect(Collectors.toList()).indexOf(key)));
            return true;
        } catch (Exception NumberFormatException) {
            return false;
        }
    }

    public Integer convertToInteger(String key){
        //converts value of key to int
        return Integer.parseInt(values.get(
                keys.stream()
                        .collect(Collectors.toList())
                        .indexOf(key))
        );
    }

    public Double convertToDouble(String key){
        //converts value of key to int
        return Double.parseDouble(values.get(
                keys.stream()
                        .collect(Collectors.toList())
                        .indexOf(key))
        );
    }
}

