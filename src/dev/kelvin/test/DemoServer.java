package dev.kelvin.test;

import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.Remote;

import java.util.Scanner;

public class DemoServer {

    public static void main(String[] args) {
        HighLevelNetworkAPI hln = new HighLevelNetworkAPI(new DemoClient());
        hln.addOnConnectionClosed(uuid -> {
            System.out.println("User with id " + uuid + " disconnected!");
        });
        hln.createPingingServer(1235, 1000, 10);
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
