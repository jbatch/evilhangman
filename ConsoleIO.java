import java.io.*;

public class ConsoleIO
{
	public static char readChar() throws IOException
	{
		char c = ' ';
		try
		{
			c = (char)System.in.read();
		}
		catch(IOException e){throw new IOException(e);}
		return c;
	}
	
	public static String readLine() throws IOException
	{
		String s = "";
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
			catch(IOException e){throw new IOException(e);}
		}
		while(c != '\n');
		return s;
	}
	
	public static void clearLine() throws IOException
	{
		char c = ' ';
		do
		{
			try
			{
				c = readChar();
			}
			catch(IOException e){throw new IOException(e);}			
		}
		while(c != '\n');
	}
	
	public static double readDouble() throws IOException
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
					throw new IllegalArgumentException("Non numeric value entered");
				}
			}
			catch(IOException e){throw new IOException(e);}
		}
		return d;
	}
	
	public static int readInt() throws IOException
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
					throw new IOException("Non integer entered");
				}
			}
			catch(IOException e){throw new IOException(e);}
	   }

		return i;
   }

}
