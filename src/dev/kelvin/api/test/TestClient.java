package dev.kelvin.api.test;

import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.Remote;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class TestClient {

    public static void main(String[] args) {
        TestClient t = new TestClient();
    }

    private final HighLevelNetworkAPI hln;
    private final Scanner scan;

    public TestClient() {
        hln = new HighLevelNetworkAPI(this);
        hln.createClient("localhost", 7777);
        scan = new Scanner(System.in);
        fetchInput();
    }

    public void fetchInput() {
        String input = "";
        do {
            input = scan.nextLine();
            hln.rct_id(1, "receiveMsg", input);
        } while (!input.equals("exit"));
        hln.disconnect();
    }

    @Remote(1)
    public void receiveMsg(String s) {
        System.out.println(s);
    }

}
