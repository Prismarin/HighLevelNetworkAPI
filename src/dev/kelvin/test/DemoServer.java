package dev.kelvin.test;

import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.Remote;

import java.util.Scanner;

public class DemoServer {

    public static void main(String[] args) {
        HighLevelNetworkAPI hln = new HighLevelNetworkAPI(new DemoServer());
        hln.addOnConnectionClosed(uuid -> {
            System.out.println("User with id " + uuid + " disconnected!");
        });
        hln.createServer(1235, 100);
        Scanner scan = new Scanner(System.in);
        String in;
        do {
            in = scan.nextLine();
            hln.rct("say", in);
        } while (!in.equals("exit"));
        System.exit(12);
    }

    @Remote(1)
    public void say(String s) {
        System.out.println("CLIENT: " + s);
    }

}
