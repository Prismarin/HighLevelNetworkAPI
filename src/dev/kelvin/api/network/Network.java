package dev.kelvin.api.network;

import java.net.DatagramSocket;

public abstract class Network {

    protected Object netObject;
    protected DatagramSocket udp_socket;


    public Network(Object object) {
        this.netObject = object;
    }

    public abstract void send(long peerId, String methodName, String...args);

    public void receive() {

    }

}
