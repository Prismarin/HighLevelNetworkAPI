package dev.kelvin.api.network;

import dev.kelvin.api.HighLevelNetworkAPI;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

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
        if (uuid == 1) {

        } else {
            System.err.println("Client is only allowed to send to 1");
        }
    }

    @Override
    public void send_tcp(long uuid, String methodName, String... args) {
        if (uuid == 1) {
            // mit dictionary
            String send = methodName + ";" + Arrays.toString(args);
            byte[] data = send.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);

            try {
                udpSocket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Client is only allowed to send to 1");
        }
    }

    @Override
    protected void listenUdp() {

    }

    @Override
    protected void listenTcp() {

    }

}
