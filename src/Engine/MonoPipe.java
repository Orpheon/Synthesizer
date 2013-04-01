package Engine;

public class MonoPipe extends Pipe
{
	public MonoPipe()
	{
		super();
		inner_buffers = new double[Constants.NUM_CHANNELS][1][Constants.SNAPSHOT_SIZE];
		type = Constants.MONO;
	}
}
