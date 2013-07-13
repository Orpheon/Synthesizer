package Engine;

public class Constants
{
	// Audio sampling rate
	public static final int SAMPLING_RATE = 44100;
	// Audio sample size in bytes
	public static final int SAMPLE_SIZE = 2;
	// Size of a single snapshot buffer
	public static final int SNAPSHOT_SIZE = 512;
	// Degree of polyphony / Number of simultaneous independent notes
	public static final int NUM_CHANNELS = 8;
	// 2*pi
	public static final double pi_times_2 = 6.28318530718;
	
	// Module type constants
	public static final int MODULE_OSCILLATOR = 1;
	public static final int MODULE_MERGER = 2;
	public static final int MODULE_COPYER = 3;
	public static final int MODULE_CONTAINER = 4;
	public static final int MODULE_RANGEMODIFIER = 5;
	public static final int MODULE_DISTORTION = 6;
	public static final int MODULE_STEREOMERGER = 7;
	
	// Module subtype constants
	public static final int DISTORTION_TANH = 0;
	public static final int OSCILLATOR_SINE = 0;
	
	// Pipe type constants
	public static final int MONO = 1;
	public static final int STEREO = 2;
	
	public static final int LEFT_CHANNEL = 1;
	public static final int RIGHT_CHANNEL = 2;
	
	public static final int DEFAULT_AUDIO_MODE = MONO;
}
