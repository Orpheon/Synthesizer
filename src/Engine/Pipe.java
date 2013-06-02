package Engine;

public abstract class Pipe
{
	// Identification system
	private static int counter;
	private int index;
	// Holds either Constants.MONO or Constants.STEREO (1 or 2)
	protected int type;
	
	// Handles to the modules at both ends of the pipe
	private Module input;
	private Module output;
	
	// The inner buffers holding the current sound data, in the format [NUMBER OF POLYPHONIC CHANNELS][STEREO/MONO][SNAPSHOT SIZE]
	public double[][][] inner_buffers;
	// Array holding the activation times of the different channels
	public long[] activation_times = new long[Constants.NUM_CHANNELS];
	
	public Pipe()
	{
		// Deactivated channels can be marked with negative times, so first set everything to -1.
		for (int i=0; i<activation_times.length; i++)
		{
			activation_times[i] = -1;
		}
		index = counter++;
	}
	
	public void set_input(Module module)
	{
		input = module;
	}
	
	public void set_output(Module module)
	{
		output = module;
	}
	
	public Module get_input()
	{
		return input;
	}
	
	public Module get_output()
	{
		return output;
	}
	
	public int get_index()
	{
		return index;
	}

	public int get_type()
	{
		return type;
	}
	
	public double[][] get_pipe(int channel)
	{
		return inner_buffers[channel];
	}
}
