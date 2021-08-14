package dev.kelvin.api.network;

import dev.beni.utils.SocketDict;
import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.network.utils.Utils;

import java.io.IOException;
import java.net.*;

public class Client extends NetworkParticipant {

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
    public void rcu_id(int uuid, String methodName, String... args) {
        if (uuid == 1) {
            SocketDict sendDict = Utils.buildFromMethodNameAndArgs(methodName, args);
            byte[] data = (sendDict + endString).getBytes();
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

    public void rcu(String methodName, String...args) {
        rcu_id(1, methodName, args);
    }

    @Override
    public void rct_id(int uuid, String methodName, String... args) {
        if (uuid == 1) {

        } else {
            System.err.println("Client is only allowed to send to 1");
        }
    }

    public void rct(String methodName, String...args) {
        rct_id(1, methodName, args);
    }

    @Override
    protected void listenUdp() {
        while (running) {
            Utils.defaultListenUDP(udpSocket, hln);
        }
    }

    @Override
    protected void listenTcp() {

    }

}
