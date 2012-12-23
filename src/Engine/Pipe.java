package Engine;

public class Pipe
{
	private static int counter;
	private int index;
	private int type;
	
	double inner_buffer[] = new double[Engine.Constants.SNAPSHOT_SIZE];
	
	private Module input;
	private Module output;
	
	public Pipe()
	{
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
