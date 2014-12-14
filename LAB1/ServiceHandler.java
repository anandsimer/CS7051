import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author anands@tcd.ie
 */

public class ServiceHandler implements Runnable {
	
	private Socket socket;
	private String input_client, sentence;
	private boolean closed;
        // OutStream for client socket connection
	private PrintWriter os;
        // InputStream for client socket connection
        private BufferedReader in;
	
	public ServiceHandler(Socket socket){
		this.socket = socket;
		this.closed = false;
	}
	
 /* Below is the code, that takes the input from client
    connected and performs action or responds back to client.
*/	
	
	@Override
	public void run() {
		try {   
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			os = new PrintWriter(socket.getOutputStream(),true);
			while(!closed){
				input_client = in.readLine();
				if(input_client.startsWith("HELO")){
                                        sentence = input_client+"IP: " +socket.getInetAddress() +"Port: " +socket.getLocalPort() +"StudentID:14303767 ";
					os.println(sentence);
				}
				else if(input_client.equals("KILL_SERVICE")){
					closed = true;
                                        os.println("Connection Termination requested");
					socket.close();
					System.exit(1);
				}
                                // When clients writes some other command(String) to the server, 
                                // server reply back with the same command (String)
				else{
                                        os.println(input_client);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}