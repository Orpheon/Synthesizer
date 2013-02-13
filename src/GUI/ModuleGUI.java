package GUI;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class ModuleGUI extends JPanel
{
	// FIXME: There has to be an inbuilt-constant for this
	private int x, y;
	private int portbox_width, portbox_height;
	private int text_width, text_height;
	private Engine.Module module;
	private Image port_icon;
	
	public ModuleGUI(Engine.Module module) throws IOException
	{
		// FIXME: Make all these magic numbers constants or lookups
		portbox_width = 16 + 20*Math.max(module.NUM_INPUT_PIPES, module.NUM_OUTPUT_PIPES) - 10;
		portbox_height = 2*10 + 10;
		
		// FIXME: Find out how to guess the length of a string
		text_width = module.MODULE_NAME.length()*6+2;
		text_height = 10+2;
		
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
		
        Font font = new Font("Arial", Font.BOLD, text_height-2);
		g2d.setFont(font);
        g2d.drawString(module.MODULE_NAME, x+1, y-1);
        
        int tmpwidth = Math.max(text_width, portbox_width);
        

		g2d.setColor(Color.black);
		g2d.drawRect(x, y - text_height, tmpwidth, text_height+portbox_height);// Implicit 2-point distance to text
		g2d.drawRect(x, y, tmpwidth, portbox_height);
        g2d.drawLine(x, y+portbox_height/2, x+tmpwidth, y+portbox_height/2);        
		// [/Copying]
		
		
		// Drawing sprites
		int tmpx, tmpy;
		// First draw the input ports
		for (int i=0; i<module.NUM_INPUT_PIPES; i++)
		{
			double d = (tmpwidth - 10.0*(module.NUM_INPUT_PIPES)) / (module.NUM_INPUT_PIPES + 1.0);
			tmpx = (int) Math.round(d+i*(10+d));
			// Should be 2.5; FIXME: Replace with portbox_height
			tmpy = 3;
			
			g2d.drawImage(port_icon, x+tmpx, y+tmpy, null);
		}
		
		// Then the output ports
		for (int i=0; i<module.NUM_OUTPUT_PIPES; i++)
		{
			double d = (tmpwidth - 10.0*(module.NUM_OUTPUT_PIPES)) / (module.NUM_OUTPUT_PIPES + 1.0);
			tmpx = (int) Math.round(d+i*(10+d));
			// Should be 7.5; FIXME: Replace with portbox_height
			tmpy = 18;
			
			g2d.drawImage(port_icon, x+tmpx, y+tmpy, null);
		}
	}
}
