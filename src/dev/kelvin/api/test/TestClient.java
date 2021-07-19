package dev.kelvin.api.test;

import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.Remote;

public class TestClient {

    public static void main(String[] args) {
        //HighLevelNetworkAPI hln = new HighLevelNetworkAPI();
        //hln.createClient();
    }

    @Remote
    public void dosth(String printThis) {
        System.out.println(printThis);
    }

    public void giveClientKey() {

    }

}
