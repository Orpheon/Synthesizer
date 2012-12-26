package Engine;

public class Constants
{
    // Audio sampling rate
	public final static int SAMPLING_RATE = 44100;
    // Audio sample size in bytes
    public final static int SAMPLE_SIZE = 2;
    // Size of a single snapshot buffer
    public final static int SNAPSHOT_SIZE = 512;
    // 2*pi
    public final static double pi_times_2 = 6.28318530718;
    
    // Module type constants
    public final static int MODULE_OSCILLATOR = 1;
    public final static int MODULE_MERGER = 2;
    public final static int MODULE_SPLITTER = 3;
    public final static int MODULE_CONTAINER = 4;
	public static final int MODULE_RANGEMODIFIER = 5;
}
