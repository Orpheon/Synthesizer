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
	private javax.sound.sampled.SourceDataLine line;
	
	private byte[] sound_buffer = new byte[Engine.Constants.SAMPLE_SIZE * Engine.Constants.SNAPSHOT_SIZE];
	private int sound_buffer_position = sound_buffer.length;
	
	private Pipe output;
	private Generator main_generator;
	
	public Module[] all_modules;
	
	private boolean is_playing;
    
    /*
	 * @throws LineUnavailableException
	 * @throws InterruptedException
	 */

    public EngineMaster() throws LineUnavailableException, InterruptedException
    {
		//Open up audio output, using 44100hz sampl2ing rate, 16 bit samples, mono, signed and little endian byte ordering
		AudioFormat format = new AudioFormat(Engine.Constants.SAMPLING_RATE, Engine.Constants.SAMPLE_SIZE*8, 1, true, false);
		
		this.line = (SourceDataLine)AudioSystem.getSourceDataLine(format);
		this.line.open(format);  
		this.line.start();

		// First an output pipe to hold the outputs
		output = new Pipe();
		// Create then a Generator to make everything run
		main_generator = new Engine.Generator(output, 440.0);
    }
    
    // Not sure if this is the best method
    public void change_frequency(double new_frequency)
    {
    	main_generator.set_frequency(new_frequency);
    }
    
    public void update()
    {
    	// If we still need to play something
    	if (sound_buffer_position < sound_buffer.length)
    	{
    		// If we can just play what's left,
    		if (sound_buffer.length - sound_buffer_position <= line.available())
    		{
    			// do so
    			line.write(sound_buffer, sound_buffer_position, sound_buffer.length - sound_buffer_position);
    		}
    		else
    		{
    			// play all that we can and update the position counter
    			int delta = line.available();
    			line.write(sound_buffer, sound_buffer_position, delta);
    			sound_buffer_position += delta;
    		}
    	}
    	else
    	{
    		// We already played to end the last chunk
    		// Now either don't do anything if we aren't playing or play the next chunk if we are
    		if (is_playing)
    		{
    			// Run the entire chain of events
    			// FIXME: There has to be a more efficient way to do this, and is it really better to start from the bottom?
    			output.get_input().run();
    			byte[] tmp;
    			int counter = 0;
    			for (int i=0; i<Engine.Constants.SNAPSHOT_SIZE; i++)
    			{
    				tmp = Functions.convert_to_16bit_bytearray(output.inner_buffer[i]);
    				System.arraycopy(tmp, 0, sound_buffer, counter+=2, 2);
    			}
    			// And then output it (or atleast as much as we can now)
    			counter = line.available();
    			line.write(sound_buffer, 0, counter);
    			sound_buffer_position = counter;
    		}
    	}
    }
    
    public void play_sound(double frequency)
    {
    	main_generator.set_frequency(frequency);
    	is_playing = true;
    }
    
    public void stop_playing()
    {
    	is_playing = false;
    }
	
    public boolean is_playing()
    {
    	return is_playing;
    }
    
    public void close()
    {
		//Done playing the whole waveform, now wait until the queued samples finish playing, then clean up and exit
		line.drain();
    	line.close();
    	main_generator.close();
    }
}
