package dev.kelvin.api.network.info;

import dev.beni.utils.SocketDict;
import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.network.utils.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ConnectionInfo extends Thread {

    private final HighLevelNetworkAPI hln;

    public final InetAddress address;
    public final int port;
    public final Socket tcpSocket;

    public final DataInputStream in;
    public final DataOutputStream out;

    public ConnectionInfo(HighLevelNetworkAPI hln, Socket tcpSocket) throws IOException {
        this.hln = hln;
        this.tcpSocket = tcpSocket;
        this.address = tcpSocket.getInetAddress();
        this.port = tcpSocket.getPort();

        this.in = new DataInputStream(this.tcpSocket.getInputStream());
        this.out = new DataOutputStream(this.tcpSocket.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                String input = in.readUTF();
                SocketDict dict = SocketDict.fromString(input);
                if (dict.get("m").equals("//dis")) {
                    tcpSocket.close();
                    break;
                }
                Utils.workWithReceivedData(hln, dict);
            } catch (EOFException ignored) {
                System.err.println("Client just disconnected without telling the server!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    tcpSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }

    public boolean send(String send) throws IOException {
        if (!tcpSocket.isConnected() || tcpSocket.isClosed())
            return false;
        out.writeUTF(send);
        return true;
    }

}
