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
	public static final int MODULE_INPUT = 1;
	public static final int MODULE_OUTPUT = 2;
	public static final int MODULE_OSCILLATOR = 3;
	public static final int MODULE_MERGER = 4;
	public static final int MODULE_COPYER = 5;
	public static final int MODULE_CONTAINER = 6;
	public static final int MODULE_RANGEMODIFIER = 7;
	public static final int MODULE_DISTORTION = 8;
	public static final int MODULE_STEREOMERGER = 9;
	public static final int MODULE_CONSTANT = 10;
	
	// Module subtype constants
	public static final int DISTORTION_TANH = 0;
	public static final int OSCILLATOR_SINE = 0;
	
	// Pipe type constants
	public static final int MONO = 1;
	public static final int STEREO = 2;
	
	public static final int LEFT_CHANNEL = 1;
	public static final int RIGHT_CHANNEL = 2;
	
	public static final int DEFAULT_AUDIO_MODE = MONO;
	
	// GUI constants
	public static final int INPUT_PORT = 1;
	public static final int OUTPUT_PORT = 2;
	
	public static final int INVALID_MODULE_GUI = 0;
	public static final int DEFAULT_MODULE_GUI = 1;
	public static final int INPUT_MODULE_GUI = 2;
	public static final int OUTPUT_MODULE_GUI = 3;
	public static final int CONSTANT_MODULE_GUI = 4;
}
