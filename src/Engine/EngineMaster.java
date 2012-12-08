/**
 * 
 */
package Engine;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * @author orpheon
 *
 */
public class EngineMaster
{
    // Audio sampling rate
    private final static int SAMPLING_RATE = 44100;
    // Audio sample size in bytes
    private final static int SAMPLE_SIZE = 2;
    // Max length of a single sound request
    private final static int MAX_SOUND_LENGTH = 2;
    
	private javax.sound.sampled.SourceDataLine line;
	private Oscillator osc;
	
	private byte[] sound_buffer = new byte[SAMPLE_SIZE*SAMPLING_RATE*MAX_SOUND_LENGTH];
	private int sound_buffer_offset = sound_buffer.length;
	private int sound_duration = MAX_SOUND_LENGTH*SAMPLING_RATE;
    
    /*
	 * @throws LineUnavailableException
	 * @throws InterruptedException
	 */

    public EngineMaster() throws LineUnavailableException, InterruptedException
    {
		//Open up audio output, using 44100hz sampl2ing rate, 16 bit samples, mono, signed and little endian byte ordering
		AudioFormat format = new AudioFormat(SAMPLING_RATE, SAMPLE_SIZE*8, 1, true, false);
		
		this.line = (SourceDataLine)AudioSystem.getSourceDataLine(format);
		this.line.open(format);  
		this.line.start();

		// Create the oscillator (args: frequency, phase offset, sampling rate)
		this.osc = new Oscillator(100, 0.0, SAMPLING_RATE);
		// Playing with infrasound... http://en.wikipedia.org/wiki/Infrasound#Human_reactions_to_infrasound
		//Oscillator osc = new Oscillator(18.98, 0.0, SAMPLING_RATE);
    }
    
    // Not sure if this is the best method
    public void change_frequency(double new_frequency)
    {
    	this.osc.set_frequency(new_frequency);
    }
    
    public void update()
    {
    	// If we still need to play something
    	if (this.sound_buffer_offset < this.sound_duration)
    	{
    		int dif = Math.min(this.sound_duration-this.sound_buffer_offset, this.line.available());
    		this.line.write(this.sound_buffer, sound_buffer_offset, dif);
    		this.sound_buffer_offset += dif;
    	}
    }
    
    public void play_sound(int duration, double frequency, double phase_offset)
    {
    	// TODO: Make a good duration system
    	duration = Math.min(EngineMaster.MAX_SOUND_LENGTH, duration);
    	
    	this.osc.set_frequency(frequency);
		// Generate "LENGTH_OF_TONE" seconds of sound (with 2 bytes per sample) and write them to the output line
    	System.arraycopy(this.osc.get_sound(EngineMaster.SAMPLING_RATE*duration, EngineMaster.SAMPLE_SIZE), 0,
    					 this.sound_buffer, 0, EngineMaster.SAMPLING_RATE*duration*EngineMaster.SAMPLE_SIZE);
		this.sound_buffer_offset = 0;
		this.sound_duration = EngineMaster.SAMPLING_RATE*duration;
    }
    
    public boolean is_playing()
    {
    	return this.sound_buffer_offset < this.sound_duration;
    }
    
    public void close()
    {
		//Done playing the whole waveform, now wait until the queued samples finish playing, then clean up and exit
		this.line.drain();
    	this.line.close();
    	this.osc.close();
    }
}
