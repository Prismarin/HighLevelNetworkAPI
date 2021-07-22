package dev.kelvin.api.network;

import dev.kelvin.api.HighLevelNetworkAPI;

public abstract class Network {

    protected Object netObject;
    protected HighLevelNetworkAPI hln;

    protected boolean running;

    protected Thread udpListener, tcpListener;

    public Network(Object object, HighLevelNetworkAPI hln) {
        this.netObject = object;
        this.hln = hln;
    }

    public void start() {
        if (!running) {
            running = true;
            udpListener = new Thread(this::listenUdp);
            tcpListener = new Thread(this::listenTcp);
        }
    }

    public void send_udp(String methodName, String...args) {
        if (!(this instanceof Server))
            send_udp(1, methodName, args);
    }

    public void send_tcp(String methodName, String...args) {
        if (!(this instanceof Server))
            send_tcp(1, methodName, args);
    }

    public abstract void send_udp(long uuid, String methodName, String...args);

    public abstract void send_tcp(long uuid, String methodName, String...args);

    protected abstract void listenUdp();

    protected abstract void listenTcp();

}
