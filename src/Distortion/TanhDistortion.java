package Distortion;

import Engine.Pipe;
import Modules.Container;
import Modules.Distortion;

public class TanhDistortion extends Distortion
{
	public static final int LEVEL_PIPE = 1;
	
	private double level = 1;
	
	public TanhDistortion(Container container)
	{
		super(container);
		
		NUM_INPUT_PIPES = 2;
		NUM_OUTPUT_PIPES = 1;

		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		MODULE_NAME = "Tanh Distortion";
	}

	@Override
	public double distortion_function(double x)
	{
		if (input_pipes[LEVEL_PIPE] == null)
		{
			return 2/Math.PI * Math.tanh(x*level);
		}
		System.out.println("Error: input_pipes[LEVEL_PIPE] in Tanh distortion module wasn't implemented yet.");
		return x;
		// TODO: Deal with LEVEL_PIPE
	}

	public double get_level()
	{
		return level;
	}

	public void set_level(double level)
	{
		this.level = level;
	}

}
