package ModuleGUIs;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import Engine.Module;
import GUI.ContainerWindow;
import GUI.ModuleGUI;
import GUI.ModuleGUI.RightClickMenu;

public class OscillatorGUI extends DefaultModuleGUI
{
	public class OscRightClickMenu extends RightClickMenu
	{
		public OscRightClickMenu(ModuleGUI module_gui, ContainerWindow main_window)
		{
			super(module_gui, main_window);
		}
		
		public void create_menu()
		{
			// Add in the option to change the oscillator type
			JMenu options = new JMenu();
			JMenuItem item;
			item = new JMenuItem(new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					// Change oscillator mode to sine wave
					Modules.Oscillator osc = (Modules.Oscillator) OscRightClickMenu.this.module_gui.module;
					osc.set_osctype(osc.SINE_WAVE);
				}
			}
			);
			item.setText("Sine wave");
			options.add(item);
			
			item = new JMenuItem(new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					// Change oscillator mode to saw wave
					Modules.Oscillator osc = (Modules.Oscillator) OscRightClickMenu.this.module_gui.module;
					osc.set_osctype(osc.SAW_WAVE);
				}
			}
			);
			item.setText("Saw wave");
			options.add(item);
			
			item = new JMenuItem(new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					// Change oscillator mode to square wave
					Modules.Oscillator osc = (Modules.Oscillator) OscRightClickMenu.this.module_gui.module;
					osc.set_osctype(osc.SQUARE_WAVE);
				}
			}
			);
			item.setText("Pulse/Square wave");
			options.add(item);
			
			options.setText("Set Oscillator waveform");
			add(options);
		}
	}
	
	public OscillatorGUI(ContainerWindow main_window, Module module) throws IOException
	{
		super(main_window, module);
	}
	
	public void set_rightclickmenu(ContainerWindow main_window)
	{
		setComponentPopupMenu(new OscRightClickMenu(this, main_window));
	}
}
