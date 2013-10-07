package Modules;

import Engine.Constants;
import Engine.EngineMaster;
import Engine.Module;
import Engine.Pipe;

// FIXME: Cutoff and Q are assumed to be constant within a snapshot. If they aren't, only the first sample will be used.

public class Highpass extends Module
{
	public static final int SIGNAL_INPUT = 0;
	public static final int FREQUENCY_INPUT = 1;
	public static final int Q_INPUT = 2;
	public static final int OUTPUT_PIPE = 0;
	
	// FIXME: Remove code duplication in constructor
	
	public Highpass()
	{
		super();
		
		NUM_INPUT_PIPES = 3;
		NUM_OUTPUT_PIPES = 1;
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		input_pipe_names = new String[NUM_INPUT_PIPES];
		output_pipe_names = new String[NUM_OUTPUT_PIPES];
		input_pipe_names[SIGNAL_INPUT] = "Sound signal to be highpassed";
		input_pipe_names[FREQUENCY_INPUT] = "Cutoff frequency";
		input_pipe_names[Q_INPUT] = "Q/Resonance";
		output_pipe_names[OUTPUT_PIPE] = "Highpassed sound signal";
		
		activation_source = SIGNAL_INPUT;
		module_type = Engine.Constants.MODULE_HIGHPASS;
		MODULE_NAME = "Highpass";
	}

	@Override
	public void run(Engine.EngineMaster engine, int channel)
	{
		
		double factor, cutoff, Q;
		double[] phase = new double[Constants.SNAPSHOT_SIZE];
		// Run once for mono, twice for stereo
		for (int side=0; side<audio_mode; side++)
		{
			// Transform (inplace) the input signal into frequency amplitudes (and store the phase for later use)
			phase = Engine.Functions.fft(input_pipes[SIGNAL_INPUT].get_pipe(channel)[side]);
			// As noted on top, only the first values of cutoff and Q get considered
			cutoff = Constants.pi_times_2 * input_pipes[FREQUENCY_INPUT].get_pipe(channel)[side][0];
			Q = input_pipes[Q_INPUT].get_pipe(channel)[side][0];
			
			for (int f=0; f<Constants.SNAPSHOT_SIZE; f++)
			{
				// Copy every frequency from the input to the output pipes while highpassing it
				// Source: http://en.wikipedia.org/wiki/Q_factor
				factor = Math.pow(f, 2) * Math.pow(cutoff, 2)/(Math.pow(f, 2) + f * cutoff/Q + Math.pow(cutoff, 2));
				output_pipes[OUTPUT_PIPE].get_pipe(channel)[side][f] = factor * (input_pipes[SIGNAL_INPUT].get_pipe(channel)[side][f]);
			}
			
			// Transform the frequencies back into time-amplitude signal
			Engine.Functions.ifft(output_pipes[OUTPUT_PIPE].get_pipe(channel)[side], phase);
		}
	}
}
