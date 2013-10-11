package Modules;

import Engine.Constants;
import Engine.Functions;
import Engine.Module;
import Engine.Pipe;

public class Lowpass extends Module
{
	public static final int SIGNAL_INPUT = 0;
	public static final int FREQUENCY_INPUT = 1;
	public static final int Q_INPUT = 2;
	public static final int SIGNAL_OUTPUT = 0;
	public static final int REAL = 0;
	public static final int IMAG = 1;
	
	public Lowpass()
	{
		super();
		
		NUM_INPUT_PIPES = 3;
		NUM_OUTPUT_PIPES = 1;
		
		input_pipes = new Pipe[NUM_INPUT_PIPES];
		output_pipes = new Pipe[NUM_OUTPUT_PIPES];
		
		input_pipe_names = new String[NUM_INPUT_PIPES];
		output_pipe_names = new String[NUM_OUTPUT_PIPES];
		input_pipe_names[SIGNAL_INPUT] = "Sound signal to be lowpassed";
		input_pipe_names[FREQUENCY_INPUT] = "Cutoff frequency";
		input_pipe_names[Q_INPUT] = "Q/Resonance";
		output_pipe_names[SIGNAL_OUTPUT] = "Lowpassed sound signal";
		
		activation_source = SIGNAL_INPUT;
		module_type = Engine.Constants.MODULE_LOWPASS;
		MODULE_NAME = "Lowpass";
	}
	
	// ATTENTION: ONLY THE FIRST CUTOFF AND Q VALUES WILL BE TAKEN IN ACCOUNT EVERY FRAME

	@Override
	public void run(Engine.EngineMaster engine, int channel)
	{
		boolean everything_connected = true;
		for (int i=0; i<NUM_INPUT_PIPES; i++)
		{
			if (input_pipes[i] == null)
			{
				everything_connected = false;
			}
		}
		for (int i=0; i<NUM_OUTPUT_PIPES; i++)
		{
			if (output_pipes[i] == null)
			{
				everything_connected = false;
			}
		}
		if (everything_connected)
		{
			for (int side=0; side<audio_mode; side++)
			{
				double cutoff = input_pipes[FREQUENCY_INPUT].get_pipe(channel)[side][0];
				double Q = input_pipes[Q_INPUT].get_pipe(channel)[side][0];
				
				double[][] freq = Functions.fft(input_pipes[SIGNAL_INPUT].get_pipe(channel)[side]);
				
				// Intermittant variables
				double factor = Constants.SAMPLING_RATE / Constants.SNAPSHOT_SIZE;
				double omega_squared = Math.pow(cutoff * Constants.pi_times_2, 2);
				double omega_div_Q = cutoff * Constants.pi_times_2 / Q;
				
				double f, filter;
				for (int i=0; i<Constants.SNAPSHOT_SIZE/2; i++)
				{
					f = i * factor;
					// Important link (Scroll down to table of filter types): http://en.wikipedia.org/wiki/Q_factor
					filter = omega_squared / (Math.pow(f, 2) + omega_div_Q*f + omega_squared);
					
					freq[REAL][i] *= filter;
					freq[IMAG][i] *= filter;
				}
				// Frequencies are symmetric, don't make them not symmetric anymore (doing that will make the signal complex)
				for (int i=Constants.SNAPSHOT_SIZE/2; i<Constants.SNAPSHOT_SIZE; i++)
				{
					f = (Constants.SNAPSHOT_SIZE-i) * factor;
					// Important link (Scroll down to table of filter types): http://en.wikipedia.org/wiki/Q_factor
					filter = omega_squared / (Math.pow(f, 2) + omega_div_Q*f + omega_squared);
					
					freq[REAL][i] *= filter;
					freq[IMAG][i] *= filter;
				}
				
				// Pass everything through an IFFT again, and let it copy the result into the output pipe
				Functions.ifft(freq[REAL], freq[IMAG], output_pipes[SIGNAL_OUTPUT].get_pipe(channel)[side]);
			}
		}
	}
}
