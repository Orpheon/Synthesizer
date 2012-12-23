/**
 * 
 */
package Engine;

/**
 * @author orpheon
 *
 */
public abstract class Module
{
	protected int NUM_INPUT_PIPES;
	protected int NUM_OUTPUT_PIPES;
	protected Pipe[] input_pipes;
	protected Pipe[] output_pipes;
	
	private static int counter;
	protected int index;
	protected int type;
	
	public boolean already_ran = false;
	
	public abstract void get_sound();

	public Module()
	{
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		index = counter++;
	}
	
	public boolean connect_input(Pipe pipe, int position)
	{
		if (position >= NUM_INPUT_PIPES)
		{
			// Trying to connect a cable to an invalid port.
			// Don't allow this
			System.out.println("ERROR: Tried to connect a pipe "+pipe.get_index()+" to an invalid input port "+position+" to Module number "+index+" of type "+type+".");
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
			System.out.println("ERROR: Tried to connect a pipe "+pipe.get_index()+" to an invalid output port "+position+" to Module number "+index+" of type "+type+".");
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
	
	public void run()
	{
		// This prevents recursive infinite loops
		// It gets set back to false in the EngineMaster get_sound method
		already_ran = true;
		
		// Do whatever this module is supposed to do and write it in the output_pipes
		get_sound();
		
		// Then call all of the modules on the other side
		for (int i=0; i<NUM_OUTPUT_PIPES; i++)
		{
			if (output_pipes[i] != null)
			{
				if (output_pipes[i].get_output() != null)
				{
					if (!output_pipes[i].get_output().already_ran)
					{
						// If output modules exist and they haven't done something yet, call them
						output_pipes[i].get_output().run();
					}
				}
			}
		}
	}
	
	public int get_index()
	{
		return index;
	}

	public int get_type()
	{
		return type;
	}
	
	public void close()
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
	}
}
