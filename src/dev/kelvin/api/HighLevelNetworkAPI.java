package dev.kelvin.api;

import dev.kelvin.api.network.NetworkParticipant;
import dev.kelvin.api.network.annotaions.NetworkParticipantCreator;
import dev.kelvin.api.network.events.IOnConnectionClosed;
import dev.kelvin.api.network.events.IOnConnectionFailed;
import dev.kelvin.api.network.events.IOnConnectionSucceeded;
import dev.kelvin.api.network.exceptions.IllegalMethodNameException;
import dev.kelvin.api.network.exceptions.MissingArgumentException;
import dev.kelvin.api.network.exceptions.TooManyArgumentsException;
import dev.kelvin.api.network.participants.Client;
import dev.kelvin.api.network.participants.Server;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
     * <h1>< init ></h1>
     *
     * <h3>
     *     Creates the new {@link HighLevelNetworkAPI}
     * </h3>
     *
     * @param object the object on which remote-methods will be called
     */
    public HighLevelNetworkAPI(@NotNull Object object) {
        this.netObject = object;

        fetchRemoteMethods();

        onConnectionSucceededList = new ArrayList<>();
        onConnectionClosedList = new ArrayList<>();
        onConnectionFailedList = new ArrayList<>();
    }

    /**
     *
     * <h1>Fetch Remote Methods</h1>
     *
     * <h3>
     *     Called when a new networking object is given. <br>
     *     The method asserts that the netObject != null
     * </h3>
     * @throws AssertionError when netObject == null
     */
    private void fetchRemoteMethods() {
        assert (netObject != null);
        Method[] allMethods = netObject.getClass().getDeclaredMethods();
        ArrayList<Method> remoteMethods = new ArrayList<>();
        for (Method method : allMethods) {
            if (method.isAnnotationPresent(Remote.class)) {
                if (remoteMethods.stream().filter(method1 -> method1.getName().equals(method.getName())).toList().size() != 1)
                    remoteMethods.add(method);
                else
                    throw new IllegalMethodNameException(method.getName());
            }
        }
        this.remoteMethods = new Method[remoteMethods.size()];
        for (int i = 0; i < this.remoteMethods.length; i++) {
            this.remoteMethods[i] = remoteMethods.get(i);
        }
    }

    /**
     *
     * <h4>@{@link NetworkParticipantCreator}</h4>
     * <h1>Create Server</h1>
     *
     * <h3>
     *     Creating a {@link Server} object on this {@link HighLevelNetworkAPI} <br>
     *     With the {@link Server} object following network operations are available:
     *     <ul>
     *         <li>Remote Call Udp | rcu</li>
     *         <li>Remote Call Udp by Id | rcu_id</li>
     *         <li>Remote Call Tcp | rct</li>
     *         <li>Remote Call Tcp by Id | rct_id</li>
     *     </ul>
     *     Following Events get called:
     *     <ul>
     *         <li>{@link IOnConnectionSucceeded}</li>
     *         <li>{@link IOnConnectionClosed}</li>
     *     </ul>
     *     Make sure not to recall this method without closing the current {@link NetworkParticipant}. While
     *     there is an active {@link Server}, no other {@link NetworkParticipant} can be created. <br>
     *     Also creating multiple {@link NetworkParticipant} in the same session could lead to small
     *     resource-leaks. <br>
     *     For big servers it is recommended to exit after closing and restart the application for reopening. <br>
     *     <br>
     * </h3>
     *
     * @param port the port on which the server should run
     */
    @NetworkParticipantCreator
    public void createServer(int port) {
        if (net == null) {
            net = new Server(netObject, this, port);
            net.start();
        } else
            System.err.println("A " + net.getClass().getName() + " is already created on this HighLevelNetwork");
    }

    /**
     *
     * <h4>@{@link NetworkParticipantCreator}</h4>
     * <h1>Create Client</h1>
     *
     * <h3>
     *     Creating a {@link Client} object on this {@link HighLevelNetworkAPI} <br>
     *     With the {@link Client} object all network operations given by the {@link HighLevelNetworkAPI}
     *     are available. <br>
     *     Make sure not to recall this method without closing the current {@link NetworkParticipant}. While there
     *     is an active {@link Client}, no other {@link NetworkParticipant} can be created. <br>
     *     Also creating multiple {@link NetworkParticipant} in the same session could lead to small
     *     resource-leaks.
     * </h3>
     *
     * @param address is the ip-address to which the client should connect
     * @param port is the port on the computer on the host
     */
    @NetworkParticipantCreator
    public void createClient(String address, int port) {
        if (net == null) {
            net = new Client(netObject, this, address, port);
            net.start();
        } else
            System.err.println("A " + net.getClass().getName() + " is already created on this HighLevelNetwork");
    }

    /**
     *
     * <h1>Remote Call Udp</h1>
     *
     * <h3>
     *     udp method | remote call udp | send unreliable <br>
     *
     *     performs default call for the current {@link NetworkParticipant} <br>
     *     <br>
     *     default calls:
     *     <ul>
     *         <li>{@link Server} 0: calls all clients equals rcu_id(0, ..., ...)</li>
     *         <li>{@link Client} 1: calls the server equals rcu_id(1, ..., ...)</li>
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
     * <h1>Remote Call Udp by Id</h1>
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
     * <h1>Remote Call Tcp</h1>
     *
     * <h3>
     *     tcp method | remote call tcp | send reliable <br>
     *
     *     performs default call for the current {@link NetworkParticipant} <br>
     *
     *     <br>
     *     default calls:
     *     <ul>
     *           <li>{@link Server} 0: calls all clients equals rcu_id(0, ..., ...)</li>
     *           <li>{@link Client} 1: calls the server equals rcu_id(1, ..., ...)</li>
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
     * <h1>Remote Call Tcp by Id</h1>
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
     * <h1>Add {@link IOnConnectionSucceeded}</h1>
     *
     * <h3>
     *     This method adds the given {@link IOnConnectionSucceeded} to the eventhandler. <br>
     *     <br>
     *     {@link IOnConnectionSucceeded} is used by:
     *     <ul>
     *         <li>{@link Client}</li>
     *         <li>{@link Server}</li>
     *     </ul>
     *
     *     Method is called for {@link Client} when the {@link Client} just connected successfully to a {@link Server}.
     *     The argument given by the method will be 0 and is meant to be ignored. <br>
     *     <br>
     *     Method is called for {@link Server} when a {@link Client} just has connected successfully. The argument will
     *     be its given userId by the {@link HighLevelNetworkAPI}. The userId is only for the session the {@link Client}
     *     is connected to the {@link Server}. As the {@link Client} disconnects, the {@link HighLevelNetworkAPI} will
     *     lose the userId and attach it later to another {@link Client}. When a {@link Client} reconnects he will get
     *     a new userId. <br>
     *     <br>
     * </h3>
     *
     * @param onConnectionSucceeded method reference
     */
    public void addOnConnectionSucceeded(IOnConnectionSucceeded onConnectionSucceeded) {
        onConnectionSucceededList.add(onConnectionSucceeded);
    }

    /**
     *
     * <h1>Trigger {@link IOnConnectionSucceeded}</h1>
     *
     * <h3>
     *     Calls all the added {@link IOnConnectionSucceeded} by addOnConnectionSucceeded. <br>
     *     <br>
     *     Called by the {@link HighLevelNetworkAPI} in following cases:
     *     <ul>
     *         <li>
     *             {@link Client}: <br>
     *             When the {@link Client} just successfully connected to a {@link Server}; userId = 0
     *         </li>
     *         <li>
     *             {@link Server}: <br>
     *             When a {@link Client} just connected to the {@link Server}; userId = new userId given by {@link HighLevelNetworkAPI}
     *         </li>
     *     </ul>
     * </h3>
     *
     * @param userId the userId described earlier.
     */
    public void triggerConnectionSucceeded(int userId) {
        for (IOnConnectionSucceeded e : onConnectionSucceededList)
            e.onConnectionSucceeded(userId);
    }

    /**
     *
     * <h1>Add {@link IOnConnectionFailed}</h1>
     *
     * <h3>
     *     {@link IOnConnectionFailed} is used by:
     *
     *     <ul>
     *         <li>{@link Client}</li>
     *     </ul>
     *
     *     Method is called for {@link Client} when the {@link Client} failed to connect to a {@link Server} on
     *     given host and port. <br>
     *     <br>
     * </h3>
     *
     * @param onConnectionFailed method reference
     */
    public void addOnConnectionFailed(IOnConnectionFailed onConnectionFailed) {
        onConnectionFailedList.add(onConnectionFailed);
    }

    /**
     *
     * <h1>Trigger {@link IOnConnectionFailed}</h1>
     *
     * <h3>
     *     Calls the added {@link IOnConnectionFailed} by addOnConnectionFailed. <br>
     *     <br>
     *     Called by the {@link HighLevelNetworkAPI} in following cases:
     *     <ul>
     *         <li>
     *             {@link Client}:<br>
     *             When the {@link Client} failed to connect to a {@link Server}
     *         </li>
     *     </ul>
     * </h3>
     *
     * called when the client tried to connect to a host which refused the client
     */
    public void triggerConnectionFailed() {
        for (IOnConnectionFailed e : onConnectionFailedList)
            e.onConnectionFailed();
    }

    /**
     *
     * <h1>Add {@link IOnConnectionClosed}</h1>
     *
     * <h3>
     *     {@link IOnConnectionClosed} is used by:
     *     <ul>
     *         <li>{@link Client}</li>
     *         <li>{@link Server}</li>
     *     </ul>
     *     Method is called for {@link Client} when the {@link Server} closed the connection without that the {@link Client}
     *     requested a disconnection <br>
     *     <br>
     *     Method is called for {@link Server} when a {@link Client} disconnected or lost the connection <br>
     *     <br>
     * </h3>
     *
     * @param onConnectionClosed method reference
     */
    public void addOnConnectionClosed(IOnConnectionClosed onConnectionClosed) {
        onConnectionClosedList.add(onConnectionClosed);
    }

    /**
     *
     * <h1>Trigger {@link IOnConnectionClosed}</h1>
     *
     * <h3>
     *     Called by the {@link HighLevelNetworkAPI} in following cases:
     *     <ul>
     *         <li>
     *             {@link Client}:
     *             When the {@link Server} closed the connection without signaling to the {@link Client} that the
     *             connection will be closed; userId = 0
     *         </li>
     *         <li>
     *             {@link Server}:
     *             When a {@link Client} disconnects or loses the connection to the {@link Server}; userId = the
     *             userId of the disconnected {@link Client}
     *         </li>
     *     </ul>
     * </h3>
     *
     * @param userId the userId described earlier
     */
    public void triggerConnectionClosed(int userId) {
        for (IOnConnectionClosed e : onConnectionClosedList)
            e.onConnectionClosed(userId);
    }

    /**
     *
     * <h1>Call</h1>
     *
     * <h3>
     *     Calls the {@link Method} by the name in the netObject with the given String[] as arguments. <br>
     *     <br>
     *     The call method can only call Methods annotated with @{@link Remote}. This is because the method-name
     *     gets searched in the array of @{@link Remote} methods. This is a security layer, so that a {@link NetworkParticipant}
     *     isn't capable of calling any method on an other {@link NetworkParticipant}. <br>
     *     <br>
     * </h3>
     *
     * @param methodName the name of the method that will be called
     * @param args the arguments for the method
     * @throws InvocationTargetException when the method can't be invoked
     * @throws IllegalAccessException when the method that is called can't be accessed because it is not public
     */
    public void call(String methodName, String... args) throws InvocationTargetException, IllegalAccessException {
        if (netObject == null)
            return;
        Method methodToRun = getMethodByName(methodName);
        if (methodToRun != null) {
            int argNumber = methodToRun.getAnnotation(Remote.class).value();
            if (argNumber == -1) {
                if (args.length <= 0) {
                    throw new MissingArgumentException(methodName, 0, 1);
                } else {
                    methodToRun.invoke(netObject, (Object) args);
                }
            } else if (argNumber < args.length)
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
     * <h1>@{@link Nullable} Get Method by Name</h1>
     *
     * <h3>
     *     Searches and returns the remote-method by the given name. <br>
     *     <br>
     * </h3>
     *
     * @param methodName is the name of the method which is searched
     * @return the method-object || null if !remoteMethods.contains(method -> methodName)
     */
    private @Nullable Method getMethodByName(String methodName) {
        for (Method method : remoteMethods) {
            if (method.getName().equals(methodName))
                return method;
        }
        return null;
    }

    /**
     *
     * <h1>Disconnect</h1>
     *
     * <h3>
     *     Usable in following cases:
     *     <ul>
     *         <li>
     *             {@link Client}: <br>
     *             The Client can message the {@link Server} that he is going to close the connection. <br>
     *             <br>
     *         </li>
     *     </ul>
     * </h3>
     *
     * @throws NullPointerException if the method is called when {@link HighLevelNetworkAPI} is not used as {@link Client}
     */
    public void disconnect() {
        if (net instanceof Client) {
            rct_id(1, "//dis");
            net.stop();
        } else
            throw new NullPointerException("HighLevelNetworkAPI is used as Server!");
    }

}
