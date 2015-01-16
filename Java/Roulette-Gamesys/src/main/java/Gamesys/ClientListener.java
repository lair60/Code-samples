package Gamesys;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientListener extends Thread {
	private BufferedReader in;
	public ClientListener(BufferedReader i)
	{
		in=i;
		
	}
	
	public void run() {
		String fromServer="";
		try {
			fromServer = in.readLine();
			while (!fromServer.equals("See you soon!"))
			{
	            System.out.println(fromServer);
				fromServer=in.readLine();
			}
			System.out.println(fromServer);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}	

}
