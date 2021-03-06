package ModuleGUIs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import Engine.Constants;
import Engine.Functions;
import Engine.InputModule;
import Engine.Module;
import GUI.ContainerWindow;
import GUI.ModuleGUI;
import GUI.PortGUI;
import GUI.ModuleGUI.RightClickMenu;
import ModuleGUIs.OscillatorGUI.OscRightClickMenu;

public class InputGUI extends ModuleGUI
{
	public int width;
	public int height;
	protected int text_width;
	protected int text_height;
	
	public class InputRightClickMenu extends RightClickMenu
	{
		class MenuCreater implements PopupMenuListener
		{
			private InputRightClickMenu rightclickmenu;
			
			public MenuCreater(InputRightClickMenu r)
			{
				rightclickmenu = r;
			}
			
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e)
			{
				// Recreate the menu
				rightclickmenu.create_menu();
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
		
		public InputRightClickMenu(ModuleGUI module_gui, ContainerWindow main_window)
		{
			super(module_gui, main_window);
			
			addPopupMenuListener(new MenuCreater(this));
		}
		
		public void create_menu()
		{
			// First remove everything
			int count = getComponentCount();
			for (int i=0; i<count; i++)
			{
				remove(getComponent(0));
			}

			JMenuItem item;
			item = new JMenuItem(new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					String input = JOptionPane.showInputDialog("Please enter the desired note (ex. A4)");
					InputRightClickMenu.this.main_window.engine.add_frequency(Functions.convert_note2frequency(input));
				}
			}
			);
			item.setText("Add new note");
			add(item);

			JMenu menu = new JMenu();
			menu.setText("Remove existing note");

			Engine.InputModule m = (InputModule) module_gui.module;
			for (int i=0; i<Constants.NUM_CHANNELS; i++)
			{
				if (m.get_activation_times()[i] >= 0)
				{
					AbstractAction a = new AbstractAction()
					{
						public void actionPerformed(ActionEvent e)
						{
							InputRightClickMenu.this.main_window.engine.remove_frequency((Integer) this.getValue("frequency_index"));
						}
					};
					a.putValue("frequency_index", i);
					item = new JMenuItem(a);
					item.setText(Functions.convert_frequency2note(m.get_frequencies()[i]));
					menu.add(item);
				}
			}
			
			add(menu);
			
			item = new JMenuItem(new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					// Disconnect all the ports (will also stop the engine)
					for (int i=0; i<InputRightClickMenu.this.module_gui.input_ports.length; i++)
					{
						if (InputRightClickMenu.this.module_gui.input_ports[i] != null)
						{
							InputRightClickMenu.this.module_gui.input_ports[i].disconnect();
						}
					}
					for (int i=0; i<InputRightClickMenu.this.module_gui.output_ports.length; i++)
					{
						if (InputRightClickMenu.this.module_gui.output_ports[i] != null)
						{
							InputRightClickMenu.this.module_gui.output_ports[i].disconnect();
						}
					}
					// Destroy the module
					InputRightClickMenu.this.main_window.remove_module(InputRightClickMenu.this.module_gui);
				}
			}
			);
			item.setText("Destroy module");
			add(item);
		}
	}
	
	public InputGUI(ContainerWindow main_window, Module module) throws IOException
	{
		super(main_window, module);
		
		width = 50;
		height = 50;
		
		name = "Frequency Input";
		type = Engine.Constants.INPUT_MODULE_GUI;
		
		// FIXME: Find out how to guess the length of a string
		text_width = name.length()*6+2;
		text_height = 10+2;
		
		// FIXME: Make all these magic numbers constants or lookups
		width = Math.max(26, text_width);
		height = 2*10 + 10;
		
		Dimension d = new Dimension(width, height+text_height);
		setMinimumSize(d);
		setPreferredSize(d);
		setSize(d);
		
		// Creating the ports
		int tmpx, tmpy;
		input_ports = new PortGUI[0];
		output_ports = new PortGUI[1];

		double a = (width - 10.0) / (2.0);
		tmpx = (int) Math.round(a);
		// Should be 2.5; FIXME: Replace with height
		tmpy = height/2 - 5;
		
		output_ports[0] = new PortGUI(main_window, this, Engine.Constants.OUTPUT_PORT, 0);
		output_ports[0].setLocation(tmpx, tmpy + text_height);
		add(output_ports[0]);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.black);
		
		// Yay copying from zetcode, section on Painting (see sources file)
		float[] dash3 = { 4f, 0f, 2f };
        BasicStroke bs3 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash3, 2f );	
		
        Font font = new Font("Arial", Font.BOLD, text_height-2);
		g2d.setFont(font);
        g2d.drawString(name, 1, text_height-1);
        
		g2d.setColor(Color.black);
		g2d.drawRect(0, 0, width-1, text_height+height-1);// Implicit 2-point distance to text
		g2d.drawRect(0, text_height, width, height);  
		// [/Copying]
		
		int tmpx, tmpy;

		double a = (width - 10.0) / (2.0);
		tmpx = (int) Math.round(a);
		// Should be 2.5; FIXME: Replace with height
		tmpy = height/2 - 5;
		output_ports[0].setLocation(tmpx, tmpy + text_height);
	}
	
	public void set_rightclickmenu(ContainerWindow main_window)
	{
		setComponentPopupMenu(new InputRightClickMenu(this, main_window));
	}
}
