package GUI;

import java.awt.*;
import javax.swing.*;

public class ContainerGUI extends JFrame
{
	/**
	 * No idea what this is
	 */
	private static final long serialVersionUID = 2466772835685193131L;
	
	private Modules.Container container;

	public ContainerGUI(Modules.Container container)
	{
		init_GUI(container, 900, 600);
	}
	
	private void init_GUI(Modules.Container container, int window_width, int window_height)
	{
		// Create a new panel to draw stuff on
        JPanel panel = new JPanel(new FlowLayout());
        add(panel);

        // Basic GUI stuff
        setTitle("");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // FIXME: Don't hardcode position
        setBounds(683 - window_width/2, 384 - window_height/2, window_width, window_height);
        
        // Remember what container we belong to
        this.container = container;
	}
}
