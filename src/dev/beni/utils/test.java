package dev.beni.utils;

import dev.beni.utils.SocketDict;

import java.util.List;

public class test {

    public static void main(String[] args) {
        SocketDict socdic = new SocketDict();

        socdic.add("name", "tom");
        socdic.add("size", "190");
        socdic.printout();
        socdic.setValueByKey("name", "Tom");
        //socdic.printout();
        //System.out.println(socdic.toString());
        System.out.println("Lül");

        socdic.fromString(socdic.toString());
        System.out.println("lül");
        //socdic2.printout();

        //socdic.remove("name");
        //socdic.printout();
        //System.out.println(socdic.toString());

        //System.out.println(socdic.canBeConvertedToInteger("size"));
        //System.out.println(socdic.canBeConvertedToDouble("size"));
        //System.out.println(socdic.convertToInteger("size"));
    }

}
