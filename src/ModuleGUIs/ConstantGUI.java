package ModuleGUIs;

import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Engine.Module;
import GUI.ContainerWindow;
import GUI.ModuleGUI;
import GUI.PortGUI;

public class ConstantGUI extends ModuleGUI
{
	protected int width;
	protected int height;
	public Modules.Constant module;
	public JFormattedTextField input;
	
	class textFieldListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			ConstantGUI.this.module.value = (Double) ConstantGUI.this.input.getValue();
		}
	}
	
	public ConstantGUI(ContainerWindow main_window, Module module) throws IOException
	{
		super(main_window, module);
		
		width = 50;
		height = 50;
		
		setSize(new Dimension(width, height));
		
		type = Engine.Constants.CONSTANT_MODULE_GUI;
		
		input = new JFormattedTextField();
		Modules.Constant tmp = (Modules.Constant) module;
		input.setValue(tmp.value);
		input.setVisible(true);
		input.setSize(new Dimension(width, height/2));
		add(input);
		
		input_ports = new PortGUI[module.NUM_INPUT_PIPES];
		output_ports = new PortGUI[module.NUM_OUTPUT_PIPES];
		
		output_ports[0] = new PortGUI(main_window, this, Engine.Constants.OUTPUT_PORT, 0);
		output_ports[0].setLocation((width-10)/2, height/2 + (height/2-10)/2);
		this.add(output_ports[0]);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.black);
		
		// Yay copying from zetcode, section on Painting (see sources file)
		float[] dash3 = { 4f, 0f, 2f };
        BasicStroke bs3 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash3, 2f );
        
		g2d.setColor(Color.black);
		g2d.drawRect(0, 0, width-2, height-1);
		g2d.drawRect(0, 0, width, height);
        g2d.drawLine(0, height/2, width, height/2);
		// [/Copying]
	}
}
