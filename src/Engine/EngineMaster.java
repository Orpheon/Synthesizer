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
    
	private javax.sound.sampled.SourceDataLine line;
	private Oscillator osc;
    
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
    
    public void play_sound(int duration, double frequency, double phase_offset)
    {
		// Generate "LENGTH_OF_TONE" seconds of sound (with 2 bytes per sample) and write them to the output line
		byte[] array = new byte[SAMPLING_RATE*duration*SAMPLE_SIZE];
		array = this.osc.get_sound(SAMPLING_RATE*duration, SAMPLE_SIZE);
		// Write them to the sound output
		line.write(array, 0, SAMPLING_RATE*duration*SAMPLE_SIZE);
		//Done playing the whole waveform, now wait until the queued samples finish playing, then clean up and exit
		this.line.drain();
    }
    
    public void close()
    {
    	this.line.close();
    	this.osc.close();
    }
}
