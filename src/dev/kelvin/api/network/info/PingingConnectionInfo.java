package dev.kelvin.api.network.info;

import dev.beni.utils.SocketDict;
import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.network.utils.Utils;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @since 1.1
 * @version 1.1
 */
public class PingingConnectionInfo extends ConnectionInfo {

    public final int pingFrequency, maxUnRespondedPings;
    protected int unRespondedPings;
    protected Timer timer;

    protected boolean running = true;

    public static final SocketDict pingDict = new SocketDict("m", "//ping");

    public PingingConnectionInfo(HighLevelNetworkAPI hln, int userId, Socket tcpSocket, int pingFrequency, int maxUnRespondedPings) throws IOException {
        super(hln, userId, tcpSocket);
        this.pingFrequency = pingFrequency;
        this.maxUnRespondedPings = maxUnRespondedPings;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (unRespondedPings > maxUnRespondedPings) {
                    hln.triggerConnectionClosed(userId);
                    running = false;
                    timer.cancel();
                    return;
                }
                try {
                    boolean sent = send(pingDict.toString());
                    if (sent)
                        unRespondedPings++;
                    else {
                        running = false;
                        hln.triggerConnectionClosed(userId);
                        timer.cancel();
                    }
                } catch (IOException e) {
                    running = false;
                    hln.triggerConnectionSucceeded(userId);
                    timer.cancel();
                }
            }
        }, pingFrequency, pingFrequency);
    }

    @Override
    public void run() {
        while (running) {
            try {
                String input = in.readUTF();
                SocketDict dict = SocketDict.fromString(input);
                if (dict.get("m").equals("//ping")) {
                    unRespondedPings = 0;
                }else if (dict.get("m").equals("//dis")) {
                    tcpSocket.close();
                    hln.triggerConnectionClosed(userId);
                    break;
                }
                Utils.workWithReceivedData(hln, dict);
            } catch (EOFException ignored) {
                System.err.println("Client just disconnected without telling the server!");
                break;
            } catch (IOException e) {
                try {
                    tcpSocket.close();
                } catch (IOException ignored) {}
                break;
            }
        }
    }

}
