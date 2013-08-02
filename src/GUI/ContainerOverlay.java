package GUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class ContainerOverlay extends JComponent
{
	public ContainerWindow main_window;
	Point mouse;
	
	public ContainerOverlay(ContainerWindow main_window)
	{
		this.main_window = main_window;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if (main_window.is_connecting)
		{
			Point a = main_window.first_port.getLocationOnScreen();
			SwingUtilities.convertPointFromScreen(a, main_window);
			// 5 = half the width and height of the port sprite, so the cursor is in the middle
			a.x += 5 - main_window.getInsets().left;
			a.y += 5 - main_window.getInsets().top;
			g.drawLine(a.x, a.y, mouse.x, mouse.y);
		}
	}
}
