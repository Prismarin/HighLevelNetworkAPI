package dev.kelvin.api.network.participants.pinging;

import dev.beni.utils.SocketDict;
import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.network.participants.normal.Client;
import dev.kelvin.api.network.utils.Utils;

import java.io.EOFException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @since 1.1
 * @version 1.1
 */
public class PingingClient extends Client {

    protected int unRespondedPings;
    protected Timer timer;

    public PingingClient(Object object, HighLevelNetworkAPI hln, String address, int port, int maxUnRespondedPings, int pingFrequency) {
        super(object, hln, address, port);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (unRespondedPings > maxUnRespondedPings) {
                    hln.triggerConnectionClosed(0);
                    timer.cancel();
                }
                addPing();
            }
        }, pingFrequency, pingFrequency);
    }

    @Override
    protected void listenTcp() {
        if (in == null)
            return;
        while (running) {
            try {
                String input = in.readUTF();
                SocketDict receiveDict = SocketDict.fromString(input);
                if (receiveDict.get("m").equals("//ping")) {
                    rct("//ping");
                    unRespondedPings = 0;
                } else
                    Utils.workWithReceivedData(hln, receiveDict);
            } catch (EOFException ignored) {
                hln.triggerConnectionClosed(0);
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void addPing() {
        unRespondedPings++;
    }

}
