package socketTest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TestServer extends Thread {

    private final ServerSocket server;

    public TestServer() throws IOException {
        server = new ServerSocket(1235);
        server.setSoTimeout(0);
    }

    @Override
    public synchronized void run() {
        while (true) {
            try {
                System.out.println("Waiting for client on port " + server.getLocalPort() + "...");
                Socket client = server.accept();

                System.out.println("Just connected to " + client.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(client.getInputStream());
                System.out.println(in.readUTF());

                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                out.writeUTF("Thank you for connecting!");

                wait(15000);

                out.writeUTF("Test");
                client.close();
            } catch (SocketTimeoutException e) {
                System.out.println("Socket timed out");
                e.printStackTrace();
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            Thread t = new TestServer();
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
