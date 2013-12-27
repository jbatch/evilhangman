import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class Game
{
	Scanner in;
	
	ArrayList<String> wordList;
	ArrayList<Character> remainingLetters, usedLetters;
	String builtWord;
		
	public Game()
	{
		System.out.println("Welcome to Hangman");
		builtWord = "";
		wordList = new ArrayList<String>();
		remainingLetters = new ArrayList<Character>();
		for(int i = 'A';i <= 'Z';i++)
			remainingLetters.add(Character.toChars(i)[0]); //List of letters not yet guessed

		usedLetters = new ArrayList<Character>(); //Initially an empty arrayList

		in = new Scanner(System.in);
		startGame();
	}
	
	private void startGame()
	{
		int wordLength = 0;
		boolean validLength = false;
		while(!validLength)
		{
			System.out.println("Enter Word length: ");
			try
			{
				wordLength = ConsoleIO.readInt();
			}
			catch(Exception e){System.out.println(e.getMessage());}

			builtWord = fillString('-', wordLength);

			if(loadWords(wordLength))
			{
				System.out.println("There are " + wordList.size() + " words");
				validLength = true;
				startGuessing();
			}
			else
			{
				System.out.println("Invalid word length");
			}
		}
	}
	
	private boolean loadWords(int wordLength)
	{
		boolean ret = false;
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
					ret = true;
				}
			}
			br.close();
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return ret;
	}
	
	private void startGuessing()
	{
		int guessesLeft = 10, lettersGuessed = 0;
		char c = ' ';

		ArrayList<String>[] newList = (ArrayList<String>[])new ArrayList[wordList.get(0).length() + 1];
		for(int i=0;i<wordList.get(0).length() + 1;i++) //Initialise each wordlist in the array
			newList[i] = new ArrayList<String>();
		
		
		while(guessesLeft > 0 && lettersGuessed < wordList.get(0).length())
		{
			printGameStatus(guessesLeft);
			System.out.print("\n\nGuess a letter: ");
			try
			{
				c = ConsoleIO.readLine().charAt(0);
				c = Character.toUpperCase(c);
			}
			catch(Exception e){System.out.println(e.getMessage());}
			
			for(String s: wordList)
			{
				s = s.toUpperCase();
				if(!s.contains(c + ""))
					newList[wordList.get(0).length()].add(s);
				else if(s.indexOf(c) != s.lastIndexOf(c))
				{
				 //Letter appears twice so ignore this word
				}
				else
				{
					for(int i = 0; i < wordList.get(0).length(); i++)
					{
						if(s.charAt(i) == c)
							newList[i].add(s);
					}
				}
			}
			
			int maxSize = 0, maxIdx = -1;
			for(int i=0;i<newList.length;i++)
			{
				if(newList[i].size()>maxSize)
				{
					maxSize = newList[i].size();
					maxIdx = i;
				}
			}
			
			wordList.clear();
			wordList.addAll(newList[maxIdx]);
			for(int i=0;i<newList.length;i++)
				newList[i].clear();
			
			guessesLeft--;
			remainingLetters.remove(Character.valueOf(c));
			usedLetters.add(Character.toChars(c)[0]);
			if(maxIdx != wordList.get(0).length())
			{
				lettersGuessed++;
				int j = wordList.get(0).indexOf(c);
				builtWord = builtWord.substring(0,j) + c + builtWord.substring(j + 1);
			}
		}
		if(lettersGuessed < wordList.get(0).length()) //PlayerLost
		{
			int r = (int)(Math.random() * ((wordList.size() - 1) + 1));
			System.out.println("You lost!\nThe word was: " + wordList.get(r));
		}
	}
	
	private String arrayListToString(ArrayList<Character> list)
	{
		String s = new String();
		for(Character c: list)
			s = s + c.toString() + " ";
		return s;
	}
	
	private void printGameStatus(int gr)
	{
		System.out.println("Guesses Remaining: " + gr + "\nLetters Remaining:\n" + arrayListToString(remainingLetters) + "\nLetters Used:\n" + arrayListToString(usedLetters) +"\nWord:" + builtWord);
	}
	
	public String fillString(char c, int count)
	{
		StringBuilder sb = new StringBuilder(count);
		for(int i=0;i<count;i++)
			sb.append(c);
		return sb.toString();
	}
	
	
}
