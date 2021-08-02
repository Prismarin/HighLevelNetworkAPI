package dev.beni.utils;

import java.io.*;
import java.util.Scanner;

public class SocketDictSaver {

    public static void writeNewSocketDictToFile(SocketDict socdict){
        try {
            FileWriter filewriter = new FileWriter("src/dev/beni/utils/test_file.sds", true);
            filewriter.write("{lol}");
            filewriter.write("\n");
            filewriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readSocketDictFromFile() {
        File file = new File("src/dev/beni/utils/test_file.sds");
        try {
            Scanner filereader = new Scanner(file);
            while (filereader.hasNextLine()) {
                String data = filereader.nextLine();
                System.out.println(data);
            }
            filereader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
