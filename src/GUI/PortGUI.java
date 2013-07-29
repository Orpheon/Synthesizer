package GUI;

import java.awt.*;
import javax.swing.*;

public class PortGUI extends JButton
{
	/**
	 * TODO: Go figure out what this is
	 */
	private static final long serialVersionUID = 6594761194013281486L;
	
	private final static Image port_image = new ImageIcon("Sprites/Port.png").getImage();
	private Engine.Module module;
	private int port_number;
	
	public int port_type;
	public PortGUI connection;
	
	public PortGUI(Engine.Module module, int port_type, int port_number)
	{
		super((Icon) port_image);
		
		this.module = module;
		this.port_type = port_type;
		this.port_number = port_number;
		connection = null;
	}
	
	public void disconnect()
	{
		if (port_type == Engine.Constants.INPUT_PORT)
		{
			module.disconnect_input(port_number);
			connection.module.disconnect_output(connection.port_number);
		}
		else
		{
			module.disconnect_output(port_number);
			connection.module.disconnect_input(connection.port_number);
		}
		connection.connection = null;
		connection = null;
	}
	
	public void attempt_connection(PortGUI other)
	{
		
	}
}
