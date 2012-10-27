/** This class defines a general analog oscillator.
 *  Should be inherited from by specific oscillators.
 *  
 *  All (void) methods should return 0 if execution succeeded, a positive error code if not
 */

/**
 * @author orpheon
 *
 */
public abstract class Oscillator
{
	private int frequency;
	private double phase_offset;

	private double period;
	private double current_position;
	
	private int samplerate;
	private double samplelength;
	
	public Oscillator(int frequency, double phase_offset, int samplerate)
	{
		this.frequency = frequency;
		this.set_period(Functions.get_period(frequency));
		this.current_position = phase_offset;
		this.phase_offset = phase_offset;
		this.set_samplerate(samplerate);
	}
	
	public byte[] get_sound(int length)
	{
		// 16 bytes per sample, so "length" samples will mean 16*length bytes
		int output_length = length*16;
		double sample;

		byte[] output;
		output = new byte[output_length];

		for (int i=0; i<output_length; i+=16)
		{
			sample = this.get_value(this.current_position);
			// Frequency and phase offset are implicitely handled in samplelength
			this.current_position += this.samplelength;
			// Then convert "sample" into a byte array and copy that to the output buffer
			// FIXME: This is pretty inefficient, a casting for every number, is it possible to convert the whole array at once?
			System.arraycopy(Functions.convert_to_bytearray(sample), 0, output, i, 16);
		}

		return output;
	}

	public int get_frequency()
	{
		return frequency;
	}

	public void set_frequency(int frequency)
	{
		this.frequency = frequency;
		this.set_period(Functions.get_period(frequency));
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
		this.samplelength = this.samplerate / this.period;
	}

	public double get_period()
	{
		return period;
	}

	public void set_period(double period)
	{
		this.period = period;
		this.samplelength = this.samplerate / this.period;
		while (this.current_position >= this.period)
		{
			this.current_position -= this.period;
		}
	}
	
	private double get_value(double position)
	{
		// SHOULD BE OVERWRITTEN
		// This method returns the actual sound value at a certain position
		// Requires a position in time
		
		// DEBUGTOOL:
		return Math.sin(position);
	}
}
