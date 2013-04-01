package Engine;

public class StereoPipe extends Pipe
{
	public StereoPipe()
	{
		super();
		inner_buffers = new double[Constants.NUM_CHANNELS][2][Constants.SNAPSHOT_SIZE];
		type = Constants.STEREO;
	}
}
