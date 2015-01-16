package Gamesys;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientListenerTest2 extends Thread {
	private BufferedReader in;
	public ClientListenerTest2(BufferedReader i)
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
					assertTrue((order==2)||(order==4)||(order==5)||(order==7));
					order++;
				}
				if (fromServer.equals("Invalid bet's format"))
				{
					assertTrue(order==3);
					order++;
				}
				if (fromServer.equals("Invalid User: Max length 12 characters"))
				{
					assertTrue(order==6);
					order++;
				}
				if (fromServer.equals("Invalid bet"))
				{
					assertTrue((order==1)||(order==8)||(order==9));
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
