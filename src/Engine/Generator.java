/**
 * 
 */
package Engine;

/**
 * @author orpheon
 *
 */
// FIXME: Find a better name for this class. Is the whole class even necessary or good?
public class Generator extends Module
{
	private double frequency;

	public Generator(Pipe output, double frequency)
	{
		super();
		
		// TODO: Add in amplitude and raise input number
		NUM_INPUT_PIPES = 0;
		NUM_OUTPUT_PIPES = 1;

		connect_input(output, 0);
		
		this.frequency = frequency;
	}
	
	@Override
	public void get_sound()
	{
		for (int i=0; i<Engine.Constants.SNAPSHOT_SIZE; i++)
		{
			output_pipes[0].inner_buffer[i] = frequency;
		}
	}

	public double get_frequency()
	{
		return this.frequency;
	}

	public void set_frequency(double frequency)
	{
		this.frequency = frequency;
	}
}
