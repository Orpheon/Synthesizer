/**
 * 
 */
package Engine;

import Modules.Container;

/**
 * @author orpheon
 *
 */
public abstract class Module
{
	public int NUM_INPUT_PIPES;
	public int NUM_OUTPUT_PIPES;
	protected Pipe[] input_pipes;
	protected Pipe[] output_pipes;
	
	protected static int counter;
	protected int index;
	
	protected int module_type;
	
	public final static String MODULE_NAME = "Default Module";
	
	public abstract void run();

	public Module(Container container)
	{
		index = counter++;
	}
	
	public Module()
	{
		index = counter++;
	}
	
	public boolean connect_input(Pipe pipe, int position)
	{
		if (position >= NUM_INPUT_PIPES)
		{
			// Trying to connect a cable to an invalid port.
			// Don't allow this
			System.out.println("ERROR: Tried to connect a pipe "+pipe.get_index()+" to an invalid input port "+position+" to Module number "+index+" of type "+module_type+".");
			// Just return false
			return false;
		}
		
		if (input_pipes[position] != null)
		{
			// There's already a pipe there, we need to disconnect it first
			disconnect_input(position);
		}
		pipe.set_output(this);
		input_pipes[position] = pipe;
		return true;
	}
	
	public void disconnect_input(int position)
	{
		input_pipes[position].set_output(null);
		input_pipes[position] = null;
	}

	public boolean connect_output(Pipe pipe, int position)
	{
		if (position >= NUM_OUTPUT_PIPES)
		{
			// Trying to connect a cable to an invalid port.
			// Don't allow this
			System.out.println("ERROR: Tried to connect a pipe "+pipe.get_index()+" to an invalid output port "+position+" to Module number "+index+" of type "+module_type+".");
			// Just return false
			return false;
		}
		
		if (output_pipes[position] != null)
		{
			// There's already a pipe there, we need to disconnect it first
			disconnect_output(position);
		}
		pipe.set_input(this);
		output_pipes[position] = pipe;
		return true;
	}
	
	public void disconnect_output(int position)
	{
		output_pipes[position].set_input(null);
		output_pipes[position] = null;
	}
	
	public int get_index()
	{
		return index;
	}

	public int get_moduletype()
	{
		return module_type;
	}
	
	public Pipe get_input_pipe(int i)
	{
		return input_pipes[i];
	}
	
	public Pipe get_output_pipe(int i)
	{
		return output_pipes[i];
	}
	
	public void close(Container container)
	{
		// Clean everything up
		for (int i=0; i<NUM_INPUT_PIPES; i++)
		{
			disconnect_input(i);
		}
		for (int i=0; i<NUM_OUTPUT_PIPES; i++)
		{
			disconnect_output(i);
		}
		
		container.remove_module(this);
	}
}
