package Modules;

import java.util.LinkedList;

import Engine.Constants;
import Engine.Module;
import Engine.Pipe;
import Distortion.OverdriveDistortion;

public class Container extends Module
{
	private Pipe[] inner_input_pipes;
	private Pipe[] inner_output_pipes;
	private LinkedList<Engine.Module> module_list;
	
	public Container()
	{
		super();
		initialize();
	}
	
	public Container(Container container)
	{
		super(container);
		initialize();
	}
	
	private void initialize()
	{
		NUM_INPUT_PIPES = 1;
		NUM_OUTPUT_PIPES = 1;
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];

		inner_input_pipes = new Pipe[NUM_INPUT_PIPES];
		inner_output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		module_type = Constants.MODULE_CONTAINER;
		
		module_list = new LinkedList<Engine.Module>();
	}
	
	@Override
	public void run()
	{
		for (int i=0; i<NUM_INPUT_PIPES; i++)
		{
			if (input_pipes[i] != null && inner_input_pipes[i] != null)
			{
				System.arraycopy(input_pipes[i].inner_buffer, 0, inner_input_pipes[i].inner_buffer, 0, Engine.Constants.SNAPSHOT_SIZE);
			}
		}
		
		// TODO: Use iterators here
		for (int i=0; i<module_list.size(); i++)
		{
			module_list.get(i).run();
		}
		
		for (int i=0; i<NUM_OUTPUT_PIPES; i++)
		{
			if (output_pipes[i] != null && inner_output_pipes[i] != null)
			{
				System.arraycopy(inner_output_pipes[i].inner_buffer, 0, output_pipes[i].inner_buffer, 0, Engine.Constants.SNAPSHOT_SIZE);
			}
		}
	}
	
	public Module add_module(int type)
	{
		Module m;
    	switch (type)
    	{
    		case Constants.MODULE_OSCILLATOR:
    			m = new Modules.Oscillator(this);
    			break;
    			
    		case Constants.MODULE_MERGER:
    			m = new Modules.Merger(this);
    			break;
    			
    		case Constants.MODULE_SPLITTER:
    			m = new Modules.Splitter(this);
    			break;
    			
    		case Constants.MODULE_CONTAINER:
    			m = new Modules.Container(this);
    			break;
    			
    		default:
    			System.out.println("ERROR: Invalid module type requested: "+type);
    			m = null;
    	}
    	// Goddamn java and it's obsession with the possibility that some code might never be executed
    	if (m != null)
    	{
    		module_list.add(m);
    	}
    	return m;
	}
	
	public Module add_module(int type, int subtype)
	{
		Module m;
    	switch (type)
    	{
    		case Constants.MODULE_DISTORTION:
    			switch (subtype)
    			{
    				case Constants.DISTORTION_OVERDRIVE:
    					m = new OverdriveDistortion(this);
    					break;
    					
    				default:
    					System.out.println("ERROR: Invalid module subtype requested: "+type+"; "+subtype);
    	    			m = null;
    			}
    			break;
    			
    		default:
    			System.out.println("ERROR: Invalid module type requested: "+type+"; "+subtype);
    			m = null;
    	}
    	// Goddamn java and it's obsession with the possibility that some code might never be executed
    	if (m != null)
    	{
    		module_list.add(m);
    	}
    	return m;
	}
	
	public void remove_module(Module m)
	{
		module_list.remove(m);
	}
	
	public void connect_modules(Module module_1, int out_port, Module module_2, int in_port)
    {
    	Pipe pipe = new Pipe();
    	if (module_1 == this)
    	{
    		// If a module wants to connect with our inner input ports
    		this.connect_inner_input(pipe, out_port);
    		module_2.connect_input(pipe, in_port);
    	}
    	else if (module_2 == this)
    	{
    		// A module wants to export its output to the general container inner output ports
    		module_1.connect_output(pipe, out_port);
    		this.connect_inner_output(pipe, in_port);
    	}
    	else
    	{
    		// Two random modules want to be connected
    		// Remember that the pipe must be connected to the output of the first module and go in the second.
        	module_1.connect_output(pipe, out_port);
        	module_2.connect_input(pipe, in_port);
    	}
    }

	public boolean connect_inner_input(Pipe pipe, int position)
	{
		if (position >= NUM_INPUT_PIPES)
		{
			// Trying to connect a cable to an invalid port.
			// Don't allow this
			System.out.println("ERROR: Tried to connect a pipe "+pipe.get_index()+" to an invalid inner input port "+position+" to Module number "+index+" of type "+module_type+".");
			// Just return false
			return false;
		}
		
		if (inner_input_pipes[position] != null)
		{
			// There's already a pipe there, we need to disconnect it first
			disconnect_inner_input(position);
		}
		pipe.set_input(this);
		inner_input_pipes[position] = pipe;
		return true;
	}
	
	public void disconnect_inner_input(int position)
	{
		inner_input_pipes[position].set_input(null);
		inner_input_pipes[position] = null;
	}
	
	public boolean connect_inner_output(Pipe pipe, int position)
	{
		if (position >= NUM_OUTPUT_PIPES)
		{
			// Trying to connect a cable to an invalid port.
			// Don't allow this
			System.out.println("ERROR: Tried to connect a pipe "+pipe.get_index()+" to an invalid inner output port "+position+" to Module number "+index+" of type "+module_type+".");
			// Just return false
			return false;
		}
		
		if (inner_output_pipes[position] != null)
		{
			// There's already a pipe there, we need to disconnect it first
			disconnect_inner_output(position);
		}
		pipe.set_output(this);
		inner_output_pipes[position] = pipe;
		return true;
	}
	
	public void disconnect_inner_output(int position)
	{
		inner_output_pipes[position].set_output(null);
		inner_output_pipes[position] = null;
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
	
	public Pipe get_inner_input_pipe(int i)
	{
		return this.inner_input_pipes[i];
	}

	public Pipe get_inner_output_pipe(int i)
	{
		return this.inner_output_pipes[i];
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
		
    	// Destroy and disconnect all the modules (who will take care of the pipes themselves)
    	Module m;
    	while (module_list.size() > 0)
    	{
    		m = module_list.getFirst();
    		// Modules disconnect themselves from EngineMaster.module_list automatically
    		m.close(this);
    	}
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
		
    	// Destroy and disconnect all the modules (who will take care of the pipes themselves)
    	Module m;
    	while (module_list.size() > 0)
    	{
    		m = module_list.getFirst();
    		// Modules disconnect themselves from this.module_list automatically
    		m.close(this);
    	}
    	
    	container.remove_module(this);
	}
}
