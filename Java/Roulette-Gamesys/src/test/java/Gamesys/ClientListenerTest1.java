package Gamesys;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientListenerTest1 extends Thread {
	private BufferedReader in;
	public ClientListenerTest1(BufferedReader i)
	{
		in=i;
		
	}
	public void run() {
		String fromServer="";
		int order=1;
		try {
			fromServer = in.readLine();
			while (!fromServer.equals("See you soon!"))
			{
				
				if (fromServer.equals("Processed bet."))
				{
					assertTrue((order==1)||(order==2)||(order==3)||(order==4));
					order++;
				}
				if (fromServer.equals("Invalid bet's format"))
				{
					assertTrue(order==8);
					order++;
				}
				if (fromServer.equals("Invalid User: Max length 12 characters"))
				{
					assertTrue(order==9);
					order++;
				}
				if (fromServer.equals("Invalid bet"))
				{
					assertTrue((order==5)||(order==6)||(order==7));
					order++;
				}
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
