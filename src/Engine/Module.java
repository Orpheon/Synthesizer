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
	private int NUM_INPUTS;
	private int NUM_OUTPUTS;
	private Pipe[] inputs;
	private Pipe[] outputs;
	private static int counter;
	private int index;
	private int type;
	
	public boolean already_ran = false;
	
	public abstract double get_sound();

	public Module()
	{
		inputs = new Pipe[NUM_INPUTS];
		outputs = new Pipe[NUM_OUTPUTS];
		index = counter++;
	}
	
	public boolean connect_input(Pipe pipe, int position)
	{
		if (position >= NUM_INPUTS)
		{
			// Trying to connect a cable to an invalid port.
			// Don't allow this
			System.out.println("ERROR: Tried to connect a pipe "+pipe.get_index()+" to an invalid input port "+position+" to Module number "+index+" of type "+type+".");
			// Just return false
			return false;
		}
		
		if (inputs[position] != null)
		{
			// There's already a pipe there, we need to disconnect it first
			disconnect_input(position);
		}
		pipe.set_output(this);
		inputs[position] = pipe;
		return true;
	}
	
	public void disconnect_input(int position)
	{
		inputs[position].set_output(null);
		inputs[position] = null;
	}

	public boolean connect_output(Pipe pipe, int position)
	{
		if (position >= NUM_OUTPUTS)
		{
			// Trying to connect a cable to an invalid port.
			// Don't allow this
			System.out.println("ERROR: Tried to connect a pipe "+pipe.get_index()+" to an invalid output port "+position+" to Module number "+index+" of type "+type+".");
			// Just return false
			return false;
		}
		
		if (outputs[position] != null)
		{
			// There's already a pipe there, we need to disconnect it first
			disconnect_output(position);
		}
		pipe.set_input(this);
		outputs[position] = pipe;
		return true;
	}
	
	public void disconnect_output(int position)
	{
		outputs[position].set_input(null);
		outputs[position] = null;
	}
	
	public void run()
	{
		// This prevents recursive infinite loops
		// It gets set back to false in the EngineMaster get_sound method
		already_ran = true;
		for (int i=0; i<NUM_INPUTS; i++)
		{
			if (inputs[i] != null)
			{
				if (inputs[i].get_input() != null)
				{
					if (!inputs[i].get_input().already_ran)
					{
						// If inputs exist and they haven't done something yet, call them
						inputs[i].get_input().run();
					}
				}
			}
		}
		// Do whatever this module is supposed to do and write it in the outputs
		get_sound();
	}
	
	public int get_index()
	{
		return index;
	}

	public int get_type()
	{
		return type;
	}
}
