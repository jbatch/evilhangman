import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
///////Stuff for GUI///////////
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
///////////////////////////

public class Game implements ActionListener
{
	HangmanGui hGui;
	ArrayList<String> wordList;
	Letter[] remainingLettersArray, usedLettersArray;
	String builtWord, pattern;
	int guessesLeft;
	JLabel builtWordLabel, gameStatusLabel;
	boolean gameOver;
	JMenuItem newGame;
		
	public Game()
	{
		hGui = new HangmanGui();
		remainingLettersArray = hGui.getRemainingLetters();
		usedLettersArray = hGui.getUsedLetters();
		builtWordLabel = hGui.getBuiltWord();
		gameStatusLabel = hGui.getGameStatusLabel();
		newGame = hGui.getNewGame();
		newGame.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent event)
            {
            	newGame();
            	hGui.reset();           	
            }
      });
		for(int i = 0;i<26;i++)
		{
			remainingLettersArray[i].addActionListener(this);
		}
		newGame();
	}
	
	private void newGame()
	{
		guessesLeft = 10;
		gameOver = false;			
		builtWord = "";
		wordList = new ArrayList<String>();				
		gameStatusLabel.setText("Guesses left: " + guessesLeft);
		startGame();
	}
	
	private void startGame()
	{
		int wordLength = 5 + (int)(Math.random() * ((24 - 5) + 1));

		builtWord = fillString('-', wordLength);
		pattern = fillString('.', wordLength);
		builtWordLabel.setText(builtWord);

		if(loadWords(wordLength))
		{
			//words loaded successfully
		}
		else
		{
			newGame();
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
	
	public void actionPerformed(ActionEvent e) 
	{
		if(!gameOver)
		{     
			((JButton)(e.getSource())).setVisible(false);
			for(int i=0;i<26;i++)
			{
				if(usedLettersArray[i].getText().equals(((JButton)(e.getSource())).getText()))
				{
					usedLettersArray[i].setVisible(true);
					guessLetter(usedLettersArray[i].getText().charAt(0));
				}
			}	
		}	
	}
	
	private void guessLetter(char c)
	{
		if(!gameOver)
		{
			ArrayList<WordFamily> familyList = new ArrayList<WordFamily>();
			boolean matchedFamily = false;

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
	
			if(biggestFamily.getPattern().replace("[^"+ c +"]", ".").contains(c + ""))
			{
				pattern = biggestFamily.getPattern().replace("[^"+ c +"]", ".");
				builtWord = pattern.replace('.','-');	
				builtWordLabel.setText(builtWord);		
			}
			else
			{
				guessesLeft--;
			}
			
			gameStatusLabel.setText("Guesses left: " + guessesLeft);
			
			if(guessesLeft == 0 && builtWord.contains("-")) //PlayerLost
			{
				int r = (int)(Math.random() * ((wordList.size() - 1) + 1));
				gameStatusLabel.setText("You lost!\nThe word was: " + wordList.get(r));
				gameOver = true;
			}
			else if(!builtWord.contains("-"))
			{
				gameStatusLabel.setText("You Win");
				gameOver = true;
			}
		}
	}
	
}
