import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.io.*;

/**
 *
 * @author anands@tcd.ie
 */
public class Service {

    // ThreadPool with max 10 clients
    private static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    private ServerSocket sock;
    private Socket socket;

    public Service(int port) throws IOException {
        sock = new ServerSocket(port);
    }

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        Service server = new Service(port);
        server.runService();
    }

    // Function which enabled our service to listen on port,
    // and accept connections.
    
    public void runService() {
        try {
            while (true) {
                if (executor.getActiveCount() < 10) {
                    socket = sock.accept();
                    Service.executor.execute(new ServiceHandler(socket));
                } else {
                    System.err.println("Maximum number of clients exceeded");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
