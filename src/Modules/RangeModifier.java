package Modules;

import Engine.Module;
import Engine.Pipe;

/*
 * This class takes an array of data from one limit to another, and normalizes it to a different range
 */

public class RangeModifier extends Module
{
	private static final int SIGNAL_INPUT = 0;
	private static final int MIN_INPUT = 1;
	private static final int MAX_INPUT = 2;
	private static final int MIN_OUTPUT = 3;
	private static final int MAX_OUTPUT = 4;
	private static final int SIGNAL_OUTPUT = 0;
	
	public RangeModifier(Container container)
	{
		super(container);
		
		NUM_INPUT_PIPES = 5;
		NUM_OUTPUT_PIPES = 1;
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		input_pipe_names = new String[NUM_INPUT_PIPES];
		output_pipe_names = new String[NUM_OUTPUT_PIPES];
		input_pipe_names[SIGNAL_INPUT] = "Signal Input";
		input_pipe_names[MIN_INPUT] = "Min input range";
		input_pipe_names[MAX_INPUT] = "Max input range";
		input_pipe_names[MIN_OUTPUT] = "Min output range";
		input_pipe_names[MAX_OUTPUT] = "Max output range";
		output_pipe_names[SIGNAL_OUTPUT] = "Scaled output";
		
		module_type = Engine.Constants.MODULE_RANGEMODIFIER;
		
		MODULE_NAME = "Range Modifier";
	}

	@Override
	public void run(Engine.EngineMaster engine, int channel)
	{
		boolean everything_connected = true;
		for (int i=0; i<NUM_INPUT_PIPES; i++)
		{
			if (input_pipes[i] == null)
			{
				everything_connected = false;
			}
		}
		for (int i=0; i<NUM_OUTPUT_PIPES; i++)
		{
			if (output_pipes[i] == null)
			{
				everything_connected = false;
			}
		}
		if (everything_connected)
		{
			double ratio, offset;
			for (int side=0; side<audio_mode; side++)
			{
				for (int i=0; i<Engine.Constants.SNAPSHOT_SIZE; i++)
				{
				// Scaling
				ratio = ((input_pipes[MAX_OUTPUT].get_pipe(channel)[side][i] - input_pipes[MIN_OUTPUT].get_pipe(channel)[side][i]) /
						(input_pipes[MAX_INPUT].get_pipe(channel)[side][i] - input_pipes[MIN_INPUT].get_pipe(channel)[side][i]));
				offset = input_pipes[MIN_OUTPUT].get_pipe(channel)[side][i] - (input_pipes[MIN_INPUT].get_pipe(channel)[side][i] * ratio);
				output_pipes[SIGNAL_OUTPUT].get_pipe(channel)[side][i] = ratio * input_pipes[SIGNAL_INPUT].get_pipe(channel)[side][i] + offset;
				}
				
			}
		}
	}
}
