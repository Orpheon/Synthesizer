package Modules;

import Engine.Module;
import Engine.Pipe;
import Engine.Constants;

public class Copyer extends Module
{
	public static final int INPUT_PIPE = 0;
	
	public Copyer()
	{
		super();
		
		NUM_INPUT_PIPES = 1;
		NUM_OUTPUT_PIPES = 2;
		
		initialize();
	}
	
	public Copyer(int num_outputs)
	{
		super();
		
		NUM_INPUT_PIPES = 1;
		NUM_OUTPUT_PIPES = num_outputs;

		initialize();
	}

	public void initialize()
	{
		input_pipe_names = new String[NUM_INPUT_PIPES];
		output_pipe_names = new String[NUM_OUTPUT_PIPES];
		input_pipe_names[0] = "Input to be duplicated";
		output_pipe_names[0] = "Output copy 1";
		output_pipe_names[1] = "Output copy 2";
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		MODULE_NAME = "Copyer";
	}
	
	@Override
	public void run(Engine.EngineMaster engine, int channel)
	{
		if (input_pipes[INPUT_PIPE] != null)
		{
			for (int i=0; i<NUM_OUTPUT_PIPES; i++)
			{
				if (output_pipes[i] != null)
				{
					for (int side=0; side<audio_mode; side++)
					{
						// Copy the input directly in the output
						System.arraycopy(input_pipes[INPUT_PIPE].get_pipe(channel)[side], 0, output_pipes[i].get_pipe(channel)[side], 0, Constants.SNAPSHOT_SIZE);
					}
					output_pipes[i].activation_times[channel] = input_pipes[INPUT_PIPE].activation_times[channel];
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
			// If we change the number of output connections to a higher one, we need to redefine output_pipes
			// And also copy the current data to the new one
			NUM_OUTPUT_PIPES = num;
			Pipe[] tmp = new Pipe[NUM_OUTPUT_PIPES];
			System.arraycopy(output_pipes, 0, tmp, 0, Math.min(output_pipes.length, NUM_OUTPUT_PIPES));
			output_pipes = tmp;
		}
	}
}
