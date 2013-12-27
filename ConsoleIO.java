import java.io.*;

public class ConsoleIO
{
	public static char readChar() throws Exception
	{
		char c = ' ';
		try
		{
			c = (char)System.in.read();
		}
		catch(Exception e){throw new Exception(e);}
		return c;
	}
	
	public static String readLine() throws Exception
	{
		String s = " ";
		char c = ' ';
		do
		{
			try
			{
				c = readChar();
				if(c != '\n')
				{
					s += c;
				}
			}
			catch(Exception e){throw new Exception(e);}
		}
		while(c != '\n');
		return s;
	}
	
	public static void clearLine() throws Exception
	{
		char c = ' ';
		do
		{
			try
			{
				c = readChar();
			}
			catch(Exception e){throw new Exception(e);}			
		}
		while(c != '\n');
	}
	
	public static double readDouble() throws Exception
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		StreamTokenizer st = new StreamTokenizer(isr);
		double d = 0.0;
		boolean gotNumber = false;
		
		while(!gotNumber)
		{
			try
			{
				if(st.nextToken() == st.TT_NUMBER)
				{
					d = st.nval;
					gotNumber = true;
				}
				else
				{
					throw new Exception("Non numeric value entered");
				}
			}
			catch(Exception e){throw new Exception(e);}
		}
		return d;
	}
	
	public static int readInt() throws Exception
   {
		InputStreamReader isr = new InputStreamReader(System.in);
		StreamTokenizer  st = new StreamTokenizer(isr);
		Double tmp;
		int i = -1;
		boolean   gotNumber = false;

		while ( !gotNumber )
	   {
			try 
			{
				if ( st.nextToken() == st.TT_NUMBER )
				{
					tmp = new Double( st.nval);
					i = tmp.intValue();
					gotNumber = true;
				}
				else
				{
					throw new Exception("Non integer entered");
				}
			}
			catch(Exception e){throw new Exception(e);}
	   }

		return i;
   }

}
