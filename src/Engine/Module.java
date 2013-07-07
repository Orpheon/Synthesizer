/**
 * 
 */
package Engine;

import Modules.Container;

/*
 * @author orpheon
 * This class is the baseline Module class. It contains everything which every module should have, and cannot be instanciated.
 */
public abstract class Module
{
	// Constants that hold the number of in and out pipes
	public int NUM_INPUT_PIPES;
	public int NUM_OUTPUT_PIPES;
	// The actual pipe arrays that hold those pipes
	protected Pipe[] input_pipes;
	protected Pipe[] output_pipes;
	// Arrays determining what pipe types are even allowed (1 for MONO, 2 for STEREO, 0 for both)
	protected int[] input_pipe_types;
	protected int[] output_pipe_types;
	// ID system to provide each module with an identifier
	protected static int counter;
	protected int index;
	// Constant holding what type of module it actually is
	protected int module_type;
	// A string doing the same as the above constant, but in a human-readable form
	public String MODULE_NAME;

	// This makes a module actually do whatever it's supposed to do on it's inputs and write to it's outputs
	public abstract void run(Engine.EngineMaster engine, int channel);
	
	public Module(Container container)
	{
		index = counter++;
		MODULE_NAME = "Default Module";
	}
	
	public Module()
	{
		index = counter++;
	}
	
	public void run(Engine.EngineMaster engine)
	{
		// We check every channel, and if there's an input pipe that's active on that channel we execute the run(channel) method to calculate the output
		// FIXME: Think of a good solution if the input pipes don't agree on the activation times
		for (int i=0; i<Constants.NUM_CHANNELS; i++)
		{
			for (int j=0; j<NUM_INPUT_PIPES; j++)
			{
				if (input_pipes[j] == null)
				{
					continue;
				}
				if (input_pipes[j].activation_times[i] >= 0)
				{
					this.run(engine, i);
					for (int k=0; k<NUM_OUTPUT_PIPES; k++)
					{
						output_pipes[k].activation_times[i] = input_pipes[j].activation_times[i];
					}
					break;
				}
			}
		}
	}
	
	public boolean connect_input(Pipe pipe, int position)
	{
		if (position >= NUM_INPUT_PIPES)
		{
			// Trying to connect a cable to an invalid port.
			// Don't allow this
			System.out.println("ERROR: Tried to connect pipe "+pipe.get_index()+" to an invalid input port "+position+" to Module number "+index+" of type "+module_type+".");
			// Just return false
			return false;
		}

		if (input_pipe_types[position] != 0 && input_pipe_types[position] != pipe.type)
		{
			// This type of pipe cannot be connected to this input
			System.out.println("ERROR: Tried to connect pipe "+pipe.get_index()+" to an input port "+position+" of "+MODULE_NAME+" of different type (pipe:"+pipe.type+", port:"+input_pipe_types[position]+").");
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
			System.out.println("ERROR: Tried to connect pipe "+pipe.get_index()+" to an invalid output port "+position+" to Module number "+index+" of type "+module_type+".");
			// Just return false
			return false;
		}
		
		if (output_pipe_types[position] != 0 && output_pipe_types[position] != pipe.type)
		{
			// This type of pipe cannot be connected to this output
			System.out.println("ERROR: Tried to connect pipe "+pipe.get_index()+" to an output port "+position+" of "+MODULE_NAME+" of different type (pipe:"+pipe.type+", port:"+output_pipe_types[position]+").");
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
