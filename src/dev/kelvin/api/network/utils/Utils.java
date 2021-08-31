package dev.kelvin.api.network.utils;

import dev.beni.utils.SocketDict;
import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.network.NetworkParticipant;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Utils {

    public static void workWithReceivedData(HighLevelNetworkAPI hln, SocketDict dict) {
        int argLength = Integer.parseInt(dict.get("s"));
        String[] args = new String[argLength];
        for (int i = 0; i < argLength; i++) {
            args[i] = dict.get("a" + i);
        }
        try {
            hln.call(dict.get("m"), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SocketDict buildFromMethodNameAndArgs(String methodName, String[] args) {
        SocketDict sendDict = new SocketDict("m", methodName);
        //sendDict.add("m", methodName);
        sendDict.add("s", String.valueOf(args.length));     //s == size
        for (int i = 0; i < args.length; i++) {
            sendDict.add("a" + i, args[i]);     //a == arg
        }
        return sendDict;
    }

    public static void defaultListenUDP(DatagramSocket udpSocket, HighLevelNetworkAPI hln) {
        byte[] data = new byte[8192];

        DatagramPacket packet = new DatagramPacket(data, data.length);
        try {
            udpSocket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Not working on with this msg!");
            return;
        }

        String msg = new String(data);
        msg = msg.substring(0, msg.indexOf(NetworkParticipant.endString));

        SocketDict receiveDict = SocketDict.fromString(msg);
        Utils.workWithReceivedData(hln, receiveDict);
    }

}
