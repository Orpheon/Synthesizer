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
	
	protected final int FREQUENCY_PIPE = 0;
	protected final int PHASE_PIPE = 1;
	
	protected abstract double get_value(double position);
	
	public Oscillator(double frequency, double phase_offset, int samplerate)
	{
		super();
		
		set_frequency(frequency);
		set_period(1/frequency);
		current_position = 0.0;
		set_phase(phase_offset);
		
		NUM_INPUT_PIPES = 2;
		NUM_OUTPUT_PIPES = 1;
	}

	public void get_sound()
	{
		for (int i=0; i<Engine.Constants.SNAPSHOT_SIZE; i++)
		{
			set_frequency(input_pipes[FREQUENCY_PIPE].inner_buffer[i]);
			set_phase(input_pipes[PHASE_PIPE].inner_buffer[i]);

			output_pipes[PHASE_PIPE].inner_buffer[i] = get_value(current_position);
			
			current_position += frequency;
		}
	}

	// TODO: Get rid of this function
/*	public byte[] get_sound(int num_samples, int sample_size)
	{
		int output_length = num_samples*sample_size;
		double sample;

//		System.out.println("\nData:");
//		System.out.println(frequency);
//		System.out.println(period);
//		System.out.println(phase_offset);
//		System.out.println(current_position);
//		System.out.println(samplerate);
//		System.out.println(samplelength);

		byte[] output;
		output = new byte[output_length];

		for (int i=0; i<output_length; i+=sample_size)
		{
			sample = get_value(current_position);
//			System.out.println("\nNew stuff:");
//			System.out.println("i: "+i+" byte[] length: "+output.length+" num_samples: "+num_samples+" output_length: "+output_length);
			current_position += samplelength * frequency * 6.28318530718;
//			while (current_position > period)
//			{
//				current_position -= period;
//			}
			// Then convert "sample" into a byte array and copy that to the output buffer
			// FIXME: This is pretty inefficient, a casting for every number, is it possible to convert the whole array at once?
			System.arraycopy(Functions.convert_to_16bit_bytearray(sample), 0, output, i, sample_size);
		}

		return output;
	}*/

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
		this.current_position += (phase_offset - this.phase_offset);
		while (this.current_position >= this.period)
		{
			this.current_position -= this.period;
		}

		this.phase_offset = phase_offset;
	}

	public int get_samplerate()
	{
		return samplerate;
	}

	public void set_samplerate(int samplerate)
	{
		this.samplerate = samplerate;
		this.samplelength = 1.0 / this.samplerate;
	}

	public double get_period()
	{
		return period;
	}

	public void set_period(double period)
	{
		this.period = period;
		while (this.current_position >= this.period)
		{
			this.current_position -= this.period;
		}
	}
}
