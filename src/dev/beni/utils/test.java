package dev.beni.utils;

import dev.beni.utils.SocketDict;

public class test {

    public static void main(String[] args) {
        SocketDict socdic = new SocketDict();

        socdic.add("name", "tom");
        socdic.add("size", "190");
        socdic.printout();
        socdic.setValueByKey("name", "Tom");
        socdic.printout();
        System.out.println(socdic.toString());

        String string = socdic.toString();

        SocketDict socdic2 = socdic.fromString(string);
        System.out.print("socdic2: ");
        socdic2.printout();

        socdic.remove("name");
        socdic.printout();
        System.out.println(socdic.toString());

        System.out.println(socdic.canBeConvertedToInteger("size"));
        System.out.println(socdic.canBeConvertedToDouble("size"));
        System.out.println(socdic.convertToInteger("size"));
        System.out.println(socdic.convertToDouble("size"));

        System.out.println();
        System.out.println();
        socdic.add("name", "John");
        //socdic.printoutstreams();
    }

}
