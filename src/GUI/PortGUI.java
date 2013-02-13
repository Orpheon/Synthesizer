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
	
	public PortGUI(Engine.Module module, int port_number)
	{
		this.module = module;
		this.port_number = port_number;
	}
	// TODO: I need a this.getPosition() function
}
