package dev.kelvin.api.test;

import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.Remote;

import java.util.Scanner;

public class TestServer {

    public static void main(String[] args) {
        TestServer t = new TestServer();
    }

    private final HighLevelNetworkAPI hln;
    private final Scanner scan;

    public TestServer() {
        hln = new HighLevelNetworkAPI(this);
        hln.createServer(7777);
        scan = new Scanner(System.in);
        fetchInput();
    }

    public void fetchInput() {
        String input = "";
        do {
            input = scan.nextLine();
            hln.rct_id(0, "receiveMsg", input);
        } while (!input.equals("exit"));
    }

    @Remote(1)
    public void receiveMsg(String msg) {
        System.out.println(msg);
    }

}
