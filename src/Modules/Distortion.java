package Modules;

import Engine.Module;
import Engine.Pipe;

public class Distortion extends Module
{
	public static final int DISTORTION_CLIPPING = 0;
	
	public static final int INPUT_PIPE = 0;
	public static final int LEVEL_PIPE = 1;
	
	public static final int OUTPUT_PIPE = 0;
	
	private double level;
	private int distortion_type;
	
	public Distortion(Container container)
	{
		super(container);
		
		NUM_INPUT_PIPES = 2;
		NUM_OUTPUT_PIPES = 1;
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		distortion_type = DISTORTION_CLIPPING;
		module_type = Engine.Constants.MODULE_DISTORTION;
	}

	@Override
	public void run()
	{
		if (input_pipes[LEVEL_PIPE] != null)
		{
			for (int i=0; i<Engine.Constants.SNAPSHOT_SIZE; i++)
			{
				set_level(input_pipes[LEVEL_PIPE].inner_buffer[i]);
				output_pipes[OUTPUT_PIPE].inner_buffer[i] = distort(input_pipes[INPUT_PIPE].inner_buffer[i]);
			}
		}
	}
	
	private double distort(double x)
	{
		switch (distortion_type)
		{
			case DISTORTION_CLIPPING:
				x *= level;
				if (Math.abs(x) > 1)
				{
					x = Math.signum(x);
				}
				break;
			
			default:
				System.out.println("ERROR: Distortion Module "+index+" has the invalid distortion type "+distortion_type);
		}
		return x;
	}

	public double get_level()
	{
		if (input_pipes[LEVEL_PIPE] != null)
		{
			return input_pipes[LEVEL_PIPE].inner_buffer[0];
		}
		else
		{
			return level;
		}
	}

	public void set_level(double level)
	{
		this.level = level;
	}
	
	public int get_distortiontype()
	{
		return distortion_type;
	}
	
	public void set_distortiontype(int new_type)
	{
		distortion_type = new_type;
	}
}
