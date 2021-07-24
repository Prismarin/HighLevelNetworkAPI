package dev.beni.utils;

import dev.beni.utils.SocketDict;

import java.util.List;

public class test {

    public static void main(String[] args) {
        SocketDict socdic = new SocketDict();
        SocketDict socdic2 = new SocketDict();

        socdic.add("name", "tom");
        socdic.add("size", "1.90m");
        //socdic.printout();
        socdic.setValue("name", "Tom");
        //socdic.printout();
        //System.out.println(socdic.toString());
        List[] array = socdic2.fromString(socdic.toString());

        //System.out.println(array[0].get(0));
        //System.out.println(array[1].get(0));

        //System.out.println(array[0].get(1));
        //System.out.println(array[1].get(1));

        List keys_from_string = socdic2.KeysfromString(socdic.toString());
        for (int i = 0; i < keys_from_string.size(); i++) {
            System.out.print("key:");
            System.out.println(keys_from_string.get(i));
        }

        List values_from_string = socdic2.ValuesFromString(socdic.toString());
        for (int i = 0; i < values_from_string.size(); i++) {
            System.out.print("value:");
            System.out.println(values_from_string.get(i));
        }

        socdic.remove("name");
        //socdic.printout();
        //System.out.println(socdic.toString());
    }

}
