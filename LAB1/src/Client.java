
/* 
    Client.java - Client works as per specifications of the Lab 1.
    You can execute the Client.java, and type 'HELO' to see the expected output.
    Type 'KILL_SERVICE' to kill all threads, server as well as client threads.
    Type any random message, to see server responding to you back.
*/

import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

/**
 *
 * @author anands@tcd.ie, NDS course, Student ID 14303767
 * Trinity College Dublin
 */
public class Client {

    private BufferedReader cmdline_input, in;
    private DataOutputStream os;
    private int port;
    private Socket socket;
    private boolean closed = true;

    public Client(int port) {
        cmdline_input = new BufferedReader(new InputStreamReader(System.in));
        this.port = port;
        try {
            this.socket = new Socket("localhost", port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {
        if (args.length < 1) {
            System.err
                    .println("Usage: java Client <port_number> \n");
        } else {
            int port = Integer.parseInt(args[0]);
            new Client(port).runClient();
        }
    }

    //  Function to communicate with the server service.

    public void runClient() {
        String reply = null;
        System.out.println("Client is now running, on port number: " + port);

        System.out.println("Please Input message:");
        while (closed) {
            try {
                os = new DataOutputStream(socket.getOutputStream());
                os.writeBytes(cmdline_input.readLine() + "\n");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while ((reply = in.readLine()) != null) {
                    if (reply.equals("end")) {
                        if (closed) {
                            System.out.println("Please Input message:");
                        }
                        break;
                    }
                    if (reply.contains("Termination requested")) {
                        System.err.println("Closing this thread and all threads");
                        socket.close();
                        System.exit(1);
                    }
                    System.out.println("Server:" + reply);
                }

            } catch (Exception e) {
                closed = false;
                System.err.println("Server Unreachable! OR may be the server is overloaded");
                e.printStackTrace();
            }
        }
    }
}
