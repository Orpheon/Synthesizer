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
	public JTextField input;
	
	class TextFieldListener implements FocusListener
	{
		@Override
		public void focusGained(FocusEvent e)
		{
			// Select everything
			ConstantGUI.this.input.selectAll();
		}

		@Override
		public void focusLost(FocusEvent e)
		{
			if (ConstantGUI.this.input.getText().isEmpty())
			{
				// Don't allow empty string, put a 0 there
				ConstantGUI.this.input.setText("0");
			}
			
			// Update the value in the engine to the new value
			double value = Double.parseDouble(ConstantGUI.this.input.getText());
			Modules.Constant m = (Modules.Constant) ConstantGUI.this.module;
			m.set_value(value);
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
		input.setText("0");
		input.setVisible(true);
		input.setSize(new Dimension(width, height/2));
		add(input);
		
		input_ports = new PortGUI[module.NUM_INPUT_PIPES];
		output_ports = new PortGUI[module.NUM_OUTPUT_PIPES];
		
		output_ports[0] = new PortGUI(main_window, this, Engine.Constants.OUTPUT_PORT, 0);
		output_ports[0].setLocation((width-10)/2, height/2 + (height/2-10)/2);
		this.add(output_ports[0]);
		
		input.addFocusListener(new TextFieldListener());
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
