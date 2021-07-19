package dev.kelvin.api.network;

import dev.kelvin.api.HighLevelNetworkAPI;

public class Server extends Network {

    public Server(Object object, HighLevelNetworkAPI hln) {
        super(object, hln);
    }

    @Override
    public void send_udp(long uuid, String methodName, String... args) {

    }

    @Override
    public void send_tcp(long uuid, String methodName, String... args) {

    }

    @Override
    protected void listenUdp() {

    }

    @Override
    protected void listenTcp() {

    }

}
