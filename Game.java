import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class Game
{
	Scanner in;
	ArrayList<String> wordList;
		
	public Game()
	{
		System.out.println("Welcome to Hangman");
		wordList = new ArrayList<String>();

		in = new Scanner(System.in);
		startGame();
	}
	
	private void startGame()
	{
		int wordLength;

		System.out.println("Word length: ");
		wordLength = in.nextInt();
		System.out.println("You picked : " + wordLength);
		if(loadWords(wordLength))
		{
			System.out.println("There are " + wordList.size() + " words");
		}
		/*for(String s : wordList)
			System.out.println(s);*/
	}
	
	private boolean loadWords(int wordLength)
	{
		boolean ret = true;
		String word;
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("wordlist.txt"));
			System.out.println("File Loaded");
			while((word=br.readLine()) != null)
			{
				if(word.length() == wordLength)
				{
					wordList.add(word);
				}
			}
			br.close();
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			ret = false;
		}
		
		return ret;
	}
}
