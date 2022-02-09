package dev.beni.utils.Betas;

import dev.beni.utils.Dict;
import dev.beni.utils.Node;

public class Test {

    public static void main(String[] args) {
        Dict dict = new Dict(new Node<String, String>("msg", "Hello"));
        dict.insert(new Node(1, "eins"));
        dict.insert(new Node("w", "w"));
        dict.output();
        //System.out.println();
        //dict.remove("msg");
        //dict.output();
        String string = dict.toString();
        Dict dict2 = dict.fromString(string);
        System.out.println("\ndict2: ");
        dict2.output();
    }

}
