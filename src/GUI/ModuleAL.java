package GUI;

import java.awt.*;
import java.awt.event.*;

public class ModuleAL implements MouseMotionListener
{
	private int x_offset;
	private int y_offset;
	private boolean dragging;
	private ModuleGUI owner;
	
	public ModuleAL(ModuleGUI owner)
	{
		super();
		
		dragging = false;
		x_offset = 0;
		y_offset = 0;
		this.owner = owner;
	}
	
	public void mouseDragged(MouseEvent e)
	{
		if (!dragging)
		{
			dragging = true;
			x_offset = e.getX();
			y_offset = e.getY();
//			System.out.println(owner.getX()+" "+owner.getWidth());
		}
		else
		{
			int x, y;
			x = owner.getX() + e.getX() - x_offset;
			y = owner.getY() + e.getY() - y_offset;
			owner.setLocation(x, y);
		}
	}
	
	public void mouseMoved(MouseEvent e)
	{
		// FIXME:
		// Possibly largest hack in the entire project
		dragging = false;
	}
}