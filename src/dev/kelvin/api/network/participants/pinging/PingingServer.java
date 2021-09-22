package dev.kelvin.api.network.participants.pinging;

import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.network.info.ConnectionInfo;
import dev.kelvin.api.network.info.PingingConnectionInfo;
import dev.kelvin.api.network.participants.normal.Server;

import java.io.IOException;

/**
 * @since 1.1
 * @version 1.1
 */
public class PingingServer extends Server {

    protected int pingFrequency, maxUnRespondedPings;

    public PingingServer(Object object, HighLevelNetworkAPI hln, int port, int maxUsers, int pingFrequency, int maxUnRespondedPings) {
        super(object, hln, port, maxUsers);
        this.pingFrequency = pingFrequency;
        this.maxUnRespondedPings = maxUnRespondedPings;
    }

    @Override
    protected void listenTcp() {
        while (running) {
            ConnectionInfo newClient = null;
            boolean use = true;
            int userId = generateUniqueUserId();
            try {
                newClient = new PingingConnectionInfo(hln, userId, tcpSocket.accept(), pingFrequency, maxUnRespondedPings);
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

}
