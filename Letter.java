import javax.swing.JButton;
import javax.swing.event.MouseInputAdapter; 
import java.awt.event.MouseEvent;

import java.awt.Color;

public class Letter extends JButton
{
	private Color focusColor = Color.blue; 
	
	public Letter(String text)
	{
		super(text);  
		setBorderPainted(false);  
		setContentAreaFilled(false);  
		setFocusPainted(false);  
		setOpaque(false);  
		addMouseListener(new RolloverListener());
	}
	
	public void setFocusColor(Color focusColor) 
	{  
		this.focusColor = focusColor;  
	}  
	
	public Color getFocusColor() 
	{  
		return focusColor;  
	}  
  //Private inner class
	private final class RolloverListener extends MouseInputAdapter {  
		
		private Color origColor;  
		  
		public void mouseEntered(MouseEvent e) {  
			origColor = getForeground();  
			setForeground(focusColor);  
		}  
		
		public void mouseExited(MouseEvent e) 
		{  
			if (getForeground() == focusColor) 
			{  
				setForeground(origColor);  
			}  
		} 
	}
	
}
