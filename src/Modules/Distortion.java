package Modules;

import Engine.Module;

public abstract class Distortion extends Module
{
	public static final int SIGNAL_INPUT = 0;
	public static final int SIGNAL_OUTPUT = 0;
	
	public abstract double distortion_function(double x);
	
	public Distortion()
	{
		super();

		activation_source = SIGNAL_INPUT;
		module_type = Engine.Constants.MODULE_DISTORTION;
		MODULE_NAME = "Default Distortion";
	}

	@Override
	public void run(Engine.EngineMaster engine, int channel)
	{
		for (int side=0; side<audio_mode; side++)
		{
			for (int i=0; i<Engine.Constants.SNAPSHOT_SIZE; i++)
			{
				output_pipes[SIGNAL_OUTPUT].get_pipe(channel)[side][i] = Math.min(1, Math.max(-1, distortion_function(input_pipes[SIGNAL_INPUT].get_pipe(channel)[side][i])));
			}
		}
	}
}
