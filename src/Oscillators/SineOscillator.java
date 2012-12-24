package Oscillators;

import Engine.Oscillator;

public class SineOscillator extends Oscillator
{
	public SineOscillator(Engine.EngineMaster engine, double frequency, double phase_offset)
	{
		super(engine, frequency, phase_offset);
	}

	protected double get_value(double position)
	{
		return Math.sin(position * Engine.Constants.pi_times_2);
	}
}
