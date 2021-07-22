package dev.kelvin.api.network;

import dev.kelvin.api.HighLevelNetworkAPI;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class Server extends Network {

    protected DatagramSocket udpSocket;
    protected ServerSocket tcpSocket;

    protected int port;

    public Server(Object object, HighLevelNetworkAPI hln, int port) {
        super(object, hln);
        this.port = port;

        try {
            udpSocket = new DatagramSocket(port);
            tcpSocket = new ServerSocket(port);
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
