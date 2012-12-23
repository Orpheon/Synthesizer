package Oscillators;

import Engine.Oscillator;

public class SineOscillator extends Oscillator
{
	public SineOscillator(double frequency, double phase_offset, int samplerate)
	{
		super(frequency, phase_offset, samplerate);
	}

	protected double get_value(double position)
	{
		return Math.sin(position * Engine.Constants.pi_times_2);
	}
}
