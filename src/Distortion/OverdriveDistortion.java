package Distortion;

import Engine.Pipe;
import Modules.Container;
import Modules.Distortion;

public class OverdriveDistortion extends Distortion
{
	public static final int LEVEL_PIPE = 1;
	
	private double level = 1;
	
	public OverdriveDistortion(Container container)
	{
		super(container);
		
		NUM_INPUT_PIPES = 2;
		NUM_OUTPUT_PIPES = 1;

		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
	}

	@Override
	public double distortion_function(int i)
	{
		if (input_pipes[LEVEL_PIPE] == null)
		{
			return level;
		}
		else
		{
			return input_pipes[LEVEL_PIPE].inner_buffer[i];
		}
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
