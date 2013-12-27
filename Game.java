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
		builtWord = new String("-----");
		wordList = new ArrayList<String>();
		remainingLetters = new ArrayList<Character>();
		for(int i = 'a';i <= 'z';i++)
			remainingLetters.add(Character.toChars(i)[0]);

		usedLetters = new ArrayList<Character>();

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
				if(wordLength >=2 && wordLength <= 10)
					validLength = true;
			}
			catch(Exception e){System.out.println(e.getMessage());}
		}

		if(loadWords(wordLength))
		{
			System.out.println("There are " + wordList.size() + " words");
			startGuessing();
		}
		else
		{
			System.out.println("An error occured while loading the word list");
		}
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
	
	private void startGuessing()
	{
		int guessesLeft = 10, lettersGuessed = 0;
		char c = ' ';

		ArrayList<String>[] newList = (ArrayList<String>[])new ArrayList[wordList.get(0).length() + 1];
		for(int i=0;i<wordList.get(0).length() + 1;i++)
			newList[i] = new ArrayList<String>();
		
		
		while(guessesLeft > 0 && lettersGuessed < wordList.get(0).length())
		{
			printGameStatus(guessesLeft);
			System.out.println("Guess a letter: ");
			try
			{
				c = ConsoleIO.readChar();
			}
			catch(Exception e){System.out.println(e.getMessage());}
			
			for(String s: wordList)
			{
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
			
			/*for(int i=0;i<newList.length;i++)
				System.out.println("List Size: " + newList[i].size());*/
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
			
			//for(String s: wordList)
				//System.out.println(s);
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
	
}
