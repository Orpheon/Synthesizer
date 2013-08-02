package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.SwingUtilities;

public class GlobalAL implements AWTEventListener
{
	private ContainerWindow main_window;
	
	public GlobalAL(ContainerWindow main_window)
	{
		super();
		this.main_window = main_window;
	}
	
	@Override
	public void eventDispatched(AWTEvent e)
	{
		if (e.getID() == MouseEvent.MOUSE_RELEASED)
		{
			ModuleGUI m;
			Iterator<ModuleGUI> i = main_window.module_list.listIterator();
			while (i.hasNext())
			{
				m = i.next();
				m.getAL().stopDragging();
			}
		}
		else if (e.getID() == MouseEvent.MOUSE_MOVED || e.getID() == MouseEvent.MOUSE_DRAGGED)
		{
			MouseEvent ev = (MouseEvent) e;
			Point p = ev.getLocationOnScreen();
			SwingUtilities.convertPointFromScreen(p, main_window);
			p.x -= main_window.getInsets().left;
			p.y -= main_window.getInsets().top;
			main_window.overlay.mouse = p;
			main_window.overlay.repaint();
		}
	}
}
