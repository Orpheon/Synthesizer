package Modules;

import Engine.Module;
import Engine.Pipe;

public abstract class Distortion extends Module
{
	public static final int INPUT_PIPE = 0;
	public static final int OUTPUT_PIPE = 0;
	
	public abstract double distortion_function(int i);
	
	public Distortion(Container container)
	{
		super(container);

		module_type = Engine.Constants.MODULE_DISTORTION;
	}

	@Override
	public void run()
	{
		for (int i=0; i<Engine.Constants.SNAPSHOT_SIZE; i++)
		{
			output_pipes[OUTPUT_PIPE].inner_buffer[i] = Math.min(1, Math.max(-1, input_pipes[INPUT_PIPE].inner_buffer[i] * distortion_function(i)));
		}
	}
}
