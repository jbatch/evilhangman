import java.util.ArrayList;

public class WordFamily extends ArrayList<String>
{
	private String pattern;

	public WordFamily(String p)
	{
		pattern = p;
	}
	
	public WordFamily()
	{
		pattern = "";
	}
	
	public String getPattern()
	{
		return pattern;
	}
	
	public boolean equals(WordFamily wf)
	{
		boolean ret = false;
		if(wf.getPattern().equals(pattern))
			ret = true;
		return ret;
	}
	
}
