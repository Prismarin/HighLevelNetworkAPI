package dev.kelvin.api.network;

import dev.beni.utils.SocketDict;
import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.network.info.ConnectionInfo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server extends Network {

    protected DatagramSocket udpSocket;
    protected ServerSocket tcpSocket;

    protected ArrayList<ConnectionInfo> connectionInfos;

    protected int port;

    public Server(Object object, HighLevelNetworkAPI hln, int port) {
        super(object, hln);
        this.port = port;

        connectionInfos = new ArrayList<>();

        try {
            udpSocket = new DatagramSocket(port);
            tcpSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rcu_id(long uuid, String methodName, String... args) {
        if (uuid == 0) {
            //send all
        } else {
            //send one
        }
    }

    public void rcu(String methodName, String... args) {
        rcu_id(0, methodName, args);
    }

    @Override
    public void rct_id(long uuid, String methodName, String... args) {

    }

    public void rct(String methodName, String... args) {
        rct_id(0, methodName, args);
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
                if (receiveDict.get("m").equals("connect"))
                    addConnection();
                hln.call(receiveDict.get("m"), args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addConnection() {

    }

    /**
     *
     * <h4>
     *     Server does not use listenTCP <br>
     *     Server uses a <strong>threadPool</strong> to listen for tcp messages
     * </h4>
     *
     */
    @Override
    protected void listenTcp() {

    }

}
