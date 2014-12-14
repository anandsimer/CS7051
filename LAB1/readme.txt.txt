Server side java programs:

1. Service.java - Listen to thread, and take care of thread pool. Initiates a new thread for every new client.
2. ServiceHandler.java - Handles the client messages exchange.

Client side java programs:

1. Client.java - Client works as per specifications of the Lab 1.

You can execute the Client.java, and type 'HELO' to see the expected output.
Type 'KILL_SERVICE' to kill all threads, server as well as client threads.
Type any random message, to see server responding to you back.