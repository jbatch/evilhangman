import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JButton;




public class HangmanGui extends JFrame// implements ActionListener
{

	private JPanel gamePanel, hangmanArea, builtWordArea;
	private JPanel remLettersArea, usedLettersArea, bottomPanel, remPanel, usedPanel;
	private Letter[] remainingLetters, usedLetters;
	private JLabel builtWord, lettersRemainingLabel, lettersUsedLabel, gameStatusLabel;


	public HangmanGui()
	{
		int gameHeight = 500, gameWidth = 750;
	
	
		gamePanel = new JPanel();
		hangmanArea = new JPanel();
		builtWordArea = new JPanel();
		remLettersArea = new JPanel();
		usedLettersArea = new JPanel();
		bottomPanel = new JPanel();	
		remPanel = new JPanel();	
		usedPanel = new JPanel();
		hangmanArea.setBackground(Color.black);
		builtWordArea.setBackground(Color.red);
		
		remLettersArea.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		remainingLetters = new Letter[26];
		for(int i = 0;i<26;i++)
		{
			remainingLetters[i] = new Letter("" + (char)('A' + i));
			//remainingLetters[i].addActionListener(this);
			remLettersArea.add(remainingLetters[i]);
		}
		remLettersArea.setPreferredSize(new Dimension(gameWidth, remainingLetters[0].getHeight()));
		
		usedLettersArea.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		usedLetters = new Letter[26];
		for(int i = 0;i<26;i++)
		{
			usedLetters[i] = new Letter("" + (char)('A' + i));
			usedLetters[i].setVisible(false);
			usedLettersArea.add(usedLetters[i]);
		}
		
		builtWord = new JLabel("-----");
		gameStatusLabel = new JLabel("ttt");
		builtWordArea.add(builtWord);
		builtWordArea.add(gameStatusLabel);
		
		lettersRemainingLabel = new JLabel("Letters Remaining:");
		remPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		remPanel.add(lettersRemainingLabel);
		remPanel.setMaximumSize(new Dimension(gameWidth, lettersRemainingLabel.getHeight()));
		
		lettersUsedLabel = new JLabel("Letters Used:");
		usedPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		usedPanel.add(lettersUsedLabel);
		usedPanel.setMaximumSize(new Dimension(gameWidth, lettersUsedLabel.getHeight()));
		
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));
		remPanel.setBackground(Color.yellow);
		bottomPanel.add(remPanel);
		bottomPanel.add(remLettersArea);
		bottomPanel.add(usedPanel);
		bottomPanel.add(usedLettersArea);
		
		
		gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.PAGE_AXIS));
		gamePanel.add(hangmanArea);
		gamePanel.add(builtWordArea);
		gamePanel.add(bottomPanel);
		
		add(gamePanel);
				
		setTitle("Evil Hangman");
     	setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(gameWidth,gameHeight);
      setLocationRelativeTo(null);
      setVisible(true);
      setResizable(false);
      
      JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem exitGame = new JMenuItem("Exit");
		
		exitGame.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent event)
            {
            	System.exit(0);           	
            }
      });
      
      file.add(newGame);
		file.add(exitGame);
		menubar.add(file);
		setJMenuBar(menubar);
     
	}
	
	/*public void actionPerformed(ActionEvent e) 
	{          
		((JButton)(e.getSource())).setVisible(false);
		for(int i=0;i<26;i++)
		{
			if(usedLetters[i].getText().equals(((JButton)(e.getSource())).getText()))
			{
				usedLetters[i].setVisible(true);
			}
		}
	}*/
	
	public Letter[] getRemainingLetters()
	{
		return remainingLetters;
	}
	public Letter[] getUsedLetters()
	{
		return usedLetters;
	}
	
	public JLabel getBuiltWord()
	{
		return builtWord;
	}
	
	public JLabel getGameStatusLabel()
	{
		return gameStatusLabel;
	}
}
