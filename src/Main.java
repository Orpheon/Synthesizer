/** Main testing class
 *  Need to be able to call my oscillator from somewhere, but want to make a clean new audio backend manager (if that's the correct term for it)
 *  DEBUGTOOL
 */

/**
 * @author orpheon
 *
 */

//DEBUGTOOL:
import java.io.*;

import javax.sound.sampled.*;

public class Main
{
	// Audio sampling rate
	final static int SAMPLING_RATE = 44100;
    // Audio sample size in bytes
    final static int SAMPLE_SIZE = 2;
    // Length of tone (in seconds)
    final static int LENGTH_OF_TONE = 5;
	
	/**
	 * @param args
	 * @throws LineUnavailableException 
	 * @throws InterruptedException 
	 */
    static void array_write(byte[] aInput, String aOutputFileName){
    	System.out.println("Writing binary file...");
        try {
          OutputStream output = null;
          try {
            output = new BufferedOutputStream(new FileOutputStream(aOutputFileName));
            output.write(aInput);
          }
          finally {
            output.close();
          }
        }
        catch(FileNotFoundException ex){
        	System.out.println("File not found.");
        }
        catch(IOException ex){
        	System.out.println(ex);
        }
      }
    
	public static void main(String[] args) throws LineUnavailableException, InterruptedException
	{
		SourceDataLine line;
		//Open up audio output, using 44100hz sampling rate, 16 bit samples, mono, and little endian byte ordering
		AudioFormat format = new AudioFormat(SAMPLING_RATE, 16, 1, true, false);
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		
		line = (SourceDataLine)AudioSystem.getLine(info);
		line.open(format);  
		line.start();

		// Create the oscillator (args: frequency, phase offset, sampling rate)
		Oscillator osc = new Oscillator(440, 0.0, SAMPLING_RATE);

		// Generate "LENGTH_OF_TONE" seconds of sound and write them to the output line
		line.write(osc.get_sound(SAMPLING_RATE*LENGTH_OF_TONE), 0, SAMPLING_RATE*LENGTH_OF_TONE);
		System.out.println("Done");

		//Done playing the whole waveform, now wait until the queued samples finish playing, then clean up and exit
		line.drain();
		line.close();
	}
}
