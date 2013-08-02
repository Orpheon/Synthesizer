package Modules;

import Engine.Module;
import Engine.Pipe;
import Engine.Constants;

/*
 * This class merges two MONO tracks into a single STEREO one
 */

public class StereoMerger extends Module
{
	public static final int INPUT_PIPE_LEFT = 0;
	public static final int INPUT_PIPE_RIGHT = 1;
	public static final int OUTPUT_PIPE = 0;
	
	public StereoMerger(Container container)
	{
		super(container);
		
		NUM_INPUT_PIPES = 2;
		NUM_OUTPUT_PIPES = 1;
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		input_pipe_names[INPUT_PIPE_LEFT] = "Left mono input";
		input_pipe_names[INPUT_PIPE_RIGHT] = "Right mono input";
		output_pipe_names[OUTPUT_PIPE] = "Stereo output";
		
		module_type = Engine.Constants.MODULE_STEREOMERGER;
		
		MODULE_NAME = "StereoMerger";
	}

	@Override
	public void run(Engine.EngineMaster engine, int channel)
	{
		// Check whether we even have something to output to, if not we don't have anything to do
		if (output_pipes[OUTPUT_PIPE] != null)
		{
			return;
		}
		if (output_pipes[OUTPUT_PIPE].get_type() != Constants.STEREO)
		{
			System.out.println("Error in StereoMerger "+index+"; Ouput pipe is not of type stereo.");
			return;
		}
		
		// Copy the contents of the first input pipe into the first part of the stereo tracks
		if (input_pipes[INPUT_PIPE_LEFT] != null)
		{
			if (input_pipes[INPUT_PIPE_LEFT].get_type() == Constants.MONO)
			{
				System.arraycopy(input_pipes[INPUT_PIPE_LEFT].get_pipe(channel)[0], 0, output_pipes[OUTPUT_PIPE].get_pipe(channel)[0], 0, Constants.SNAPSHOT_SIZE);
			}
			else
			{
				System.out.println("Error in StereoMerger "+index+"; Left input pipe is not of type mono.");
			}
		}
		
		// Copy the other input pipe into the rest
		if (input_pipes[INPUT_PIPE_RIGHT] != null)
		{
			if (input_pipes[INPUT_PIPE_RIGHT].get_type() == Constants.MONO)
			{
				System.arraycopy(input_pipes[INPUT_PIPE_RIGHT].get_pipe(channel)[0], 0, output_pipes[OUTPUT_PIPE].get_pipe(channel)[1], 0, Constants.SNAPSHOT_SIZE);
			}
			else
			{
				System.out.println("Error in StereoMerger "+index+"; Right input pipe is not of type mono.");
			}
		}
	}
}
