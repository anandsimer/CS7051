MultiThreaded Chatroom Program 
==============================

User could join the chatroom (more than one at a time, as of now I have made two chatrooms namely 'Trinity' and 'NDS'), users will get to know about people who are entering into the chatroom and leaving the chatroom.  A user will not get the messages of chatroom of whom he/she is not part of.

There are Two main Java classes,

1) ChatServer.java
   ---------------
   
   This class accepts the connections from the users, makes a thread of each user and run it through the thread pool executor (max size 10). A short note for thread pool executor:
   
   An Important note

   ThreadPool with max 10 'ACTIVE' clients, Our client request is too small, and they are not involved in any prolong active transaction hence, there are less chances we see a scenario where new client request will be rejected.

   I tried to run multiple clients to see the rejection of new client request, from thread dump analysis, what I found threads goes to standby state, as soon as the messages are exchanged, hence thread pool executor does not count them in as Active threads.   
   
2) ChatClient.java
   ---------------

   This class needs to run by the user to join the chat room, this class makes socket connection to the Service class and takes the input of the user, and send it to the ChatServer program.
	

3)  ChatServerListener.java 
    -----------------------
   
    The chat client thread. This client thread opens the input and the output streams for a particular client, ask the client's name, informs all the clients connected to the server about the fact that a new client has joined the chat room, and as long as it receive data, echos that data back to all other clients. When a client leaves the chat room this thread informs also all the clients about that and terminates.
	 
	 
   