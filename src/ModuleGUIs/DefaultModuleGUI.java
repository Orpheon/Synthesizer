package ModuleGUIs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import Engine.Module;
import GUI.ContainerRightClickMenu;
import GUI.ContainerWindow;
import GUI.ModuleGUI;
import GUI.PortGUI;

public class DefaultModuleGUI extends ModuleGUI
{
	protected int width, height;
	protected int text_width, text_height;
	
	public DefaultModuleGUI(ContainerWindow main_window, Module module) throws IOException
	{
		super(main_window, module);
		
		// FIXME: Find out how to guess the length of a string
		text_width = name.length()*6+2;
		text_height = 10+2;
		
		// FIXME: Make all these magic numbers constants or lookups
		width = Math.max(16 + 20*Math.max(module.NUM_INPUT_PIPES, module.NUM_OUTPUT_PIPES) - 10, text_width);
		height = 2*10 + 10;
		
		Dimension d = new Dimension(width, height+text_height);
		setMinimumSize(d);
		setPreferredSize(d);
		setSize(d);
		
		type = Engine.Constants.DEFAULT_MODULE_GUI;
		
		// Creating the ports
		int tmpx, tmpy;
		input_ports = new PortGUI[module.NUM_INPUT_PIPES];
		output_ports = new PortGUI[module.NUM_OUTPUT_PIPES];
		// First create the input ports
		for (int i=0; i<module.NUM_INPUT_PIPES; i++)
		{
			double a = (width - 10.0*(module.NUM_INPUT_PIPES)) / (module.NUM_INPUT_PIPES + 1.0);
			tmpx = (int) Math.round(a+i*(10+a));
			// Should be 2.5; FIXME: Replace with height
			tmpy = 3;
			
			input_ports[i] = new PortGUI(main_window, this, Engine.Constants.INPUT_PORT, i);
			input_ports[i].setLocation(tmpx, tmpy + text_height);
			this.add(input_ports[i]);
		}
		
		// Then the output ports
		for (int i=0; i<module.NUM_OUTPUT_PIPES; i++)
		{
			double a = (width - 10.0*(module.NUM_OUTPUT_PIPES)) / (module.NUM_OUTPUT_PIPES + 1.0);
			tmpx = (int) Math.round(a+i*(10+a));
			// Should be 7.5; FIXME: Replace with height
			tmpy = 18;
			
			output_ports[i] = new PortGUI(main_window, this, Engine.Constants.OUTPUT_PORT, i);
			output_ports[i].setLocation(tmpx, tmpy + text_height);
			this.add(output_ports[i]);
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.black);
		
		// Yay copying from zetcode, section on Painting (see sources file)
		float[] dash3 = { 4f, 0f, 2f };
        BasicStroke bs3 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash3, 2f );	
		
        Font font = new Font("Arial", Font.BOLD, text_height-2);
		g2d.setFont(font);
        g2d.drawString(name, 1, text_height-1);
        
		g2d.setColor(Color.black);
		g2d.drawRect(0, 0, width-1, text_height+height-1);// Implicit 2-point distance to text
		g2d.drawRect(0, text_height, width, height);
        g2d.drawLine(0, text_height+height/2, width, text_height+height/2);        
		// [/Copying]
	}
}
