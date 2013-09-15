package GUI;

import java.awt.*;

import javax.swing.*;

import Engine.Module;

public class PortGUI extends JButton
{
	/**
	 * TODO: Go figure out what this is
	 */
	private static final long serialVersionUID = 6594761194013281486L;
	
	private final static ImageIcon port_image = new ImageIcon("Sprites/Port.png");
	private ModuleGUI module_gui;
	public Engine.Module module;
	private int port_number;
	
	public int port_type;
	public PortGUI connection;
	private PortAL AL;
	
	private ContainerWindow main_window;
	
	public PortGUI(ContainerWindow main_window, ModuleGUI module_gui, int port_type, int port_number)
	{
		super(port_image);
		
		this.module_gui = module_gui;
		this.module = module_gui.module;
		this.port_type = port_type;
		this.port_number = port_number;
		connection = null;

		if (port_type == Engine.Constants.INPUT_PORT)
		{
			setToolTipText(module.input_pipe_names[port_number]);
		}
		else
		{
			setToolTipText(module.output_pipe_names[port_number]);
		}
		
		
		setVisible(true);
		setSize(new Dimension(port_image.getIconWidth(), port_image.getIconHeight()));
		
		AL = new PortAL(main_window, this);
		addMouseListener(AL);
		
		this.main_window = main_window;
	}
	
	public void disconnect()
	{
		main_window.engine.stop_playing();
		
		if (port_type == Engine.Constants.INPUT_PORT)
		{
			disconnect_input(module_gui);
			connection.disconnect_output(connection.module_gui);
		}
		else
		{
			connection.disconnect_input(connection.module_gui);
			disconnect_output(module_gui);
		}
		
		connection.connection = null;
		connection = null;
	}
	
	private void disconnect_input(ModuleGUI gui)
	{
		gui.module.disconnect_input(port_number);
	}
	
	private void disconnect_output(ModuleGUI gui)
	{
		gui.module.disconnect_output(port_number);
	}
	
	public void attempt_connection(PortGUI other)
	{
		if (this.port_type != other.port_type)
		{
			PortGUI input;
			PortGUI output;
			if (this.port_type == Engine.Constants.INPUT_PORT)
			{
				input = this;
				output = other;
			}
			else
			{
				input = other;
				output = this;
			}
			
			if (input.module != output.module)
			{
				if (input.module.get_audio_mode() == output.module.get_audio_mode())
				{
					if (this.connection != null)
					{
						disconnect();
					}
					main_window.container.connect_modules(output.module, output.port_number, input.module, input.port_number, input.module.get_audio_mode());
					input.connection = output;
					output.connection = input;
				}
			}
		}
	}
}
