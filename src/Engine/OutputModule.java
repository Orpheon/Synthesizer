package Engine;

import Modules.Container;

public class OutputModule extends Module
{
	public OutputModule(Container container)
	{
		super(container);
		
		NUM_INPUT_PIPES = 1;
		NUM_OUTPUT_PIPES = 0;
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		input_pipe_names = new String[NUM_INPUT_PIPES];
		output_pipe_names = new String[NUM_OUTPUT_PIPES];
		input_pipe_names[0] = "Sound sink";
		
		module_type = Engine.Constants.MODULE_INPUT;
		
		MODULE_NAME = "Input";
	}

	@Override
	public void run(EngineMaster engine, int channel)
	{
		// Don't actually do anything, just be there allowing the engine to come and pick up sound from a pipe.
	}
}
