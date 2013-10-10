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
	
	public static final int SIGNAL_OUTPUT = 0;
	
	private int operation = MULTIPLICATION;
	
	public Merger()
	{
		super();
		
		NUM_INPUT_PIPES = 2;
		NUM_OUTPUT_PIPES = 1;
		
		initialize();
	}
	
	public Merger(int num_inputs)
	{
		super();
		
		NUM_INPUT_PIPES = num_inputs;
		NUM_OUTPUT_PIPES = 1;
		
		initialize();
	}
	
	public void initialize()
	{
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		input_pipe_names = new String[NUM_INPUT_PIPES];
		output_pipe_names = new String[NUM_OUTPUT_PIPES];
		input_pipe_names[0] = "Input 1";
		input_pipe_names[1] = "Input 2";
		output_pipe_names[0] = "Merged output";
		
		module_type = Engine.Constants.MODULE_MERGER;
		
		MODULE_NAME = "Merger";
	}

	@Override
	public void run(Engine.EngineMaster engine, int channel)
	{
		if (output_pipes[SIGNAL_OUTPUT] != null)
		{
			int i, j;
			switch (operation)
			{
				case ADDITION:
					double[] sum;
					for (i=0; i<Constants.SNAPSHOT_SIZE; i++)
					{
						sum = new double[2];
						for (int side=0; side<audio_mode; side++)
						{
							for (j=0; j<NUM_INPUT_PIPES; j++)
							{
								if (input_pipes[j] != null)
								{
									sum[side] += input_pipes[j].get_pipe(channel)[side][i];
								}
							}
							// Don't forget to normalize from -1 to 1 again
							output_pipes[SIGNAL_OUTPUT].get_pipe(channel)[0][i] = sum[0] / NUM_INPUT_PIPES;
						}
					}
					break;
					
				case MULTIPLICATION:
					double[] product;
					product = new double[2];
					for (i=0; i<Constants.SNAPSHOT_SIZE; i++)
					{
						product[0] = 1.0;
						product[1] = 1.0;
						for (int side=0; side<audio_mode; side++)
						{
							for (j=0; j<NUM_INPUT_PIPES; j++)
							{
								if (input_pipes[j] != null)
								{
									product[side] *= input_pipes[j].get_pipe(channel)[side][i];
								}
							}
							output_pipes[SIGNAL_OUTPUT].get_pipe(channel)[side][i] = product[side];
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
			String[] new_names = new String[NUM_OUTPUT_PIPES];
			for (int i=0; i<NUM_INPUT_PIPES; i++)
			{
				new_names[i] = "Input "+i;
			}
			input_pipe_names = new_names;
		}
	}
}
