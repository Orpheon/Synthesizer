package Modules;

import Engine.Constants;
import Engine.Module;
import Engine.Pipe;

/**
 * @author orpheon
 *
 */
public class Oscillator extends Module
{
	private double frequency = 1;
	private double phase_offset = 0.0;
	private double amplitude = 1.0;
	// FIXME: Not sure if this variable has any right to exist
	private double detune = 0;
	
	private double period = Engine.Constants.pi_times_2;
	private double current_position = 0.0;
	
	public static final int FREQUENCY_PIPE = 0;
	public static final int PHASE_PIPE = 1;
	
	public static final int OUTPUT_PIPE = 0;
	
	public static final int SINE_WAVE = 0;
	public static final int SAW_WAVE = 1;
	public static final int SQUARE_WAVE = 2;
	
	private int osc_type = SINE_WAVE;
	
	public Oscillator(Container container, double frequency, double phase_offset, double detune, int osc_type)
	{		
		super(container);
		
		NUM_INPUT_PIPES = 2;
		NUM_OUTPUT_PIPES = 1;
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		// Both inputs and outputs are all MONO
		input_pipe_types = new int[NUM_INPUT_PIPES];
		for (int i=0; i<NUM_INPUT_PIPES; i++)
		{
			input_pipe_types[i] = Constants.MONO;
		}
		output_pipe_types = new int[NUM_OUTPUT_PIPES];
		for (int i=0; i<NUM_OUTPUT_PIPES; i++)
		{
			output_pipe_types[i] = Constants.MONO;
		}
		
		module_type = Engine.Constants.MODULE_OSCILLATOR;
		
		MODULE_NAME = "Oscillator";
		
		current_position = 0.0;
		set_frequency(frequency);
		set_phase(phase_offset);
		set_detune(detune);
		set_osctype(osc_type);
	}
	
	public Oscillator(Container container)
	{		
		super(container);
		
		NUM_INPUT_PIPES = 2;
		NUM_OUTPUT_PIPES = 1;
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		// Both inputs and outputs are all MONO
		input_pipe_types = new int[NUM_INPUT_PIPES];
		for (int i=0; i<NUM_INPUT_PIPES; i++)
		{
			input_pipe_types[i] = Constants.MONO;
		}
		output_pipe_types = new int[NUM_OUTPUT_PIPES];
		for (int i=0; i<NUM_OUTPUT_PIPES; i++)
		{
			output_pipe_types[i] = Constants.MONO;
		}
		
		current_position = 0.0;
		
		module_type = Engine.Constants.MODULE_OSCILLATOR;
		
		MODULE_NAME = "Oscillator";
	}

	public void run(int channel)
	{
		// Set the frequency to whatever the frequency input pipe wants, if it exists
		if (input_pipes[FREQUENCY_PIPE] != null)
		{
			if (input_pipes[FREQUENCY_PIPE].get_type() != Constants.MONO)
			{
				System.out.println("Error in Oscillator "+index+"; Frequency pipe has type "+input_pipes[FREQUENCY_PIPE].get_type()+".");
			}
		}
		
		// Ditto for the phase
		if (input_pipes[PHASE_PIPE] != null)
		{
			if (input_pipes[PHASE_PIPE].get_type() != Constants.MONO)
			{
				System.out.println("Error in Oscillator "+index+"; Phase pipe has type "+input_pipes[PHASE_PIPE].get_type()+".");
			}
		}
		
		// If we can actually output anything
		if (output_pipes[OUTPUT_PIPE] != null)
		{
			for (int i=0; i<Engine.Constants.SNAPSHOT_SIZE; i++)
			{
				// If we have freq input, then set out freq to that and add in detuning
				if (input_pipes[FREQUENCY_PIPE] != null)
				{
					// TODO: Make detune work in half-tone percentage and so dependent on frequency
					set_frequency(input_pipes[FREQUENCY_PIPE].get_pipe(channel)[0][i] + detune);
				}
				// Same for phase
				if (input_pipes[PHASE_PIPE] != null)
				{
					set_phase(input_pipes[PHASE_PIPE].get_pipe(channel)[0][i]);
				}
				// Calculate what position of our wave we have to sample, thus already taking in count the frequency
				current_position += frequency * 1/Constants.SAMPLING_RATE;
				// All waves are periodic, that's a good thing
				while (Math.abs(current_position) > 1)
				{
					current_position -= Math.signum(current_position);
				}

				// We sample at that position, multiply it with the amplitude and write it in the output
				output_pipes[OUTPUT_PIPE].get_pipe(channel)[0][i] = get_value(current_position) * amplitude;
				if (output_pipes[OUTPUT_PIPE].get_type() == Constants.STEREO)
				{
					output_pipes[OUTPUT_PIPE].get_pipe(channel)[1][i] = output_pipes[OUTPUT_PIPE].get_pipe(channel)[0][i];
				}
			}
		}
	}
	
	protected double get_value(double position)
	{
		// Sampling at a certain position
		// TODO: Make anti-aliased saws and squares
		switch (osc_type)
		{
			case SINE_WAVE:
				return Math.sin(position*Constants.pi_times_2);
			
			case SAW_WAVE:
				return position;
				
			case SQUARE_WAVE:
				if (position < 0.5)
				{
					return 0.0;
				}
				else
				{
					return 1.0;
				}
				
			default:
				System.out.println("ERROR: Oscillator "+index+" has the invalid osc_type "+osc_type+"!");
				return 0;
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
		while (Math.abs(current_position) > 1)
		{
			current_position -= Math.signum(current_position);
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
		while (Math.abs(current_position) > 1)
		{
			current_position -= Math.signum(current_position);
		}
	}
	
	public void set_osctype(int type)
	{
		osc_type = type;
	}
	
	public int get_osctype()
	{
		return osc_type;
	}

	public double get_amplitude()
	{
		return amplitude;
	}

	public void set_amplitude(double amplitude)
	{
		this.amplitude = amplitude;
	}

	public double get_detune()
	{
		return detune;
	}

	public void set_detune(double detune)
	{
		this.detune = detune;
	}
}
