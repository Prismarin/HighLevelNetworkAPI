package dev.kelvin.api;

import dev.kelvin.api.network.Client;
import dev.kelvin.api.network.Network;
import dev.kelvin.api.network.Server;
import dev.kelvin.api.network.events.IOnConnectionClosed;
import dev.kelvin.api.network.events.IOnConnectionFailed;
import dev.kelvin.api.network.events.IOnConnectionSucceeded;
import dev.kelvin.api.network.exceptions.MissingArgumentException;
import dev.kelvin.api.network.exceptions.TooManyArgumentsException;

import java.lang.reflect.InvocationTargetException;
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

    public void call(String methodName, String... args) throws InvocationTargetException, IllegalAccessException {
        Method methodToRun = getMethodByName(methodName);
        if (methodToRun != null) {
            int argNumber = methodToRun.getAnnotation(Remote.class).value();
            if (argNumber < args.length)
                throw new TooManyArgumentsException(methodName, argNumber, args.length);
            else if (argNumber > args.length)
                throw new MissingArgumentException(methodName, args.length, argNumber);
            else if (argNumber > 12)
                throw new RuntimeException("Remote methods can only have a maximum of 12 arguments");
            else {
                switch (argNumber) {
                    case 0:
                        methodToRun.invoke(netObject);
                        break;
                    case 1:
                        methodToRun.invoke(netObject, args[0]);
                        break;
                    case 2:
                        methodToRun.invoke(netObject, args[0], args[1]);
                        break;
                    case 3:
                        methodToRun.invoke(netObject, args[0], args[1], args[2]);
                        break;
                    case 4:
                        methodToRun.invoke(netObject, args[0], args[1], args[2], args[3]);
                        break;
                    case 5:
                        methodToRun.invoke(netObject, args[0], args[1], args[2], args[3], args[4]);
                        break;
                    case 6:
                        methodToRun.invoke(netObject, args[0], args[1], args[2], args[3], args[4], args[5]);
                        break;
                    case 7:
                        methodToRun.invoke(netObject, args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
                        break;
                    case 8:
                        methodToRun.invoke(netObject, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
                        break;
                    case 9:
                        methodToRun.invoke(netObject, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8]);
                        break;
                    case 10:
                        methodToRun.invoke(netObject, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9]);
                        break;
                    case 11:
                        methodToRun.invoke(netObject, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10]);
                        break;
                    case 12:
                        methodToRun.invoke(netObject, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10], args[11]);
                        break;
                    default:
                        System.err.println("HighLevelNetworkAPI unknown error!");
                }
            }
        }
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
