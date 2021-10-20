package dev.beni.utils.Betas;

public class Test {

    public static void main(String[] args) {
        TreeDictBeta<String, String> tdb = new TreeDictBeta<>("Hullo", "Hey");
        tdb.add("Nice", "GG");
        tdb.add("EZ", "NGL");
        tdb.traverseInOrder2(tdb.root);
        String string = tdb.toString();
        System.out.println(string);
        TreeDictBeta tdb2 = tdb.fromString(string);
        tdb2.traverseInOrder2(tdb2.root);
    }

}
