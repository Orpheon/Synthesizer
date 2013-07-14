package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.*;

public class ContainerWindow extends JFrame
{
	/**
	 * No idea what this is
	 */
	private static final long serialVersionUID = 2466772835685193131L;
	
	private Modules.Container container;
	
	// FIXME: Remove this
	private LinkedList<ModuleGUI> module_list;
	private JPanel central_container;
	
	private static final int DEFAULT_WINDOW_WIDTH = 900;
	private static final int DEFAULT_WINDOW_HEIGHT = 600;
	
	private ContainerRightClickMenu popup_menu;
	
	private JMenu menu;
	
	// Rightclick menu listener
	class PopupListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			if (e.isPopupTrigger())
			{
				popup_menu.open(e.getLocationOnScreen());
			}
			else
			{
				popup_menu.hide();
			}
		}
		
		public void mouseReleased(MouseEvent e)
		{
			if (e.isPopupTrigger())
			{
				popup_menu.open(e.getLocationOnScreen());
			}
		}
	}
	
	public ContainerWindow(Modules.Container container) throws IOException
	{
		this(container, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
	}
	
	public ContainerWindow(Modules.Container container, int window_width, int window_height) throws IOException
	{
		// We keep a pointer to the our container
		this.container = container;
		
		module_list = new LinkedList<ModuleGUI>();

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
        
        // Rightclick menu event catcher
        central_container.addMouseListener(new PopupListener());
        popup_menu = new ContainerRightClickMenu(this);
	}
	
	public void add_module(int type, int x, int y) throws IOException
	{
		Engine.Module m = container.add_module(type);
		ModuleGUI m_gui = new ModuleGUI(m);
		module_list.add(m_gui);
		central_container.add(m_gui);
		m_gui.setLocation(x, y);
		System.out.println(m_gui.getName());
	}
}
