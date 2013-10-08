package Modules;

import Engine.Constants;
import Engine.Module;
import Engine.Pipe;

// FIXME: Cutoff and Q are assumed to be constant within a snapshot. If they aren't, only the first sample will be used.

public class Lowpass extends Module
{
	public static final int SIGNAL_INPUT = 0;
	public static final int FREQUENCY_INPUT = 1;
	public static final int Q_INPUT = 2;
	public static final int SIGNAL_OUTPUT = 0;

	public double[] filter_buffer_x;
	public double[] filter_buffer_y;
	
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
		
		filter_buffer_x = new double[2];
		filter_buffer_y = new double[2];
	}

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
			// Main resource: http://www.musicdsp.org/files/Audio-EQ-Cookbook.txt
			// Fuck FFT, that was a mistake
			double cutoff, Q;
			double w0, cos_w0, sin_w0, alpha;
			double a0, a1, a2, b0, b1, b2;
			double[] x, y;

			for (int side=0; side<audio_mode; side++)
			{
				x = input_pipes[SIGNAL_INPUT].get_pipe(channel)[side];
				y = output_pipes[SIGNAL_OUTPUT].get_pipe(channel)[side];
				for (int i=0; i<Constants.SNAPSHOT_SIZE; i++)
				{
					// FIXME: Decide whether to do this in here for modulation, or whether to do it beforehand for speed.
					cutoff = input_pipes[FREQUENCY_INPUT].get_pipe(channel)[side][i];
					Q = input_pipes[Q_INPUT].get_pipe(channel)[side][i];
					
					w0 = Constants.pi_times_2 * cutoff/Constants.SAMPLING_RATE;
					cos_w0 = Math.cos(w0);
					sin_w0 = Math.sin(w0);
					alpha = sin_w0/(2*Q);
					
                    a0 =   1 + alpha;
                    a1 =  -2*cos_w0;
                    a2 =   1 - alpha;
		            b0 =  (1 - cos_w0)/2;
                    b1 =   1 - cos_w0;
                    b2 =  (1 - cos_w0)/2;
                    
                    // These two cases must be handled separately, or at least should
                    if (i == 0)
                    {
                        y[i] = Math.min(1, Math.max(-1, 
                        			(b0/a0)*x[i] + (b1/a0)*filter_buffer_x[1] + (b2/a0)*filter_buffer_x[0] - (a1/a0)*filter_buffer_y[1] - (a2/a0)*filter_buffer_y[0]
                        		));
                    }
                    else if (i == 1)
                    {
                    	y[i] = Math.min(1, Math.max(-1, 
                    				(b0/a0)*x[i] + (b1/a0)*x[0] + (b2/a0)*filter_buffer_x[1] - (a1/a0)*y[0] - (a2/a0)*filter_buffer_y[1]
                    			));
                    }
                    else
                    {
                    	y[i] = Math.min(1, Math.max(-1, 
                    				(b0/a0)*x[i] + (b1/a0)*x[i-1] + (b2/a0)*x[i-2] - (a1/a0)*y[i-1] - (a2/a0)*y[i-2]
                    			));
                    }
				}
			}
		}
	}
}
