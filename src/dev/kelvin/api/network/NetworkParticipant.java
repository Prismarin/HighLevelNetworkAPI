package dev.kelvin.api.network;

import dev.kelvin.api.HighLevelNetworkAPI;

public abstract class NetworkParticipant {

    public static final String endString = "\\e";

    protected Object netObject;
    protected HighLevelNetworkAPI hln;

    protected boolean running;

    protected Thread udpListener, tcpListener;

    public NetworkParticipant(Object object, HighLevelNetworkAPI hln) {
        this.netObject = object;
        this.hln = hln;
    }

    public void start() {
        if (!running) {
            running = true;
            udpListener = new Thread(this::listenUdp);
            tcpListener = new Thread(this::listenTcp);
            udpListener.start();
            tcpListener.start();
        }
    }

    public abstract void rcu_id(int uuid, String methodName, String...args);

    public abstract void rct_id(int uuid, String methodName, String...args);

    protected abstract void listenUdp();

    protected abstract void listenTcp();

    public void stop() {
        running = false;
    }

}
