/**
 * 
 */
package Modules;

import Engine.EngineMaster;
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
	
	private int operation = ADDITION;
	
	public Merger(EngineMaster engine, int num_inputs, int operation)
	{
		super(engine);
		
		NUM_INPUT_PIPES = num_inputs;
		NUM_OUTPUT_PIPES = 1;
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		this.operation = operation;
	}

	@Override
	public void get_sound()
	{
		if (output_pipes[0] != null)
		{
			int i, j;
			switch (operation)
			{
				case ADDITION:
					int sum;
					for (i=0; i<Constants.SNAPSHOT_SIZE; i++)
					{
						sum = 0;
						for (j=0; j<NUM_INPUT_PIPES; j++)
						{
							if (input_pipes[j] != null)
							{
								sum += input_pipes[j].inner_buffer[i];
							}
						}
						output_pipes[0].inner_buffer[i] = sum;
					}
					break;
					
				case MULTIPLICATION:
					int product;
					for (i=0; i<Constants.SNAPSHOT_SIZE; i++)
					{
						product = 1;
						for (j=0; j<NUM_INPUT_PIPES; j++)
						{
							if (input_pipes[j] != null)
							{
								product *= input_pipes[j].inner_buffer[i];
							}
						}
						output_pipes[0].inner_buffer[i] = product;
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

}
