package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class ContainerWindow extends JFrame
{
	/**
	 * No idea what this is
	 */
	private static final long serialVersionUID = 2466772835685193131L;
	
	private Modules.Container container;
	
	// FIXME: Remove this
	private ModuleGUI m;
	private JPanel central_container;
	
	private static final int DEFAULT_WINDOW_WIDTH = 900;
	private static final int DEFAULT_WINDOW_HEIGHT = 600;
	
	private JMenu menu;
	
	public ContainerWindow(Modules.Container container) throws IOException
	{
		this(container, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
	}
	
	public ContainerWindow(Modules.Container container, int window_width, int window_height) throws IOException
	{
		// We keep a pointer to the our container
		this.container = container;

        // Basic GUI stuff
        setTitle("");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // FIXME: Don't hardcode position
        setBounds(683 - window_width/2, 384 - window_height/2, window_width, window_height);
        setLayout(new BorderLayout());
        
        // TODO: Flesh out menu
        JMenuBar menu_bar;
        menu_bar = new JMenuBar();
        menu = new JMenu("Testing");
        menu.add(new JMenuItem("Mc Chicken"));
        menu_bar.add(menu);
        add(menu_bar, BorderLayout.PAGE_START);

        central_container = new JPanel();
        add(central_container, BorderLayout.CENTER);
        central_container.setLayout(null);
        
        this.m = new ModuleGUI(new Modules.Oscillator(container));
        central_container.add(m);
	}
}
