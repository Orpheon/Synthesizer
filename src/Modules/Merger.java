/**
 * 
 */
package Modules;

import Engine.Module;
import Engine.Pipe;
import Engine.Constants;

/**
 * @author orpheon
 *
 */
public class Merger extends Module
{
	public static final int ADDITION = 1;
	public static final int MULTIPLICATION = 2;
	
	public static final int OUTPUT_PIPE = 0;
	
	private int operation = ADDITION;
	
	public Merger(Container container)
	{
		super(container);
		
		NUM_INPUT_PIPES = 2;
		NUM_OUTPUT_PIPES = 1;
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		input_pipe_types = new int[NUM_INPUT_PIPES];
		output_pipe_types = new int[NUM_OUTPUT_PIPES];
		
		module_type = Engine.Constants.MODULE_MERGER;
		
		MODULE_NAME = "Merger";
	}
	
	public Merger(Container container, int num_inputs)
	{
		super(container);
		
		NUM_INPUT_PIPES = num_inputs;
		NUM_OUTPUT_PIPES = 1;
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		input_pipe_types = new int[NUM_INPUT_PIPES];
		for (int i=0; i<NUM_INPUT_PIPES; i++)
		{
			input_pipe_types[i] = Constants.MONO;
		}
		output_pipe_types = new int[NUM_OUTPUT_PIPES];
		
		module_type = Engine.Constants.MODULE_MERGER;
		
		MODULE_NAME = "Merger";
	}

	@Override
	public void run(int channel)
	{
		if (output_pipes[OUTPUT_PIPE] != null)
		{
			int i, j;
			switch (operation)
			{
				case ADDITION:
					double sum;
					for (i=0; i<Constants.SNAPSHOT_SIZE; i++)
					{
						sum = 0;
						for (j=0; j<NUM_INPUT_PIPES; j++)
						{
							if (input_pipes[j] != null)
							{
								sum += input_pipes[j].get_pipe(channel)[0][i];
								if (input_pipes[j].get_type() == Constants.STEREO)
								{
									sum += input_pipes[j].get_pipe(channel)[1][i];
								}
							}
						}
						// Don't forget to normalize from -1 to 1 again
						output_pipes[OUTPUT_PIPE].get_pipe(channel)[0][i] = sum / NUM_INPUT_PIPES;
						if (output_pipes[OUTPUT_PIPE].get_type() == Constants.STEREO)
						{
							output_pipes[OUTPUT_PIPE].get_pipe(channel)[1][i] = sum / NUM_INPUT_PIPES;
						}
					}
					break;
					
				case MULTIPLICATION:
					double product;
					for (i=0; i<Constants.SNAPSHOT_SIZE; i++)
					{
						product = 1;
						for (j=0; j<NUM_INPUT_PIPES; j++)
						{
							if (input_pipes[j] != null)
							{
								product *= input_pipes[j].get_pipe(channel)[0][i];
								if (input_pipes[j].get_type() == Constants.STEREO)
								{
									product *= input_pipes[j].get_pipe(channel)[1][i];
								}
							}
						}
						output_pipes[OUTPUT_PIPE].get_pipe(channel)[0][i] = product;
						if (output_pipes[OUTPUT_PIPE].get_type() == Constants.STEREO)
						{
							output_pipes[OUTPUT_PIPE].get_pipe(channel)[1][i] = product;
						}
					}
					break;
					
				default:
					System.out.println("ERROR: Merger "+index+" has an invalid operation type: "+operation);
			}
		}
	}

	public int get_operation()
	{
		return operation;
	}

	public void set_operation(int operation)
	{
		this.operation = operation;
	}

	public int get_num_inputs()
	{
		return NUM_INPUT_PIPES;
	}
	
	public void set_num_inputs(int num)
	{
		// No need to change anything if there's no change
		if (num != NUM_INPUT_PIPES)
		{
			// If we want to lower the number of connections, then disconnect all the superfluous ones
			if (NUM_INPUT_PIPES > num)
			{ 
				for (int i=num; i<NUM_INPUT_PIPES; i++)
				{
					if (input_pipes[i] != null)
					{
						disconnect_input(i);
					}
				}
			}
			NUM_INPUT_PIPES = num;
			Pipe[] tmp = new Pipe[NUM_INPUT_PIPES];
			System.arraycopy(input_pipes, 0, tmp, 0, Math.min(input_pipes.length, NUM_INPUT_PIPES));
			input_pipes = tmp;
		}
	}
}
