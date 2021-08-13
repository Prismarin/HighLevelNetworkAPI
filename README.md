# HighLevelNetworkAPI
The HighLevelNetworkAPI for Java is currently in development.
The idea is, to make it simple to make client - server applications

Our goal is, to make it possible for the Programmer using the API to make their programm networking capable by just using the HighLevelNetwork class and the @Remote annotation

The HighLevelNetwork object gets an object attatched wich is the object containing all the methods for networking
The whole system is a remote call system, one networkuser can tell the other one to call a method
To assure security, we implemented the @Remote annotation, only these can be called remotly, when a not annotated method is called it will throw an exception

The HighLevelNetworkAPI uses the UDP and TCP protocolls

This system is inspired by Godots Networking API
