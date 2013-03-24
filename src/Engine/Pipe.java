package Engine;

public abstract class Pipe
{
	private static int counter;
	private int index;
	protected int type;
	
	private Module input;
	private Module output;
	
	public double[] activation_times = new double[Constants.NUM_CHANNELS];
	
	public Pipe()
	{
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
}
