package dev.kelvin.api.network.participants.mass;

import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.network.NetworkParticipant;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * on connection failed
 * on connection succeeded
 */
public class MassClient extends NetworkParticipant {

    protected DatagramSocket socket;

    protected InetAddress address;
    protected int port;

    public MassClient(Object object, HighLevelNetworkAPI hln, String address, int port) {
        super(object, hln);

        this.port = port;

        try {
            socket = new DatagramSocket();
            this.address = InetAddress.getByName(address);

            hln.triggerConnectionSucceeded(0);
        } catch (SocketException | UnknownHostException e) {
            hln.triggerConnectionFailed();
            e.printStackTrace();
        }
    }

    @Override
    public void rcu(String methodName, String... args) {

    }

    @Override
    public void rcu_id(int uuid, String methodName, String... args) {

    }

    @Override
    public void rct(String methodName, String... args) {

    }

    @Override
    public void rct_id(int uuid, String methodName, String... args) {

    }

    @Override
    protected void listenUdp() {

    }

    @Override
    protected void listenTcp() {

    }

    public InetAddress getAddress() {
        return address;
    }

}
