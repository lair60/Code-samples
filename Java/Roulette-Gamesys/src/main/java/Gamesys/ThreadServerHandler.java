package Gamesys;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

class ThreadServerHandler extends Thread {
	
	private Socket socket = null;
	private Roulette app = null;
	PrintWriter out=null;
	BufferedReader in=null;

    public ThreadServerHandler(Socket socket,Roulette a,BufferedReader i, PrintWriter o) {
    	super("ThreadServerHandler");
    	this.socket = socket;
    	app=a;
    	out=o;
    	in=i;    	
    	out.println("Welcome to the Roulette!!");
    	out.println("Please enter your bet while waiting for the spin of the roulette:  [name, 1-36 or odd or even, Amount] or enter 'end' to exit");
    }
    public void run() {
      try {
    	 
    	  String bet;
    	  String message;
    	  bet=in.readLine();
    	  while (!bet.equals("end")) 
    	  {
    		  synchronized(app){
    			  message=app.validBet(bet);  
    			
    			  out.println(message);
    			  out.println("Please enter your bet while waiting for the spin of the roulette:  [name, 1-36 or odd or even, Amount] or enter 'end' to exit");
    	  	    
    		  }
    		  bet=in.readLine();
    	}
    	  
    	out.println("See you soon!");
    	out.close();
    	out=null;
  	    in.close();
  	    socket.close();
  	
      }
      catch (Exception e) {
        // manipular las excepciones
      }
    }
  }
