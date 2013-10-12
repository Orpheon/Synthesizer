package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class ContainerRightClickMenu extends JPopupMenu
{
	private ContainerWindow main_window;
	private Point mouse_position;
	
	class PositionChecker implements PopupMenuListener
	{
		private ContainerRightClickMenu rightclickmenu;
		
		public PositionChecker(ContainerRightClickMenu r)
		{
			super();
			rightclickmenu = r;
		}

		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent e)
		{
			// Store the mouse position in the popup menu so that created modules get put at the right place
			rightclickmenu.mouse_position = rightclickmenu.main_window.getMousePosition();
		}

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
		{
			// Do nothing
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent e)
		{
			// Do nothing
		}
	}
	
	public ContainerRightClickMenu(ContainerWindow main_window)
	{
		super();
		
		this.main_window = main_window;
		this.mouse_position = new Point(0, 0);
		
		this.addPopupMenuListener(new PositionChecker(this));
		
		JMenu module_list = new JMenu("Add modules");

		
		JMenuItem item = new JMenuItem(
			new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					ContainerRightClickMenu.this.create_module(Engine.Constants.MODULE_OSCILLATOR);
				}
			}
		);
		item.setText("Create Oscillator");
		module_list.add(item);
		
		item = new JMenuItem(
			new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					ContainerRightClickMenu.this.create_module(Engine.Constants.MODULE_CONSTANT);
				}
			}
		);
		item.setText("Create Constant");
		module_list.add(item);
		
		item = new JMenuItem(
			new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					ContainerRightClickMenu.this.create_module(Engine.Constants.MODULE_COPYER);
				}
			}
		);
		item.setText("Create Copyer");
		module_list.add(item);
		
		item = new JMenuItem(
			new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					ContainerRightClickMenu.this.create_module(Engine.Constants.MODULE_MERGER);
				}
			}
		);
		item.setText("Create Merger");
		module_list.add(item);
		
		item = new JMenuItem(
			new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					ContainerRightClickMenu.this.create_module(Engine.Constants.MODULE_RANGEMODIFIER);
				}
			}
		);
		item.setText("Create Range Modifier");
		module_list.add(item);
		
		// Add filters as submenu
		JMenu filters = new JMenu("Filters");
		item = new JMenuItem(
			new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					ContainerRightClickMenu.this.create_module(Engine.Constants.MODULE_LOWPASS);
				}
			}
		);
		item.setText("Create Lowpass filter");
		filters.add(item);
		item = new JMenuItem(
			new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					ContainerRightClickMenu.this.create_module(Engine.Constants.MODULE_HIGHPASS);
				}
			}
		);
		item.setText("Create Highpass filter");
		filters.add(item);
		
		module_list.add(filters);
		add(module_list);
		
		
		item = new JMenuItem(
			new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
//					ContainerRightClickMenu.this.main_window.engine.set_frequency(440, 554.365, 659.26); // A C# E (A chord)
					ContainerRightClickMenu.this.main_window.engine.start_playing();
				}
			}
		);
		item.setText("Start Playing");
		add(item);
		
		item = new JMenuItem(
			new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					ContainerRightClickMenu.this.main_window.engine.stop_playing();
				}
			}
		);
		item.setText("Stop Playing");
		add(item);
	}
	
	public void create_module(int type)
	{
		try
		{
			Point a;
			a = mouse_position;
			// +10 so that the module is under the mouse, and not just the topleft corner
//			a.x -= main_window.getInsets().left + 10;
			a.y -= main_window.getInsets().top + main_window.menu.getHeight();
			main_window.add_module(type, a.x, a.y);
//			SwingUtilities.convertPointFromScreen(pos, main_window);
//			// FIXME Magic number
//			pos.x -= main_window.getInsets().left;
//			pos.y -= main_window.getInsets().top + main_window.menu.getHeight() + 4;
//			main_window.add_module(type, pos.x, pos.y);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
