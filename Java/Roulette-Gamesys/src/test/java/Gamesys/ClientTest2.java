package Gamesys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class ClientTest2 {

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
	      t = new ClientListenerTest2(in);
		  t.start();
	    } catch (UnknownHostException e) {
	      System.err.println("Don't know about host");
	      System.exit(1);
	    } catch (IOException e) {
	      System.err.println("Couldn't get I/O for the connection");
	      System.exit(1);
	    }

	    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	 
	    Random ra=new Random();
	    
	    
	    //1 message (waiting for "Invalid bet")
		 try {
				Thread.sleep(ra.nextInt(8)*1000);
				out.println("Barbara even -0.4");
				System.out.println("Barbara even -0.4");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
	 
	  
	  //2 message (waiting for "Processed bet.")
	    try {
			Thread.sleep(ra.nextInt(8)*1000);
			out.println("Barbara 1 0.1");
			System.out.println("Barbara 1 0.1");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //3 message (waiting for "Invalid bet's format")
  		try {
  			Thread.sleep(ra.nextInt(8)*1000);
  			out.println("Barbaraeven 10");
  			System.out.println("Barbaraeven 10");
  		} catch (InterruptedException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
	  //4 message (waiting for "Processed bet.")
		try {
			Thread.sleep(ra.nextInt(8)*1000);
			out.println("Barbara 36 100.5");
			System.out.println("Barbara 36 100.5");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//5 message (waiting for "Processed bet.")
	    try {
			Thread.sleep(ra.nextInt(8)*1000);
			out.println("Barbara even 9.9");
			System.out.println("Barbara even 9.9");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  //6 message (waiting for "Invalid User")
	  		try {
	  			Thread.sleep(ra.nextInt(8)*1000);
	  			out.println("John_monkey4444 odd 2.4");
	  			System.out.println("John_monkey4444 odd 2.4");
	  		} catch (InterruptedException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}
		
		
		//7 message (waiting for "Processed bet.")
	    try {
			Thread.sleep(ra.nextInt(8)*1000);
			out.println("Barbara even 10");
			System.out.println("Barbara even 10");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//8 message (waiting for "Invalid bet" )
		try {
			Thread.sleep(ra.nextInt(8)*1000);
			out.println("Barbara 37 1");
			System.out.println("Barbara 37 1");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//9 message (waiting for "Invalid bet")
		
				try {
					Thread.sleep(ra.nextInt(8)*1000);
					out.println("Barbara 0 10");
					System.out.println("Barbara 0 10");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		//10 message  (waiting for "See you soon!")   
		try {
			Thread.sleep(ra.nextInt(8)*1000);
			out.println("end");
			System.out.println("end");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	    
	    while (t.isAlive())//waiting for thread's finish
	    {
	    	
	    }
	    out.close();
	    in.close();
	    stdIn.close();
	    socket.close();
	 }
}
