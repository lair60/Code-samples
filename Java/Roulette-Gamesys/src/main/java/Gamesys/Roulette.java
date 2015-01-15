package Gamesys;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

public class Roulette 
{
	private int ball;
	private HashMap<String, Vector>  currentList;
	private HashMap<String, float[]> info;
	private String file;
	public Roulette(String fileName)
	{
		info=new HashMap<String,float[]>();		
		currentList= new HashMap<String, Vector>() ;
		file=fileName;
		readFile(file);
	}
	private boolean udpateFile()
	{
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
        	URL url = ClassLoader.getSystemResource(file);
            fichero = new FileWriter(url.getFile());
            pw = new PrintWriter(fichero);
            Iterator it= info.entrySet().iterator();
    		while (it.hasNext()) 
    		{
    			Map.Entry e = (Map.Entry)it.next();
    			pw.println((String)e.getKey() + ","+ ((float)((float[])e.getValue())[0])+","+((float)((float[])e.getValue())[1]));
    		}	
 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
              return false;
           }
        }
		return true;
	}
	private boolean readFile(String path)
	{
		BufferedReader bf=null;
		try {
			String sCadena;
			InputStream is = ClassLoader.getSystemResourceAsStream(file);
			InputStreamReader isr = new InputStreamReader(is);
			bf = new BufferedReader(isr);
			
			float [] arrayfloat;
			String name;
			while ((sCadena = bf.readLine())!=null) {
				StringTokenizer tokens=new StringTokenizer(sCadena,",");
				name=tokens.nextToken();//name
				arrayfloat=new float [2];
				arrayfloat[0]= Float.parseFloat(tokens.nextToken());//total win 
				arrayfloat[1]= Float.parseFloat(tokens.nextToken());//total bet
				if (tokens.hasMoreTokens())
					return false;//Invalid format.
				info.put(name, arrayfloat);
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("File not Founded!");
			return false;
		}finally{
			if(bf!=null)
				try {
					bf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					return false;
				}
		}
		return true;
	}
	public String spinRoulette()
	{
		int milsec=1000;
		Random r = new Random();
		ball= r.nextInt(36)+1;//1..36 inclusive
		try {
			Thread.sleep(milsec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		processResult();
		return printPlayersList();
	}
	private String printPlayersList()
	{
		String result="";
		result="Number: "+ ball+"\n";
		result=result+String.format("%-15s %-9s %-9s %n","Player","Total Win","Total Bet");
		Iterator it= info.entrySet().iterator();
		while (it.hasNext()) 
		{
			Map.Entry e = (Map.Entry)it.next();
			result=result+String.format("%-15s %9.1f %9.1f %n",e.getKey(),((float[])e.getValue())[0],((float[])e.getValue())[1]);			
		}
		return result;
	}
	private void processResult()
	{
		Iterator it= currentList.entrySet().iterator();
		while (it.hasNext()) 
		{
			Map.Entry e = (Map.Entry)it.next();
			Vector p1=(Vector) e.getValue();
			if (e.getKey().equals("even")&& (ball%2==0))
			{
				for(int i=0;i<p1.size();i=i+2)
					{
						float[] array=info.get(p1.elementAt(i));
						array[0]=array[0]+((Float)p1.elementAt(i+1)).floatValue();//total win
						array[1]=array[1]+((Float)p1.elementAt(i+1)).floatValue();//total bet
						info.put((String)p1.elementAt(i), array);			
					}
			}
			else
				{
					if (e.getKey().equals("odd")&& (ball%2!=0))
					{
						for(int i=0;i<p1.size();i=i+2)
							{
								float[] array=info.get(p1.elementAt(i));
								array[0]=array[0]+((Float)p1.elementAt(i+1)).floatValue();//total win
								array[1]=array[1]+((Float)p1.elementAt(i+1)).floatValue();//total bet
								info.put((String)p1.elementAt(i), array);			
							}
					}
					else//It's number
					{
						if (e.getKey().equals(Integer.toString(ball)))//win number
						{
							for(int i=0;i<p1.size();i=i+2)
								{
									float[] array=info.get(p1.elementAt(i));
									array[0]=((Float)p1.elementAt(i+1)).floatValue()*35;//total win(36-1)
									array[1]=array[1]+((Float)p1.elementAt(i+1)).floatValue();//total bet
									info.put((String)p1.elementAt(i), array);			
								}
						}
						else//it's number but not win
						{
							for(int i=0;i<p1.size();i=i+2)
								{
									float[] array=info.get(p1.elementAt(i));
									array[1]=array[1]+((Float)p1.elementAt(i+1)).floatValue();//total bet
									info.put((String)p1.elementAt(i), array);			
								}
						}
					}
					
				}			
		}
		udpateFile();	
		currentList.clear();		
	}
	
	public String validBet(String bet)
	{
		StringTokenizer tokens=new StringTokenizer(bet);
		
		if (tokens.countTokens()!=3)
		{
			return "Invalid bet's format";
		}
		else
		{
			String v1=tokens.nextToken();//name
			String v2=tokens.nextToken();// 1..36 or ODD or EVEN
			Float v3=Float.parseFloat(tokens.nextToken());// how much
			
			//if (!info.containsKey(v1))
			if (v1.length()>12)
				return "Invalid User: Max length 12 characters";
			else
			
				if (((!(v2.toLowerCase().equals("odd")||(v2.toLowerCase().equals("even")||((0< Integer.parseInt(v2))&&((Integer.parseInt(v2)<37)))))) || (0.0 > v3) ))		
					return "Invalid bet";
				else
				{
					if (!info.containsKey(v1))//If it's a new user
					{
						info.put(v1, new float[]{0,0});
					}
			
					if (!currentList.containsKey(v2.toLowerCase()))
					{
						Vector p1=new Vector();
						p1.addElement(v1);//Name
						p1.addElement(v3);//Amount
						currentList.put(v2.toLowerCase(),p1);
					}
					else
					{
						Vector p1=currentList.get(v2.toLowerCase());
						p1.addElement(v1);//Name 
						p1.addElement(v3);//Amount
						currentList.put(v2.toLowerCase(),p1);
					}
					return "Processed bet.";					
				}
		}
}
}

