package GUI;

import java.awt.*;
import java.awt.event.*;

public class PortAL implements MouseListener
{
	private ContainerWindow main_window;
	private PortGUI owner;
	
	public PortAL(ContainerWindow main_window, PortGUI owner)
	{
		this.main_window = main_window;
		this.owner = owner;
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (main_window.is_connecting)
		{
			// Connect to something else
			owner.attempt_connection(main_window.first_port);
			main_window.first_port = null;
		}
		else
		{
			if (owner.connection != null)
			{
				// We are already connected, but the user wants to take out the pipe
				main_window.first_port = owner.connection;
				owner.disconnect();
			}
			else
			{
				// Get ready to connect to something
				main_window.first_port = owner;
			}
		}

		// All cases require an inversion of main_window.is_connecting
		main_window.is_connecting = !main_window.is_connecting;
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// Pass
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// Pass
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// Pass
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// Pass
	}
}
