package dev.kelvin.api;

import dev.kelvin.api.network.Client;
import dev.kelvin.api.network.NetworkParticipant;
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
    protected NetworkParticipant net;

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
     *
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

    public void createServer(int port) {
        if (net == null) {
            net = new Server(netObject, this, port);
            net.start();
        } else
            System.err.println("A " + net.getClass().getName() + " is already created on this HighLevelNetwork");
    }

    public void createClient(String address, int port) {
        if (net == null) {
            net = new Client(netObject, this, address, port);
            net.start();
        } else
            System.err.println("A " + net.getClass().getName() + " is already created on this HighLevelNetwork");
    }

    /**
     *
     * <h1>rcu</h1>
     *
     * <h3>
     *     udp method | remote call udp | send unreliable <br>
     *
     *     performs default call for the current {@link NetworkParticipant} <br>
     *     <br>
     *     default calls:
     *     <ul>
     *         <li>{@link Client} 1: calls the server equals rcu_id(1, ..., ...)</li>
     *         <li>{@link Server} 0: calls all clients equals rcu_id(0, ..., ...)</li>
     *     </ul>
     * </h3>
     *
     * @param methodName name of the method on the called {@link NetworkParticipant}
     * @param args parameters of the method on the called {@link NetworkParticipant}
     *
     * @throws NullPointerException if no {@link NetworkParticipant} has been created
     */
    public void rcu(String methodName, String...args) {
        net.rcu(methodName, args);
    }

    /**
     *
     * <h1>rcu_id</h1>
     *
     * <h3>
     *     udp method | remote call udp | send unreliable
     * </h3>
     *
     * @param userId case 0 send to all, case 1 send to server, case n send to specific peer
     * @param methodName name of the method on the called {@link NetworkParticipant}
     * @param args parameters of the method on the called {@link NetworkParticipant}
     *
     * @throws NullPointerException if no {@link NetworkParticipant} has been created
     */
    public void rcu_id(int userId, String methodName, String... args) {
        net.rcu_id(userId, methodName, args);
    }

    /**
     *
     * <h1>rct</h1>
     *
     * <h3>
     *     tcp method | remote call tcp | send reliable <br>
     *
     *     performs default call for the current {@link NetworkParticipant} <br>
     *
     *     <br>
     *     default calls:
     *     <ul>
     *           <li>{@link Client} 1: calls the server equals rcu_id(1, ..., ...)</li>
     *           <li>{@link Server} 0: calls all clients equals rcu_id(0, ..., ...)</li>
     *     </ul>
     * </h3>
     *
     * @param methodName name of the method on the called {@link NetworkParticipant}
     * @param args parameters of the method on the called {@link NetworkParticipant}
     *
     * @throws NullPointerException if no {@link NetworkParticipant} has been created
     */
    public void rct(String methodName, String...args) {
        net.rct(methodName, args);
    }

    /**
     *
     * <h1>rct_id</h1>
     *
     * <h3>
     *     tcp method | remote call tcp | send reliable
     * </h3>
     *
     * @param userId case 0 send to all, case 1 send to server, case n send to specific peer
     * @param methodName name of called method on the other side
     * @param args parameters for called methods on other side
     *
     * @throws NullPointerException if no {@link NetworkParticipant} has been created
     */
    public void rct_id(int userId, String methodName, String... args) {
        net.rct_id(userId, methodName, args);
    }

    /**
     *
     * used for clients when connected to the server - argument for onConnectionSucceeded = 0
     * used for server when a client just connected - argument for onConnectionSucceeded = userId or given id from a database
     *
     * @param onConnectionSucceeded method reference
     */
    public void addOnConnectionSucceeded(IOnConnectionSucceeded onConnectionSucceeded) {
        onConnectionSucceededList.add(onConnectionSucceeded);
    }

    /**
     *
     * called when a new client has connected to the server
     *
     * @param userId the userId from the new user
     */
    public void triggerConnectionSucceeded(int userId) {
        for (IOnConnectionSucceeded e : onConnectionSucceededList)
            e.onConnectionSucceeded(userId);
    }

    /**
     *
     * only used for clients, when failed to connect to given host and port
     *
     * @param onConnectionFailed method reference
     */
    public void addOnConnectionFailed(IOnConnectionFailed onConnectionFailed) {
        onConnectionFailedList.add(onConnectionFailed);
    }

    /**
     *
     * called when the client tried to connect to a host which refused the client
     */
    public void triggerConnectionFailed() {
        for (IOnConnectionFailed e : onConnectionFailedList)
            e.onConnectionFailed();
    }

    /**
     *
     * used for clients when the server has closed the connection or crashed
     * used for server when a client has disconnected
     *
     * @param onConnectionClosed method reference
     */
    public void addOnConnectionClosed(IOnConnectionClosed onConnectionClosed) {
        onConnectionClosedList.add(onConnectionClosed);
    }

    public void triggerConnectionClosed(int userId) {
        for (IOnConnectionClosed e : onConnectionClosedList)
            e.onConnectionClosed(userId);
    }

    /**
     *
     * @param methodName the name of the method that will be called
     * @param args the arguments for the method
     * @throws InvocationTargetException when the method can't be invoked
     * @throws IllegalAccessException when the method that is called can't be accessed because it is not public
     */
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
                    case 0 -> methodToRun.invoke(netObject);
                    case 1 -> methodToRun.invoke(netObject, args[0]);
                    case 2 -> methodToRun.invoke(netObject, args[0], args[1]);
                    case 3 -> methodToRun.invoke(netObject, args[0], args[1], args[2]);
                    case 4 -> methodToRun.invoke(netObject, args[0], args[1], args[2], args[3]);
                    case 5 -> methodToRun.invoke(netObject, args[0], args[1], args[2], args[3], args[4]);
                    case 6 -> methodToRun.invoke(netObject, args[0], args[1], args[2], args[3], args[4], args[5]);
                    case 7 -> methodToRun.invoke(netObject, args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
                    case 8 -> methodToRun.invoke(netObject, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
                    case 9 -> methodToRun.invoke(netObject, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8]);
                    case 10 -> methodToRun.invoke(netObject, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9]);
                    case 11 -> methodToRun.invoke(netObject, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10]);
                    case 12 -> methodToRun.invoke(netObject, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10], args[11]);
                    default -> System.err.println("HighLevelNetworkAPI unknown error!");
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

    /**
     *
     * client method to disconnect from the server
     *
     * @throws NullPointerException if the method is called when {@link HighLevelNetworkAPI} is used as server
     */
    public void disconnect() {
        if (net instanceof Client) {
            rct_id(1, "//dis");
            net.stop();
        } else
            throw new NullPointerException("HighLevelNetworkAPI is used as Server!");
    }

}
