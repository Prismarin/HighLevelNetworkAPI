package dev.beni.utils;

import java.io.*;

public class SocketDictSaver {

    public static void saveInFile(String fileNameWithoutEnding, SocketDict socketDictToBeSaved){
        try {
            String fileName = fileNameWithoutEnding + ".ssd.hln";
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            StringBuilder SocketDictAsString = new StringBuilder(socketDictToBeSaved.toString());
            out.writeObject(SocketDictAsString);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static SocketDict readFromFile(String fileName){
        SocketDict readIn;
        StringBuilder readInString;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            readInString = (StringBuilder) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("SocketDict class not found");
            c.printStackTrace();
            return null;
        }
        readIn = SocketDict.fromString(readInString.toString());

        return readIn;
    }

}
