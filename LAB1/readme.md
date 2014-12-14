Server side java programs:

1. Service.java - Listen to thread, and take care of thread pool. Initiates a new thread for every new client.

Usage: java -classpath . Service <port_number>
for example: java -classpath . Service 7001

2. ServiceHandler.java - Handles the client messages exchange.

Client side java programs:

1. Client.java - Client works as per specifications of the Lab 1.

Usage: java -classpath . Client <port_number>
for example: java -classpath . Client 7001

You can execute the Client.java, and type 'HELO' to see the expected output.
Type 'KILL_SERVICE' to kill all threads, server as well as client threads.
Type any random message, to see server responding to you back.

An Important note
<p style="color:#F00;">ThreadPool with max 10 'ACTIVE' clients, Our client request is too small, and they 
     are not involved in any prolong active transaction hence, there are less chances we see a scenario 
     where new client request will be rejected. 
       </p>
<p style="color:#F00;">I tried to run multiple clients to see the rejection of new client request, from thread dump 
     analysis, what I found threads goes to standby state, as soon as the messages are exchanged, hence 
     thread pool executor does not count them in as Active threads.
</p>