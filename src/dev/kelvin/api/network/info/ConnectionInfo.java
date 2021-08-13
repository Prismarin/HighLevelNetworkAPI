package dev.kelvin.api.network.info;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ConnectionInfo {

    public final InetAddress address;
    public final int port;
    public final Socket tcpSocket;

    private DataInputStream in;
    private DataOutputStream out;

    public ConnectionInfo(Socket tcpSocket) {
        this.tcpSocket = tcpSocket;
        this.address = tcpSocket.getInetAddress();
        this.port = tcpSocket.getPort();

        try {
            this.in = new DataInputStream(this.tcpSocket.getInputStream());
            this.out = new DataOutputStream(this.tcpSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataInputStream in() {
        return in;
    }

    public DataOutputStream out() {
        return out;
    }

}
