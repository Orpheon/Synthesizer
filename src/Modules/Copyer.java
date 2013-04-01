package Modules;

import Engine.Module;
import Engine.Pipe;
import Engine.Constants;

public class Copyer extends Module
{
	public static final int INPUT_PIPE = 0;
	
	public Copyer(Container container)
	{
		super(container);
		
		NUM_INPUT_PIPES = 1;
		NUM_OUTPUT_PIPES = 2;
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		module_type = Engine.Constants.MODULE_COPYER;
		
		MODULE_NAME = "Splitter";
	}
	
	public Copyer(Container container, int num_outputs)
	{
		super(container);
		
		NUM_INPUT_PIPES = 1;
		NUM_OUTPUT_PIPES = num_outputs;
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		MODULE_NAME = "Splitter";
	}

	@Override
	public void run(int channel)
	{
		if (input_pipes[INPUT_PIPE] != null)
		{
			for (int i=0; i<NUM_OUTPUT_PIPES; i++)
			{
				if (input_pipes[INPUT_PIPE].get_type() != output_pipes[i].get_type())
				{
					System.out.println("Error in Copyer "+index+"; Input pipe and output pipe "+i+" have different types.");
					continue;
				}
				
				if (output_pipes[i] != null)
				{
					// Copy the input directly in the output
					System.arraycopy(input_pipes[INPUT_PIPE].get_pipe(channel)[0], 0, output_pipes[i].get_pipe(channel)[0], 0, Constants.SNAPSHOT_SIZE);
					if (input_pipes[INPUT_PIPE].get_type() == Engine.Constants.STEREO)
					{
						System.arraycopy(input_pipes[INPUT_PIPE].get_pipe(channel)[1], 0, output_pipes[i].get_pipe(channel)[1], 0, Constants.SNAPSHOT_SIZE);
					}
				}
			}
		}
	}
	
	public int get_num_outputs()
	{
		return NUM_OUTPUT_PIPES;
	}
	
	public void set_num_outputs(int num)
	{
		// No need to change anything if there's no change
		if (num != NUM_OUTPUT_PIPES)
		{
			// If we want to lower the number of connections, then disconnect all the superfluous ones
			if (NUM_OUTPUT_PIPES > num)
			{ 
				for (int i=num; i<NUM_OUTPUT_PIPES; i++)
				{
					if (output_pipes[i] != null)
					{
						disconnect_output(i);
					}
				}
			}
			NUM_OUTPUT_PIPES = num;
			Pipe[] tmp = new Pipe[NUM_OUTPUT_PIPES];
			System.arraycopy(output_pipes, 0, tmp, 0, Math.min(output_pipes.length, NUM_OUTPUT_PIPES));
			output_pipes = tmp;
		}
	}
}
