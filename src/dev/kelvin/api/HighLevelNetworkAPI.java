package dev.kelvin.api;

import dev.kelvin.api.network.Client;
import dev.kelvin.api.network.Network;
import dev.kelvin.api.network.Server;

public class HighLevelNetworkAPI {

    protected Object netObject;
    protected Network net;

    public HighLevelNetworkAPI(Object object) {
        this.netObject = object;
    }

    public void createServer() {
        if (net == null)
            net = new Client(netObject);
    }

    public void createClient() {
        if (net == null)
            net = new Server(netObject);
    }

    /**
     *
     * udp method
     *
     * @param peerId case 0 send to all, case 1 send to server, case n send to specific peer
     * @param methodName name of called method on the other side
     * @param strings parameters for called methods on other side
     */
    public void send_unreliable(long peerId, String methodName, String... strings) {}

    /**
     *
     * tcp method
     *
     * @param peerId case 0 send to all, case 1 send to server, case n send to specific peer
     * @param methodName name of called method on the other side
     * @param strings parameters for called methods on other side
     */
    public void send(long peerId, String methodName, String... strings) {}



}
