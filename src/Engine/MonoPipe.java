package Engine;

public class MonoPipe extends Pipe
{
	
	public double[][] inner_buffers = new double[Constants.NUM_CHANNELS][Constants.SNAPSHOT_SIZE];
	
	public MonoPipe()
	{
		super();
		type = Constants.STEREO;
	}
	
	public double[] get_pipe(int channel)
	{
		return inner_buffers[channel];
	}
}
