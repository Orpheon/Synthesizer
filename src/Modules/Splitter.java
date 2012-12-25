package Modules;

import Engine.EngineMaster;
import Engine.Module;
import Engine.Pipe;
import Engine.Constants;

public class Splitter extends Module
{
	public Splitter(EngineMaster engine, int num_outputs)
	{
		super(engine);
		
		NUM_INPUT_PIPES = 1;
		NUM_OUTPUT_PIPES = num_outputs;
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
	}

	@Override
	public void get_sound()
	{
		if (input_pipes[0] != null)
		{
			for (int i=0; i<NUM_OUTPUT_PIPES; i++)
			{
				if (output_pipes[0] != null)
				{
					// Copy the input directly in the output
					System.arraycopy(input_pipes[0].inner_buffer, 0, output_pipes[i].inner_buffer, 0, Constants.SNAPSHOT_SIZE);
				}
			}
		}
	}

}
