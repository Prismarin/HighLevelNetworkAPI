package dev.kelvin.api.network;

import dev.kelvin.api.HighLevelNetworkAPI;

import java.io.IOException;
import java.net.*;

public class Client extends Network {

    protected DatagramSocket udpSocket;
    protected Socket tcpSocket;

    protected String ipString;
    protected InetAddress ip;
    protected int port;

    public Client(Object object, HighLevelNetworkAPI hln, String address, int port) {
        super(object, hln);

        try {
            udpSocket = new DatagramSocket();

            this.ipString = address;
            this.ip = InetAddress.getByName(address);
            this.port = port;

            tcpSocket = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
