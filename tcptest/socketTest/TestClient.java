package socketTest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TestClient {

    public static void main(String[] args) {
        String ip = "127.0.0.1";
        int port = 1235;
        try {
            System.out.println("Connecting to " + ip + " on port " + port);
            Socket client = new Socket(ip, port);

            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());

            out.writeUTF("Hello from " + client.getRemoteSocketAddress());

            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println("Server says: " + in.readUTF());
            System.out.println(in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
