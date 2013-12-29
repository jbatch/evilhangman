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

import javax.swing.Box;




public class HangmanGui extends JFrame// implements ActionListener
{

	private JPanel gamePanel, hangmanArea, builtWordArea;
	private JPanel remLettersArea, usedLettersArea, bottomPanel, remPanel, usedPanel;
	private Letter[] remainingLetters, usedLetters;
	private JLabel builtWord, lettersRemainingLabel, lettersUsedLabel, gameStatusLabel;
	private JMenuItem newGame;


	public HangmanGui()
	{
		int gameHeight = 500, gameWidth = 750;
		
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		newGame = new JMenuItem("New Game");
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
	
		gamePanel = new JPanel();
		hangmanArea = new JPanel();
		builtWordArea = new JPanel();
		remLettersArea = new JPanel();
		usedLettersArea = new JPanel();
		bottomPanel = new JPanel();	
		remPanel = new JPanel();	
		usedPanel = new JPanel();
		hangmanArea.setBackground(Color.black);
		//builtWordArea.setBackground(Color.red);
		
		remLettersArea.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		remainingLetters = new Letter[26];
		for(int i = 0;i<26;i++)
		{
			remainingLetters[i] = new Letter("" + (char)('A' + i));
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
		usedLettersArea.add(Box.createRigidArea(new Dimension(0,50)));
		
		
		
		builtWordArea.setLayout(new BoxLayout(builtWordArea, BoxLayout.Y_AXIS));
		builtWord = new JLabel("-----");
		builtWord.setAlignmentX(Component.CENTER_ALIGNMENT);
		gameStatusLabel = new JLabel("");
		gameStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		builtWordArea.add(Box.createVerticalGlue());
		builtWordArea.add(builtWord);
		builtWordArea.add(Box.createRigidArea(new Dimension(0,40)));
		builtWordArea.add(gameStatusLabel);
		builtWordArea.add(Box.createVerticalGlue());
		
		lettersRemainingLabel = new JLabel("Letters Remaining:");
		remPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		remPanel.add(lettersRemainingLabel);
		remPanel.setMaximumSize(new Dimension(gameWidth, lettersRemainingLabel.getHeight()));
		
		lettersUsedLabel = new JLabel("Letters Used:");
		usedPanel.setBackground(Color.yellow);
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

	}
	
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
	
	public JMenuItem getNewGame()
	{
		return newGame;
	}
	
	public void reset()
	{
		for(int i = 0;i<26;i++)
		{
			remainingLetters[i].setVisible(true);
			usedLetters[i].setVisible(false);
		}
	}
}
