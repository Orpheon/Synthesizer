package Engine;
/**
 * @author orpheon
 *
 */
// TODO: Make this abstract
public class Oscillator
{
	private double frequency = 1;
	private double phase_offset = 0.0;

	private double period = Engine.Constants.pi_times_2;
	private double current_position = 0.0;
	
	private int samplerate = 1;
	private double samplelength = 1;
	
	public Oscillator(double frequency, double phase_offset, int samplerate)
	{
		this.set_frequency(frequency);
		this.set_period(Functions.get_period(frequency));
		this.current_position = 0.0;
		this.set_phase(phase_offset);
		this.set_samplerate(samplerate);
	}
	
	public byte[] get_sound(int num_samples, int sample_size)
	{
		int output_length = num_samples*sample_size;
		double sample;

//		System.out.println("\nData:");
//		System.out.println(this.frequency);
//		System.out.println(this.period);
//		System.out.println(this.phase_offset);
//		System.out.println(this.current_position);
//		System.out.println(this.samplerate);
//		System.out.println(this.samplelength);

		byte[] output;
		output = new byte[output_length];

		for (int i=0; i<output_length; i+=sample_size)
		{
			sample = this.get_value(this.current_position);
//			System.out.println("\nNew stuff:");
//			System.out.println("i: "+i+" byte[] length: "+output.length+" num_samples: "+num_samples+" output_length: "+output_length);
			this.current_position += this.samplelength * this.frequency * 6.28318530718;
//			while (this.current_position > this.period)
//			{
//				this.current_position -= this.period;
//			}
			// Then convert "sample" into a byte array and copy that to the output buffer
			// FIXME: This is pretty inefficient, a casting for every number, is it possible to convert the whole array at once?
			System.arraycopy(Functions.convert_to_16bit_bytearray(sample), 0, output, i, sample_size);
		}

		return output;
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
