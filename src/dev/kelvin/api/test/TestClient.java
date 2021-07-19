package dev.kelvin.api.test;

import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.Remote;

import java.lang.reflect.InvocationTargetException;

public class TestClient {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        TestClient t = new TestClient();
        t.test();
    }

    private final HighLevelNetworkAPI hln;

    public TestClient() {
        hln = new HighLevelNetworkAPI(this);
        hln.createClient();
    }

    public void test() throws InvocationTargetException, IllegalAccessException {
        hln.call("dosth", "Hello World!");
    }

    @Remote(1)
    public void dosth(String printThis) {
        System.out.println(printThis);
    }

}
