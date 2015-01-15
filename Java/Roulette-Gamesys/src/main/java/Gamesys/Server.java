package Gamesys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Server {
	static ServerSocket serverSocket;
	static Vector<PrintWriter> clientsOutput; 
	
	public static void main( String[] args )
    {
		int port=1234;
		int milsec=30000;
		try {
			serverSocket=new ServerSocket(port);
			final Roulette app=new Roulette("Input.txt");
			clientsOutput=new Vector<PrintWriter>();
			Timer timer = new Timer();
	        timer.schedule(new TimerTask() {
	             @Override
	             public void run() {
	            	 synchronized(app){
	            		String message="";
			         
		            	for (int i=0;i<clientsOutput.size();i++)
			            	clientsOutput.elementAt(i).println("Spinning Roulette...");
		            	message=app.spinRoulette();
		            	for (int i=0;i<clientsOutput.size();i++)
			            	clientsOutput.elementAt(i).println(message);
		            	for (int i=0;i<clientsOutput.size();i++)		            
			            	clientsOutput.elementAt(i).println("Please enter your bet while waiting for the spin of the roulette:  [name, 1-36 or odd or even, Amount] or enter 'end' to exit ");

		            	//update list
		            	int i=0;
		            	 while (i<clientsOutput.size())
		            	 {
		            		 if (clientsOutput.elementAt(i)==null)
		            			 clientsOutput.remove(i);
		            		 else
		            			 i++;
		            	 }
	            	 }
	            	 
	             }
	        }, milsec, milsec);
			while (true) {
				Socket socket=serverSocket.accept();
				BufferedReader in = new BufferedReader(
						    new InputStreamReader(
						    socket.getInputStream()));
		    	PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				clientsOutput.addElement(out);
			    Thread t = new ThreadServerHandler(socket,app,in,out);
			    t.start();
			   }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
