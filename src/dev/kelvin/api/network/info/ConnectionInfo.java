package dev.kelvin.api.network.info;

import java.net.InetAddress;
import java.net.Socket;

public class ConnectionInfo {

    public final InetAddress address;
    public final int port;
    public final Socket tcpSocket;
    public final boolean isServer;

    public ConnectionInfo(Socket tcpSocket, InetAddress address, int port, boolean isServer) {
        this.tcpSocket = tcpSocket;
        this.address = address;
        this.port = port;
        this.isServer = isServer;
    }

}
