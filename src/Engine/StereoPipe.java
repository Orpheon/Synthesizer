package Engine;

public class StereoPipe extends Pipe
{
	public double[][][] inner_buffers = new double [Constants.NUM_CHANNELS] [2] [Constants.SNAPSHOT_SIZE];
	
	public StereoPipe()
	{
		super();
		type = Constants.STEREO;
	}

	public double[][] get_pipe(int channel)
	{
		return inner_buffers[channel];
	}
}
