package Modules;

import Engine.Module;
import Engine.Pipe;

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
		
		module_type = Engine.Constants.MODULE_RANGEMODIFIER;
		
		range_in = new double[2];
		range_out = new double[2];
	}

	@Override
	public void run()
	{
		if (input_pipes[INPUT_PIPE] != null && output_pipes[OUTPUT_PIPE] != null)
		{
			double ratio, offset;
			ratio = (range_out[1] - range_out[0]) / (range_in[1] - range_in[0]);
			offset = range_out[0] - (range_in[0] * ratio);
			for (int i=0; i<Engine.Constants.SNAPSHOT_SIZE; i++)
			{
				output_pipes[OUTPUT_PIPE].inner_buffer[i] = ratio * input_pipes[INPUT_PIPE].inner_buffer[i] + offset;
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
