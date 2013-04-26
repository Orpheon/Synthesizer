package Engine;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import Modules.Container;

/**
 * @author orpheon
 *
 */
public class EngineMaster
{
	private javax.sound.sampled.SourceDataLine line;
	
	private byte[] sound_buffer = new byte[Engine.Constants.SAMPLE_SIZE * Engine.Constants.SNAPSHOT_SIZE];
	private int sound_buffer_position = sound_buffer.length;
	
	private boolean is_playing;
	private double global_volume = 1;
	
	// FIXME: Should be private but can't if a window can't touch it
	public Container main_container;
    
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

		// Create a container to hold everything
		main_container = new Container();
    }
    
    public void update()
    {
    	// line.available() changes during execution of the method and that breaks stuff
    	int line_available = line.available();
    	
    	// If we still need to play something
    	if (sound_buffer_position < sound_buffer.length)
    	{
    		// If we can just play what's left,
    		if (sound_buffer.length - sound_buffer_position <= line_available)
    		{
    			// do so
    			line.write(sound_buffer, sound_buffer_position, sound_buffer.length - sound_buffer_position);
    			sound_buffer_position = sound_buffer.length;
    		}
    		else
    		{
    			// play all that we can and update the position counter
    			int delta = line_available;
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
    			while (true)
    			{
	    			// Run the entire chain of events
	    			main_container.run(0);
	    			byte[] tmp;
	    			int counter=0;
	    			
	    			for (int i=0; i<Engine.Constants.SNAPSHOT_SIZE; i++)
	    			{
	    				tmp = Functions.convert_to_16bit_bytearray(global_volume * main_container.get_inner_output_pipe(0).get_pipe(0)[0][i]);
	    				System.arraycopy(tmp, 0, sound_buffer, counter, 2);
	    				counter += 2;
	    			}
	    			
	    			// And then output it (or atleast as much as we can now)
	    			counter = line_available;
	    			if (counter <= sound_buffer.length)
	    			{
	    				// If we can fill the entire line
		    			line.write(sound_buffer, 0, counter);
		    			sound_buffer_position = counter;
		    			// Stop for now
		    			break;
	    			}
	    			else
	    			{
	    				// If we can't, then output all we have and generate more sound (this is inside a while loop)
	    				line.write(sound_buffer, 0, sound_buffer.length);
	    			}
    			}
    		}
    	}
    }
    
    public Module add_module(int type)
    {
    	return main_container.add_module(type);
    }
    
    public Module add_module(int type, int subtype)
    {
    	return main_container.add_module(type, subtype);
    }
    
    public void connect_modules(Module module_1, int out_port, Module module_2, int in_port, boolean stereo)
    {
    	// TODO: Another thing that's going to have to disappear when GUI is here.
    	main_container.connect_modules(module_1, out_port, module_2, in_port, stereo);
    }
    
    public void set_frequency(double frequency)
    {
    	// TODO: Another thing that's going to have to disappear when GUI is here.
    	for (int i=0; i<Constants.SNAPSHOT_SIZE; i++)
    	{
    		main_container.get_inner_input_pipe(0).activation_times[0] = 0;
    		main_container.get_inner_input_pipe(0).get_pipe(0)[0][i] = frequency;
    	}
    }
    
    public void start_playing()
    {
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

    public double get_globalvolume()
	{
		return global_volume;
	}

	public void set_globalvolume(double global_volume)
	{
		this.global_volume = global_volume;
	}

	public void close()
    {
		//Done playing the whole waveform, now wait until the queued samples finish playing, then clean up and exit
		line.drain();
    	line.close();
    	
    	main_container.close();
    }
}
