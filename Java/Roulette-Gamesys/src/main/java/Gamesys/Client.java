package Gamesys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) throws IOException {

	    Socket socket = null;
	    PrintWriter out = null;
	    BufferedReader in = null;
	    int port=1234;
	    Thread t=null;
	    try {
	      socket = new Socket("localhost", port);
	      out = new PrintWriter(socket.getOutputStream(), true);
	      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	      t = new ClientListener(in);
		  t.start();
	    } catch (UnknownHostException e) {
	      System.err.println("Don't know about host");
	      System.exit(1);
	    } catch (IOException e) {
	      System.err.println("Couldn't get I/O for the connection");
	      System.exit(1);
	    }

	    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	    
	    String fromUser="";
	    
	    while (!fromUser.equals("end"))
	    {
	    
	    	fromUser = stdIn.readLine();
		    out.println(fromUser);
		    
	    }
	    
	    while (t.isAlive())//waiting the thread
	    {
	    	
	    }
	    out.close();
	    in.close();
	    stdIn.close();
	    socket.close();
	 }
}
