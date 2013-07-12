package Modules;

import Engine.Module;
import Engine.Pipe;

/*
 * This class takes an array of data from one limit to another, and normalizes it to a different range
 */

public class RangeModifier extends Module
{
	private double[] range_in;
	private double[] range_out;
	
	private static final int INPUT_PIPE = 0;
	private static final int OUTPUT_PIPE = 0;
	
	public RangeModifier(Container container)
	{
		super(container);
		
		NUM_INPUT_PIPES = 1;
		NUM_OUTPUT_PIPES = 1;
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		input_pipe_types = new int[NUM_INPUT_PIPES];
		output_pipe_types = new int[NUM_OUTPUT_PIPES];
		
		module_type = Engine.Constants.MODULE_RANGEMODIFIER;
		
		MODULE_NAME = "Range Modifier";
		// To store the ranges involved
		range_in = new double[2];
		range_out = new double[2];
	}

	@Override
	public void run(Engine.EngineMaster engine, int channel)
	{
		if (input_pipes[INPUT_PIPE] != null && output_pipes[OUTPUT_PIPE] != null)
		{
			if (input_pipes[INPUT_PIPE].get_type() != output_pipes[OUTPUT_PIPE].get_type())
			{
				System.out.println("Error in RangeModifier "+index+"; Input and output pipes have different types.");
			}
			double ratio, offset;
			// Normalizing
			ratio = (range_out[1] - range_out[0]) / (range_in[1] - range_in[0]);
			offset = range_out[0] - (range_in[0] * ratio);
			if (input_pipes[INPUT_PIPE].get_type() == Engine.Constants.MONO)
			{
				// Mono
				for (int i=0; i<Engine.Constants.SNAPSHOT_SIZE; i++)
				{
					output_pipes[OUTPUT_PIPE].get_pipe(channel)[0][i] = ratio * input_pipes[INPUT_PIPE].get_pipe(channel)[0][i] + offset;
				}
			}
			else
			{
				// Stereo
				for (int i=0; i<Engine.Constants.SNAPSHOT_SIZE; i++)
				{
					output_pipes[OUTPUT_PIPE].get_pipe(channel)[0][i] = ratio * input_pipes[INPUT_PIPE].get_pipe(channel)[0][i] + offset;
					output_pipes[OUTPUT_PIPE].get_pipe(channel)[1][i] = ratio * input_pipes[INPUT_PIPE].get_pipe(channel)[1][i] + offset;
				}
			}
		}
	}

	public double[] get_range_in()
	{
		return range_in;
	}

	public void set_range_in(double[] range_in)
	{
		this.range_in = range_in;
	}
	
	public void set_range_in(double min, double max)
	{
		range_in[0] = min;
		range_in[1] = max;
	}

	public double[] get_range_out()
	{
		return range_out;
	}

	public void set_range_out(double[] range_out)
	{
		this.range_out = range_out;
	}
	
	public void set_range_out(double min, double max)
	{
		range_out[0] = min;
		range_out[1] = max;
	}
}
