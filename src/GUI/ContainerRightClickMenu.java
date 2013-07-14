package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.*;

public class ContainerRightClickMenu extends JPopupMenu
{
	private ContainerWindow owner;
	private Point pos;
	
	public ContainerRightClickMenu(ContainerWindow owner)
	{
		super();
		
		this.owner = owner;
		
		JMenuItem item = new JMenuItem(new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				ContainerRightClickMenu.this.create_module(Engine.Constants.MODULE_OSCILLATOR);
				ContainerRightClickMenu.this.hide();
			}
		}
		);
		item.setText("Create Oscillator");
		add(item);
		
		item = new JMenuItem(new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				ContainerRightClickMenu.this.create_module(Engine.Constants.MODULE_CONTAINER);
				ContainerRightClickMenu.this.hide();
			}
		}
		);
		item.setText("Create Container");
		add(item);
	}
	
	public void open(Point pos)
	{
		this.pos = pos;
		setLocation(pos);
		setVisible(true);
	}
	
	public void hide()
	{
		setVisible(false);
	}
	
	public void create_module(int type)
	{
		try
		{
			owner.add_module(type, pos.x - owner.getLocation().x, pos.y - owner.getLocation().y - this.getHeight());
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
