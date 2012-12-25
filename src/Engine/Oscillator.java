package Engine;
/**
 * @author orpheon
 *
 */
// TODO: Make this abstract
public abstract class Oscillator extends Module
{
	protected double frequency = 1;
	protected double phase_offset = 0.0;
	
	protected double period = Engine.Constants.pi_times_2;
	protected double current_position = 0.0;
	
	public static final int FREQUENCY_PIPE = 0;
	public static final int PHASE_PIPE = 1;
	
	public static final int OUTPUT_PIPE = 0;
	
	protected abstract double get_value(double position);
	
	public Oscillator(EngineMaster engine, double frequency, double phase_offset)
	{		
		super(engine);
		
		NUM_INPUT_PIPES = 2;
		NUM_OUTPUT_PIPES = 1;
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		current_position = 0.0;
		set_frequency(frequency);
		set_period(1/frequency);
		set_phase(phase_offset);
	}

	public void get_sound()
	{
		for (int i=0; i<Engine.Constants.SNAPSHOT_SIZE; i++)
		{
			if (input_pipes[FREQUENCY_PIPE] != null)
			{
				set_frequency(input_pipes[FREQUENCY_PIPE].inner_buffer[i]);
			}
			if (input_pipes[PHASE_PIPE] != null)
			{
				set_phase(input_pipes[PHASE_PIPE].inner_buffer[i]);
			}
			current_position += frequency * 1/Constants.SAMPLING_RATE;

			output_pipes[OUTPUT_PIPE].inner_buffer[i] = get_value(current_position);
		}
	}

	public double get_frequency()
	{
		return frequency;
	}

	public void set_frequency(double frequency)
	{
		this.frequency = frequency;
		set_period(1/frequency);
	}

	public double get_phase()
	{
		return phase_offset;
	}

	public void set_phase(double phase_offset)
	{
		// Move the current_pos by the same offset
		current_position += (phase_offset - this.phase_offset);
		while (current_position >= 1)
		{
			current_position -= 1;
		}

		this.phase_offset = phase_offset;
	}

	public double get_period()
	{
		return period;
	} 

	public void set_period(double period)
	{
		this.period = period;
		while (current_position > 1)
		{
			current_position -= 1;
		}
	}
}
