package Modules;

import Engine.Module;

public abstract class Distortion extends Module
{
	public static final int INPUT_PIPE = 0;
	public static final int OUTPUT_PIPE = 0;
	
	public abstract double distortion_function(double x);
	
	public Distortion(Container container)
	{
		super(container);

		module_type = Engine.Constants.MODULE_DISTORTION;
		MODULE_NAME = "Default Distortion";
		
		input_pipe_types = new int[NUM_INPUT_PIPES];
		input_pipe_types[INPUT_PIPE] = Engine.Constants.MONO;
		output_pipe_types = new int[NUM_OUTPUT_PIPES];
	}

	@Override
	public void run(int channel)
	{
		if (input_pipes[INPUT_PIPE].get_type() != output_pipes[OUTPUT_PIPE].get_type())
		{
			System.out.println("Error in Distortion Module "+index+"! INPUT_PIPE type is "+input_pipes[INPUT_PIPE].get_type()+" while OUTPUT_PIPE type is "+output_pipes[OUTPUT_PIPE].get_type()+"!");
		}
		else if (input_pipes[INPUT_PIPE].get_type() == Engine.Constants.STEREO)
		{
			// FIXME: This can be done much cleaner
			// Stereo
			for (int i=0; i<Engine.Constants.SNAPSHOT_SIZE; i++)
			{
				output_pipes[OUTPUT_PIPE].get_pipe(channel)[0][i] = Math.min(1, Math.max(-1, distortion_function(input_pipes[INPUT_PIPE].get_pipe(channel)[0][i])));
				output_pipes[OUTPUT_PIPE].get_pipe(channel)[1][i] = Math.min(1, Math.max(-1, distortion_function(input_pipes[INPUT_PIPE].get_pipe(channel)[1][i])));
			}
		}
		else
		{
			// Mono
			for (int i=0; i<Engine.Constants.SNAPSHOT_SIZE; i++)
			{
				output_pipes[OUTPUT_PIPE].get_pipe(channel)[0][i] = Math.min(1, Math.max(-1, distortion_function(input_pipes[INPUT_PIPE].get_pipe(channel)[0][i])));
			}
		}
	}
}
