package GUI;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class ModuleGUI extends JPanel
{
	// FIXME: There has to be an inbuilt-constant for this
	private int x, y;
	private int portbox_width, portbox_height;
	private Engine.Module module;
	private Image port_icon;
	
	public ModuleGUI(Engine.Module module) throws IOException
	{
		// FIXME: Make all these magic numbers constants or lookups
		portbox_width = 16 + 20*Math.max(module.NUM_INPUT_PIPES, module.NUM_OUTPUT_PIPES) - 10;
		portbox_height = 2*10 + 10;
		
		x = 300;
		y = 300;
		
		Dimension d = new Dimension(portbox_width, portbox_height);
		setMinimumSize(d);
		setPreferredSize(d);
		setSize(d);
		
		this.module = module;
		
		port_icon = new ImageIcon("Sprites/Port.png").getImage();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		// Yay copying from zetcode, section on Painting (see sources file)
		float[] dash3 = { 4f, 0f, 2f };
        BasicStroke bs3 = new BasicStroke(1, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 1.0f, dash3, 2f );	
		
		g2d.setColor(Color.black);
		g2d.drawRect(x, y, portbox_width, portbox_height);
        g2d.drawLine(x, y+portbox_height/2, x+portbox_width, y+portbox_height/2);        
		g2d.setColor(Color.gray);
		g2d.drawRect(x+1, y+1, portbox_width-1, portbox_height-1);
		
		// Drawing sprites
		int tmpx, tmpy;
		// First draw the input ports
		for (int i=0; i<module.NUM_INPUT_PIPES; i++)
		{
			double d = (portbox_width - 10.0*(module.NUM_INPUT_PIPES)) / (module.NUM_INPUT_PIPES + 1.0);
			tmpx = (int) Math.round(d+i*(10+d));
			// Should be 2.5; FIXME: Replace with portbox_height
			tmpy = 3;
			
			g2d.drawImage(port_icon, x+tmpx, y+tmpy, null);
		}
		
		// Then the output ports
		for (int i=0; i<module.NUM_OUTPUT_PIPES; i++)
		{
			double d = (portbox_width - 10.0*(module.NUM_OUTPUT_PIPES)) / (module.NUM_OUTPUT_PIPES + 1.0);
			tmpx = (int) Math.round(d+i*(10+d));
			// Should be 7.5; FIXME: Replace with portbox_height
			tmpy = 17;
			
			g2d.drawImage(port_icon, x+tmpx, y+tmpy, null);
		}
	}
}
