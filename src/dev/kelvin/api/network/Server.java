package dev.kelvin.api.network;

import dev.beni.utils.SocketDict;
import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.network.info.ConnectionInfo;
import dev.kelvin.api.network.utils.Utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.*;

public class Server extends NetworkParticipant {

    protected DatagramSocket udpSocket;
    protected ServerSocket tcpSocket;

    protected HashMap<Integer, ConnectionInfo> clients;

    protected int port;

    protected Random rand;

    public Server(Object object, HighLevelNetworkAPI hln, int port) {
        super(object, hln);
        this.port = port;

        clients = new HashMap<>();
        rand = new Random();

        try {
            udpSocket = new DatagramSocket(port);
            tcpSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rcu_id(int uuid, String methodName, String... args) {
        if (uuid == 0) {
            //send all
            for (Map.Entry<Integer, ConnectionInfo> info : clients.entrySet()) {
                rcu_id(info.getKey(), methodName, args);
            }
        } else {
            //send one
            if (clients.containsKey(uuid)) {
                SocketDict sendDict = Utils.buildFromMethodNameAndArgs(methodName, args);
                ConnectionInfo info = clients.get(uuid);
                try {
                    byte[] data = (sendDict + endString).getBytes();
                    DatagramPacket packet = new DatagramPacket(data, data.length, info.address, info.port);
                    udpSocket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                throw new IllegalArgumentException(uuid + " does not exist!");
            }
        }
    }

    @Override
    public void rcu(String methodName, String... args) {
        rcu_id(0, methodName, args);
    }

    @Override
    public void rct_id(int uuid, String methodName, String... args) {
        if (uuid == 0) {
            for (Map.Entry<Integer, ConnectionInfo> info : clients.entrySet()) {
                rct_id(info.getKey(), methodName, args);
            }
        } else {
            if (clients.containsKey(uuid)) {
                SocketDict sendDict = Utils.buildFromMethodNameAndArgs(methodName, args);
                ConnectionInfo info = clients.get(uuid);
                try {
                    boolean connected = info.send(sendDict.toString());
                    if (!connected)
                        clients.remove(uuid);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                throw new IllegalArgumentException(uuid + " does not exist!");
            }
        }
    }

    @Override
    public void rct(String methodName, String... args) {
        rct_id(0, methodName, args);
    }

    @Override
    protected void listenUdp() {
        while (running) {
            Utils.defaultListenUDP(udpSocket, hln);
        }
    }

    /**
     *
     * <h4>
     *     Server uses listenTCP for accepting new clients <br>
     *     Server uses a <strong>threadPool</strong> to listen for tcp messages
     * </h4>
     *
     */
    @Override
    protected void listenTcp() {
        while (running) {
            ConnectionInfo newClient = null;
            boolean use = true;
            try {
                newClient = new ConnectionInfo(hln, tcpSocket.accept());
            } catch (IOException e) {
                e.printStackTrace();
                use = false;
            }

            if (use) {
                int userId = generateUniqueUserId();
                clients.put(userId, newClient);
                newClient.start();
                //trigger event client connected with userId
                hln.triggerConnectionSucceeded(userId);
            }
        }
    }

    private int generateUniqueUserId() {
        int ret;
        do {
            ret = rand.nextInt();
        } while (clients.containsKey(ret) || ret <= 1);
        return ret;
    }

}
