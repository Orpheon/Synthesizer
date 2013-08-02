package GUI;

import javax.swing.*;
import java.util.*;

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
		
		ModuleGUI m;
		PortGUI p;
		Point a, b;
		Iterator<ModuleGUI> i = main_window.module_list.iterator();
		while (i.hasNext())
		{
			m = i.next();
			for (int j=0; j<m.input_ports.length; j++)
			{
				p = m.input_ports[j];
				if (p.connection != null)
				{
					// Port p is connected to some other port, we need to draw that
					a = p.getLocationOnScreen();
					b = p.connection.getLocationOnScreen();
					
					SwingUtilities.convertPointFromScreen(a, main_window);
					SwingUtilities.convertPointFromScreen(b, main_window);
					// 5 = half the width and height of the port sprite, so the cursor is in the middle
					a.x += 5 - main_window.getInsets().left;
					a.y += 5 - main_window.getInsets().top;
					b.x += 5 - main_window.getInsets().left;
					b.y += 5 - main_window.getInsets().top;
					g.drawLine(a.x, a.y, b.x, b.y);
				}
			}
			// Since all pipes have an input and an output, by checking all the inputs we have all pipes (and no overlap)
		}
	}
}
