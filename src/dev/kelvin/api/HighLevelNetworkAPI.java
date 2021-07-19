package dev.kelvin.api;

import dev.kelvin.api.network.Client;
import dev.kelvin.api.network.Network;
import dev.kelvin.api.network.Server;
import dev.kelvin.api.network.events.IOnConnectionClosed;
import dev.kelvin.api.network.events.IOnConnectionFailed;
import dev.kelvin.api.network.events.IOnConnectionSucceeded;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class HighLevelNetworkAPI {

    protected Object netObject;
    protected Network net;

    protected Method[] remoteMethods;

    protected ArrayList<IOnConnectionSucceeded> onConnectionSucceededList;
    protected ArrayList<IOnConnectionClosed> onConnectionClosedList;
    protected ArrayList<IOnConnectionFailed> onConnectionFailedList;

    /**
     *
     * @param object is the object in which there are all the remote methods
     */
    public HighLevelNetworkAPI(Object object) {
        this.netObject = object;

        getRemoteMethods();

        onConnectionSucceededList = new ArrayList<>();
        onConnectionClosedList = new ArrayList<>();
        onConnectionFailedList = new ArrayList<>();
    }

    /**
     * getRemoteMethods assumes that netObject != null
     */
    private void getRemoteMethods() {
        Method[] allMethods = netObject.getClass().getDeclaredMethods();
        ArrayList<Method> remoteMethods = new ArrayList<>();
        for (Method method : allMethods) {
            if (method.isAnnotationPresent(Remote.class)) {
                remoteMethods.add(method);
            }
        }
        this.remoteMethods = new Method[remoteMethods.size()];
        for (int i = 0; i < this.remoteMethods.length; i++) {
            this.remoteMethods[i] = remoteMethods.get(i);
        }
    }

    public void createServer() {
        if (net == null)
            net = new Client(netObject);
    }

    public void createClient() {
        if (net == null)
            net = new Server(netObject);
    }

    /**
     *
     * udp method
     *
     * @param peerId case 0 send to all, case 1 send to server, case n send to specific peer
     * @param methodName name of called method on the other side
     * @param strings parameters for called methods on other side
     */
    public void send_unreliable(long peerId, String methodName, String... strings) {}

    /**
     *
     * tcp method
     *
     * @param peerId case 0 send to all, case 1 send to server, case n send to specific peer
     * @param methodName name of called method on the other side
     * @param strings parameters for called methods on other side
     */
    public void send(long peerId, String methodName, String... strings) {}

    /**
     *
     * used for clients when connected to the server - argument for onConnectionSucceeded = 0
     * used for server when a client just connected - argument for onConnectionSucceeded = peerId or given id from a database
     *
     * @param onConnectionSucceeded method reference
     */
    public void addOnConnectionSucceeded(IOnConnectionSucceeded onConnectionSucceeded) {
        onConnectionSucceededList.add(onConnectionSucceeded);
    }

    /**
     *
     * only used for clients, when failed to connect to given host and port
     *
     * @param onConnectionFailed method reference
     */
    public void addOnConnectionFailed(IOnConnectionFailed onConnectionFailed) {
        if (net instanceof Client)
            onConnectionFailedList.add(onConnectionFailed);
    }

    /**
     *
     * used for clients when the server has closed the connection or crashed
     * used for server when a client has disconnected and does not respond to pings anymore
     *
     * @param onConnectionClosed method reference
     */
    public void addOnConnectionClosed(IOnConnectionClosed onConnectionClosed) {
        onConnectionClosedList.add(onConnectionClosed);
    }

    public void call(String methodName, String... args) {
        Method methodToRun = getMethodByName(methodName);
    }

    /**
     *
     * @param methodName is the name of the method which is searched
     * @return the method-object || null if !remoteMethods.contains(method -> methodName)
     */
    private Method getMethodByName(String methodName) {
        for (Method method : remoteMethods) {
            if (method.getName().equals(methodName))
                return method;
        }
        return null;
    }

}
