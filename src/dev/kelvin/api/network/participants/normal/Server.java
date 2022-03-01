package dev.kelvin.api.network.participants.normal;

import dev.beni.utils.SocketDictStringsOnly;
import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.network.NetworkParticipant;
import dev.kelvin.api.network.info.ConnectionInfo;
import dev.kelvin.api.network.utils.Utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.*;

/**
 * @since 1.0
 * @version 1.1
 *
 * <h2>
 *     Can support up to 100 clients flawlessly
 * </h2>
 */
public class Server extends NetworkParticipant {

    protected DatagramSocket udpSocket;
    protected ServerSocket tcpSocket;

    protected ConnectionInfo[] tcpClients;
    protected int[][] userIdsToTcpClientsPosition;

    protected int port;
    protected int maxUsers;

    protected Random rand;

    public Server(Object object, HighLevelNetworkAPI hln, int port, int maxUsers) {
        super(object, hln);
        hln.addOnConnectionClosed(this::remove);
        this.port = port;

        this.maxUsers = maxUsers;
        tcpClients = new ConnectionInfo[maxUsers];
        userIdsToTcpClientsPosition = new int[maxUsers][2];

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
            for (int i = 0; i < maxUsers; i++) {
                rcu_id(userIdsToTcpClientsPosition[i][0], methodName, args);
            }
        } else {
            //send one
            if (doesUuidExist(uuid)) {
                SocketDictStringsOnly sendDict = Utils.buildFromMethodNameAndArgs(methodName, args);
                //should not throw exception
                ConnectionInfo info = getInfo(uuid);
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
            for (int i = 0; i < maxUsers; i++) {
                int userId = userIdsToTcpClientsPosition[i][0];
                if (userId != 0)
                    rct_id(userId, methodName, args);
            }
        } else {
            if (doesUuidExist(uuid)) {
                SocketDictStringsOnly sendDict = Utils.buildFromMethodNameAndArgs(methodName, args);
                ConnectionInfo info = getInfo(uuid);
                try {
                    boolean connected = info.send(sendDict.toString());
                    if (!connected)
                        remove(uuid);
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
            int userId = generateUniqueUserId();
            try {
                newClient = new ConnectionInfo(hln, userId, tcpSocket.accept());
            } catch (IOException e) {
                e.printStackTrace();
                use = false;
            }

            if (use) {
                boolean hasSpace = add(userId, newClient);
                if (!hasSpace) {
                    System.err.println("The Server has no space left!");
                    continue;
                }
                newClient.start();
                hln.triggerConnectionSucceeded(userId);
            }
        }
    }

    protected int generateUniqueUserId() {
        int ret;
        do {
            ret = rand.nextInt();
        } while (doesUuidExist(ret));
        return ret;
    }

    public boolean doesUuidExist(int uuid) {
        if (uuid == 0 || uuid == 1)
            return true;
        for (int i = 0; i < maxUsers; i++) {
            if (userIdsToTcpClientsPosition[i][0] == uuid)
                return true;
        }
        return false;
    }

    /**
     * does not check whether uuid exists
     * @param uuid the uuid the {@link ConnectionInfo} is requested
     * @return the {@link ConnectionInfo} from the uuid
     * @throws ArrayIndexOutOfBoundsException when the uuid does not exist
     */
    public ConnectionInfo getInfo(int uuid) {
        int info = maxUsers;
        for (int i = 0; i < maxUsers; i++) {
            if (userIdsToTcpClientsPosition[i][0] == uuid)
                info = userIdsToTcpClientsPosition[i][1];
        }
        return tcpClients[info];
    }

    /**
     *
     * @param uuid the uuid to add
     * @param info the {@link ConnectionInfo} to add
     * @return if the server has space to add this client or not, the {@link HighLevelNetworkAPI}
     */
    public boolean add(int uuid, ConnectionInfo info) {
        int infoSpot = -1;
        for (int i = 0; i < maxUsers; i++) {
            if (tcpClients[i] == null) {
                tcpClients[i] = info;
                infoSpot = i;
                break;
            }
        }
        if (infoSpot == -1)
            return false;
        for (int i = 0; i < maxUsers; i++) {
            if (userIdsToTcpClientsPosition[i][0] == 0) {
                userIdsToTcpClientsPosition[i][0] = uuid;
                userIdsToTcpClientsPosition[i][1] = infoSpot;
                return true;
            }
        }
        return false;
    }

    /**
     * does nothing when the uuid does not exist
     * @param uuid the uuid that should be removed
     */
    public void remove(int uuid) {
        for (int i = 0; i < maxUsers; i++) {
            if (userIdsToTcpClientsPosition[i][0] == uuid) {
                int info = userIdsToTcpClientsPosition[i][1];
                userIdsToTcpClientsPosition[i][0] = 0;
                userIdsToTcpClientsPosition[i][1] = 0;
                tcpClients[info] = null;
            }
        }
    }

}
