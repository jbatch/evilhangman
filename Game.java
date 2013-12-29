import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Game
{
	Scanner in;
	
	ArrayList<String> wordList;
	ArrayList<Character> remainingLetters, usedLetters;
	String builtWord, pattern;
		
	public Game()
	{
		new HangmanGui();	
		System.out.println("Welcome to Hangman");
		builtWord = "";
		wordList = new ArrayList<String>();
		remainingLetters = new ArrayList<Character>();
		for(int i = 'A';i <= 'Z';i++)
		{
			remainingLetters.add(Character.toChars(i)[0]); //List of letters not yet guessed
		}

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
			catch(IOException e){System.out.println(e.getMessage());}

			builtWord = fillString('-', wordLength);
			pattern = fillString('.', wordLength);

			if(loadWords(wordLength))
			{
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
			while((word=br.readLine()) != null)
			{
				if(word.matches(pattern))
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
		boolean matchedFamily = false;
		
		ArrayList<WordFamily> familyList = new ArrayList<WordFamily>();
	
		while(guessesLeft > 0 && pattern.contains("."))
		{
			printGameStatus(guessesLeft);
			System.out.print("\n\nGuess a letter: ");
			try
			{
				c = ConsoleIO.readLine().charAt(0);
				c = Character.toUpperCase(c);
			}
			catch(IOException e){System.out.println(e.getMessage());}
			
			if(charExistsInArrayList(c,remainingLetters))
			{
				for(String s: wordList)
				{
					matchedFamily = false;
					s = s.toUpperCase();

					String sPattern = buildWordPattern(s,c, pattern);
					WordFamily thisFamily = new WordFamily(sPattern);
	
					for(WordFamily wf: familyList)
					{
						if(s.matches(wf.getPattern()))
						{
							wf.add(s);
							matchedFamily = true;
						}
					}			
					if(!matchedFamily)
					{
						thisFamily.add(s);
						familyList.add(thisFamily);
					}
				}

				WordFamily biggestFamily = new WordFamily();
				for(WordFamily wf:familyList)
				{
					if(wf.size()>biggestFamily.size())
					{
						biggestFamily = wf;
					}
				}
			
			
				wordList.clear();
				wordList.addAll(biggestFamily);
			
				for(WordFamily wf:familyList)
				{
					wf.clear();
				}
				familyList.clear();
					
				remainingLetters.remove(Character.valueOf(c));
				usedLetters.add(Character.toChars(c)[0]);
			
				if(biggestFamily.getPattern().replace("[^"+ c +"]", ".").contains(c + ""))
				{
					lettersGuessed++;
					pattern = biggestFamily.getPattern().replace("[^"+ c +"]", ".");
					builtWord = pattern.replace('.','-');			
				}
				else
				{
					guessesLeft--;
				}
			}
			else
			{
				System.out.println("That is not a remaining letter");
			}
		}
		if(builtWord.contains("-")) //PlayerLost
		{
			int r = (int)(Math.random() * ((wordList.size() - 1) + 1));
			System.out.println("You lost!\nThe word was: " + wordList.get(r));
		}
		else
		{
			System.out.println("You win");
		}
	}
	
	private String buildWordPattern(String s, char c, String patternSoFar)
	{
		int i = 0;
		char[] returnCharArray = patternSoFar.toCharArray();
		for(char ch:s.toCharArray())
		{
			if(ch == c)
			{
				returnCharArray[i] = c;
			}
			else if (ch != c && patternSoFar.charAt(i) == '.')
			{
				returnCharArray[i] = '^';
			}
			i++;
		}
		
		return String.valueOf(returnCharArray).replace("^", "[^" + c + "]");
	}
	
	private String arrayListToString(ArrayList<Character> list)
	{
		String s = new String();
		for(Character c: list)
		{
			s = s + c.toString() + " ";
		}
		return s;
	}
	
	private void printGameStatus(int gr)
	{
		System.out.println("Possible Words: " + wordList.size() + "\nGuesses Remaining: " + gr + "\nLetters Remaining:\n" + arrayListToString(remainingLetters) + "\nLetters Used:\n" + arrayListToString(usedLetters) +"\nWord:" + builtWord);
		//printWordList(wordList);
	}
	
	private void printWordList(ArrayList<String> wl)
	{
		for(String s:wl)
		{
			System.out.println(s);
		}
	}
	
	public String fillString(char c, int count)
	{
		StringBuilder sb = new StringBuilder(count);
		for(int i=0;i<count;i++)
		{
			sb.append(c);
		}
		return sb.toString();
	}
	
	private boolean charExistsInArrayList(char c, ArrayList<Character> al)
	{
		boolean ret = false;
		
		for(Character ch:al)
		{
			if(ch.charValue() == c)
			{
				ret = true;
			}
		}
		
		return ret;
	}
	
	
	
}
