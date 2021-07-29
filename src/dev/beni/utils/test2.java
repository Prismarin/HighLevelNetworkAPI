package dev.beni.utils;

import java.io.*;
import java.util.*;

public class test2 {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/dev/beni/utils/test_file.sds");

        try {
            Scanner filereader = new Scanner(file);
            while(filereader.hasNextLine()){
                String data = filereader.nextLine();
                System.out.println(data);
            }
            filereader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter filewriter = new FileWriter("src/dev/beni/utils/test_file.sds", true);
            filewriter.write("{lol}");
            filewriter.write("{lol}");
            filewriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter filewriter = new FileWriter("src/dev/beni/utils/test_file.sds", true);
            filewriter.write("{lol}");
            filewriter.write("{lol}");
            filewriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Scanner filereader = new Scanner(file);
            while(filereader.hasNextLine()){
                String data = filereader.nextLine();
                System.out.println(data);
            }
            filereader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
