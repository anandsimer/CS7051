/*
    Service.java - Listen to thread, and take care of thread pool. Initiates a new thread 
    for every new client.
*/
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.io.*;

/**
 *
 * @author anands@tcd.ie, NDS course, Student ID 14303767
 * Trinity College Dublin
 */

public class Service {

    /* ThreadPool with max 10 'ACTIVE' clients, Our client request is too small, and they 
     are involved in any prolong active transaction hence, there are less chances we see a scenario 
     where new client request will be rejected. 
       
     I tried to run multiple clients to see the rejection of new client request, from thread dump 
     analysis, what I found threads goes to standy state, as soon as the messages are exchanged, hence 
     thread pool executor does not count them in as Active threads.
     */
    private static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    // open socket on the server
    private ServerSocket sock;
    // accept the connection on above socket
    private Socket socket;

    // default constructor
    Service() {
    }

    // Overloaded Constructor, initializing the port fot class.
    public Service(int port) throws IOException {
        sock = new ServerSocket(port);
    }

    public static void main(String[] args) throws IOException {
        if (args.length<1) {
            System.err
                    .println("Usage: java Service <port_number> \n");
        } else {
            int port = Integer.parseInt(args[0]);
            Service server = new Service(port);
            server.runService();
        }
    }

    // Function which enabled our service to listen on port,
    // and accept connections.

    public void runService() {
        try {
            while (true) {
                if (executor.getActiveCount() < 11) {
                    socket = sock.accept();
                    executor.submit(new ServiceHandler(socket));
                } else {
                    System.err.println("Maximum number of clients exceeded");
                    socket.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void killAll() throws IOException {
        executor.shutdown();
        socket.close();
        System.exit(1);
    }
}
