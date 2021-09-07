package dev.kelvin.api.network.participants.normal;

import dev.beni.utils.SocketDict;
import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.network.NetworkParticipant;
import dev.kelvin.api.network.utils.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;

/**
 * @since 1.0
 * @version 1.0
 */
public class Client extends NetworkParticipant {

    protected DatagramSocket udpSocket;
    protected Socket tcpSocket;

    protected DataInputStream in;
    protected DataOutputStream out;

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

            in = new DataInputStream(tcpSocket.getInputStream());
            out = new DataOutputStream(tcpSocket.getOutputStream());

            hln.triggerConnectionSucceeded(0);
        } catch (UnknownHostException | ConnectException e) {
            hln.triggerConnectionFailed();
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

    /**
     *
     * <h4>
     *     sends to the server
     * </h4>
     */
    @Override
    public void rcu(String methodName, String...args) {
        rcu_id(1, methodName, args);
    }

    @Override
    public void rct_id(int uuid, String methodName, String... args) {
        if (uuid == 1) {
            SocketDict sendDict = Utils.buildFromMethodNameAndArgs(methodName, args);

            try {
                out.writeUTF(sendDict.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Client is only allowed to send to 1");
        }
    }

    /**
     *
     * <h4>
     *     sends to the server
     * </h4>
     */
    @Override
    public void rct(String methodName, String...args) {
        rct_id(1, methodName, args);
    }

    @Override
    protected void listenUdp() {
        if (in == null)
            return;
        while (running) {
            Utils.defaultListenUDP(udpSocket, hln);
        }
    }

    @Override
    protected void listenTcp() {
        if (in == null)
            return;
        while (running) {
            try {
                String input = in.readUTF();
                SocketDict receiveDict = SocketDict.fromString(input);
                Utils.workWithReceivedData(hln, receiveDict);
            } catch (EOFException ignored) {
                hln.triggerConnectionClosed(0);
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
