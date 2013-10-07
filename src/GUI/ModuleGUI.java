package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.*;

public abstract class ModuleGUI extends JPanel
{
	/**
	 * God knows what this is, but eclipse wants it
	 */
	private static final long serialVersionUID = 4170785623135839381L;

	// FIXME: There has to be an inbuilt-constant for this
	public Engine.Module module;
	protected ModuleAL AL;
	protected String name;
	public PortGUI[] input_ports;
	public PortGUI[] output_ports;
	public int type;
	
	class RightClickMenu extends JPopupMenu
	{
		private ModuleGUI module_gui;
		private ContainerWindow main_window;
		
		public RightClickMenu(ModuleGUI module_gui, ContainerWindow main_window)
		{
			super();
			
			this.module_gui = module_gui;
			this.main_window = main_window;
			JMenuItem item = new JMenuItem(new AbstractAction()
				{
					public void actionPerformed(ActionEvent e)
					{
						// Disconnect all the ports (will also stop the engine)
						for (int i=0; i<RightClickMenu.this.module_gui.input_ports.length; i++)
						{
							if (RightClickMenu.this.module_gui.input_ports[i] != null)
							{
								RightClickMenu.this.module_gui.input_ports[i].disconnect();
							}
						}
						for (int i=0; i<RightClickMenu.this.module_gui.output_ports.length; i++)
						{
							if (RightClickMenu.this.module_gui.output_ports[i] != null)
							{
								RightClickMenu.this.module_gui.output_ports[i].disconnect();
							}
						}
						// Destroy the module
						RightClickMenu.this.main_window.remove_module(RightClickMenu.this.module_gui);
					}
				}
			);
			item.setText("Destroy module");
			add(item);
		}
	}
	
	public ModuleGUI(ContainerWindow main_window, Engine.Module module) throws IOException
	{
		name = module.MODULE_NAME + " " + module.get_index();
		
		this.module = module;
		
		type = Engine.Constants.INVALID_MODULE_GUI;
		
		AL = new ModuleAL(this);
		addMouseMotionListener(AL);
		
		setComponentPopupMenu(new RightClickMenu(this, main_window));
		
		setLayout(new GroupLayout(this));
	}
	
	public ModuleAL getAL()
	{
		return AL;
	}
}
