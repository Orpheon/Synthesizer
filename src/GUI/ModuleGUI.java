package GUI;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class ModuleGUI extends JPanel
{
	/**
	 * God knows what this is, but eclipse wants it
	 */
	private static final long serialVersionUID = 4170785623135839381L;

	// FIXME: There has to be an inbuilt-constant for this
	private int portbox_width, portbox_height;
	private int text_width, text_height;
	private Engine.Module module;
	private ModuleAL AL;
	private String name;
	private PortGUI[] input_ports;
	private PortGUI[] output_ports;
	
	public ModuleGUI(ContainerWindow main_window, Engine.Module module) throws IOException
	{
		name = module.MODULE_NAME + " " + module.get_index();
		
		// FIXME: Find out how to guess the length of a string
		text_width = name.length()*6+2;
		text_height = 10+2;
		
		// FIXME: Make all these magic numbers constants or lookups
		portbox_width = Math.max(16 + 20*Math.max(module.NUM_INPUT_PIPES, module.NUM_OUTPUT_PIPES) - 10, text_width);
		portbox_height = 2*10 + 10;
		
		Dimension d = new Dimension(portbox_width, portbox_height+text_height);
		setMinimumSize(d);
		setPreferredSize(d);
		setSize(d);
		
		this.setLocation(300, 300);
		
		this.module = module;
		
		AL = new ModuleAL(this);
		addMouseMotionListener(AL);
		
		// Creating the ports
		int tmpx, tmpy;
		input_ports = new PortGUI[module.NUM_INPUT_PIPES];
		output_ports = new PortGUI[module.NUM_OUTPUT_PIPES];
		// First create the input ports
		for (int i=0; i<module.NUM_INPUT_PIPES; i++)
		{
			double a = (portbox_width - 10.0*(module.NUM_INPUT_PIPES)) / (module.NUM_INPUT_PIPES + 1.0);
			tmpx = (int) Math.round(a+i*(10+a));
			// Should be 2.5; FIXME: Replace with portbox_height
			tmpy = 3;
			
			input_ports[i] = new PortGUI(main_window, module, Engine.Constants.INPUT_PORT, i);
			input_ports[i].setLocation(tmpx, tmpy + text_height);
			System.out.println("Input port position: "+input_ports[i].getX()+"|"+input_ports[i].getY());
			this.add(input_ports[i]);
		}
		
		// Then the output ports
		for (int i=0; i<module.NUM_OUTPUT_PIPES; i++)
		{
			double a = (portbox_width - 10.0*(module.NUM_OUTPUT_PIPES)) / (module.NUM_OUTPUT_PIPES + 1.0);
			tmpx = (int) Math.round(a+i*(10+a));
			// Should be 7.5; FIXME: Replace with portbox_height
			tmpy = 18;
			
			output_ports[i] = new PortGUI(main_window, module, Engine.Constants.OUTPUT_PORT, i);
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
        BasicStroke bs3 = new BasicStroke(1, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 1.0f, dash3, 2f );	
		
        Font font = new Font("Arial", Font.BOLD, text_height-2);
		g2d.setFont(font);
        g2d.drawString(name, 1, text_height-1);
        
		g2d.setColor(Color.black);
		g2d.drawRect(0, 0, portbox_width-1, text_height+portbox_height-1);// Implicit 2-point distance to text
		g2d.drawRect(0, text_height, portbox_width, portbox_height);
        g2d.drawLine(0, text_height+portbox_height/2, portbox_width, text_height+portbox_height/2);        
		// [/Copying]
	}
	
//	public void paintComponent(Graphics g)
//	{
//		super.paintComponent(g);
//		
//		Graphics2D g2d = (Graphics2D) g;
//		
//		// Yay copying from zetcode, section on Painting (see sources file)
//		float[] dash3 = { 4f, 0f, 2f };
//        BasicStroke bs3 = new BasicStroke(1, BasicStroke.CAP_BUTT,
//                BasicStroke.JOIN_ROUND, 1.0f, dash3, 2f );	
//		
//        Font font = new Font("Arial", Font.BOLD, text_height-2);
//		g2d.setFont(font);
//        g2d.drawString(module.MODULE_NAME, x+1, y-1);
//        
//        int portbox_width = Math.max(text_width, portbox_width);
//        
//        
//		g2d.setColor(Color.black);
//		g2d.drawRect(x, y - text_height, portbox_width, text_height+portbox_height);// Implicit 2-point distance to text
//		g2d.drawRect(x, y, portbox_width, portbox_height);
//        g2d.drawLine(x, y+portbox_height/2, x+portbox_width, y+portbox_height/2);        
//		// [/Copying]
//		
//		
//		// Drawing sprites
//		int tmpx, tmpy;
//		// First draw the input ports
//		for (int i=0; i<module.NUM_INPUT_PIPES; i++)
//		{
//			double d = (portbox_width - 10.0*(module.NUM_INPUT_PIPES)) / (module.NUM_INPUT_PIPES + 1.0);
//			tmpx = (int) Math.round(d+i*(10+d));
//			// Should be 2.5; FIXME: Replace with portbox_height
//			tmpy = 3;
//			
//			g2d.drawImage(port_icon, x+tmpx, y+tmpy, null);
//		}
//		
//		// Then the output ports
//		for (int i=0; i<module.NUM_OUTPUT_PIPES; i++)
//		{
//			double d = (portbox_width - 10.0*(module.NUM_OUTPUT_PIPES)) / (module.NUM_OUTPUT_PIPES + 1.0);
//			tmpx = (int) Math.round(d+i*(10+d));
//			// Should be 7.5; FIXME: Replace with portbox_height
//			tmpy = 18;
//			
//			g2d.drawImage(port_icon, x+tmpx, y+tmpy, null);
//		}
//	}
	
	public ModuleAL getAL()
	{
		return AL;
	}
}
