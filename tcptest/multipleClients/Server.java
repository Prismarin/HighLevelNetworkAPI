package multipleClients;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public int maxUsers;
    private int currentUsers;

    public Server(int maxUsers) {
        this.maxUsers = maxUsers;
        currentUsers = 0;
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            boolean use = true;
            ClientHandler handler = null;
            try {
                handler = new ClientHandler(serverSocket.accept(), maxUsers, currentUsers);
            } catch (IllegalArgumentException ignored) {
                use = false;
            }

            if (use) {
                handler.start();
                currentUsers++;
            }

        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class ClientHandler extends Thread {

        private DataInputStream in;
        private DataOutputStream out;

        public ClientHandler(Socket client, final int maxUsers, final int users) {
            if (users + 1 > maxUsers)
                throw new IllegalArgumentException(maxUsers + " is smaller than " + users);
            try {
                in = new DataInputStream(client.getInputStream());
                out = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String s) {
            try {
                out.writeUTF(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            String input;
            while (true) {
                try {
                    input = in.readUTF();
                    System.out.println(input);
                    if (input.equals(".")) {
                        System.out.println("Disconnect client");
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }

            }
        }

    }

}
