package Engine;

public class Constants
{
    // Audio sampling rate
	public final static int SAMPLING_RATE = 44100;
    // Audio sample size in bytes
    public final static int SAMPLE_SIZE = 2;
    // Size of a single snapshot buffer
    // FIXME: I don't like this cast, but java seems to want it. Beware.
    public final static int SNAPSHOT_SIZE = (int) Math.round((1/30.0)*SAMPLING_RATE*SAMPLE_SIZE);
    // 2*pi
    public final static double pi_times_2 = 6.28318530718;
    
    // Module type constants
    public final static int OSCILLATOR = 1;
    
    // Pipe type constants
    public final static int SOUND_PIPE = 1;
}
