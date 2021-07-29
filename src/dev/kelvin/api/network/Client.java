package dev.kelvin.api.network;

import dev.beni.utils.SocketDict;
import dev.kelvin.api.HighLevelNetworkAPI;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
        if (uuid == 1) {
            SocketDict sendDict = new SocketDict();
            sendDict.add("m", methodName);  //m == methodName
            sendDict.add("s", String.valueOf(args.length));     //s == size
            for (int i = 0; i < args.length; i++) {
                sendDict.add("a" + i, args[i]);     //a == arg
            }
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

    @Override
    public void send_tcp(long uuid, String methodName, String... args) {
        if (uuid == 1) {

        } else {
            System.err.println("Client is only allowed to send to 1");
        }
    }

    @Override
    protected void listenUdp() {
        while (running) {
            byte[] data = new byte[8192];

            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                udpSocket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Not working on with this msg!");
                continue;
            }

            String msg = new String(data);
            msg = msg.substring(0, msg.indexOf(endString));

            SocketDict receiveDict = SocketDict.fromString(msg);
            int argLength = Integer.parseInt(receiveDict.get("s"));
            String[] args = new String[argLength];
            for (int i = 0; i < argLength; i++) {
                args[i] = receiveDict.get("a" + i);
            }
            try {
                hln.call(receiveDict.get("m"), args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void listenTcp() {

    }

}
