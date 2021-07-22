package dev.beni.utils;

import dev.beni.utils.SocketDict;

public class test {

    public static void main(String[] args) {
        SocketDict socdic = new SocketDict();

        socdic.add("name", "tom");
        socdic.add("size", "1.90m");
        socdic.printout();
    }

}
