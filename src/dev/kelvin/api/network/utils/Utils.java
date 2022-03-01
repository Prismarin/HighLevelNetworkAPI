package dev.kelvin.api.network.utils;

import dev.beni.utils.SocketDictStringsOnly;
import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.network.NetworkParticipant;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Utils {

    public static void workWithReceivedData(HighLevelNetworkAPI hln, SocketDictStringsOnly dict) {
        int argLength = Integer.parseInt(dict.getValue("s"));
        String[] args = new String[argLength];
        for (int i = 0; i < argLength; i++) {
            args[i] = dict.getValue("a" + i);
        }
        try {
            hln.call(dict.getValue("m"), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SocketDictStringsOnly buildFromMethodNameAndArgs(String methodName, String[] args) {
        SocketDictStringsOnly sendDict = new SocketDictStringsOnly("m", methodName);
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

        SocketDictStringsOnly receiveDict = SocketDictStringsOnly.fromString(msg);
        Utils.workWithReceivedData(hln, receiveDict);
    }

}
