package dev.beni.utils;

public class test {

    public static void main(String[] args) {
        SocketDict socdic =  new SocketDict("name", "Tom");
        socdic.add("lastname", "Cena");
        socdic.add("zbs", "BurgerKing");
        socdic.printout();
        System.out.println();
        SocketDictSaver.saveInFile("C:/Users/Beni/Downloads/SocketDictSaved", socdic);
        System.out.println("saved");
        SocketDict socdic2 = SocketDictSaver.readFromFile("C:/Users/Beni/Downloads/SocketDictSaved.ssd");
        socdic2.printout();
    }

}
