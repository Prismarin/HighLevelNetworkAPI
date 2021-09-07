package dev.kelvin.test;

import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.Remote;

import java.util.Scanner;

public class DemoServer {

    public static void main(String[] args) {
        HighLevelNetworkAPI hln = new HighLevelNetworkAPI(new DemoClient());
        hln.createServer(1235);
        Scanner scan = new Scanner(System.in);
        String in;
        do {
            in = scan.nextLine();
            hln.rct("say", in);
        } while (!in.equals("exit"));
    }

    @Remote(1)
    public void say(String s) {
        System.out.println("CLIENT: " + s);
    }

}
