package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class ContainerGUI extends JFrame
{
	/**
	 * No idea what this is
	 */
	private static final long serialVersionUID = 2466772835685193131L;
	
	private Modules.Container container;
	
	// FIXME: Remove this
	private ModuleGUI m;
	
	private static final int DEFAULT_WINDOW_WIDTH = 900;
	private static final int DEFAULT_WINDOW_HEIGHT = 600;
	
	public ContainerGUI(Modules.Container container) throws IOException
	{
		this(container, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);		
	}
	
	public ContainerGUI(Modules.Container container, int window_width, int window_height) throws IOException
	{
		// We keep a pointer to the our container
		this.container = container;

        // Basic GUI stuff
        setTitle("");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // FIXME: Don't hardcode position
        setBounds(683 - window_width/2, 384 - window_height/2, window_width, window_height);
        
        this.m = new ModuleGUI(new Modules.Splitter(container));
        add(m);
	}
}
