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
    private final static int SAMPLING_RATE = 44100;
    // Audio sample size in bytes
    private final static int SAMPLE_SIZE = 2;
    // Length of tone (in seconds)
    private final static int LENGTH_OF_TONE = 5;
	
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
		//Open up audio output, using 44100hz sampling rate, SAMPLE_SIZE*8 bit samples, mono, signed and little endian byte ordering
		AudioFormat format = new AudioFormat(SAMPLING_RATE, SAMPLE_SIZE*8, 1, true, false);
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		
		line = (SourceDataLine)AudioSystem.getLine(info);
		line.open(format);  
		line.start();

		// Create the oscillator (args: frequency, phase offset, sampling rate)
		Oscillator osc = new Oscillator(440, 0.0, SAMPLING_RATE);

		// Generate "LENGTH_OF_TONE" seconds of sound (with 2 bytes per sample) and write them to the output line
		byte[] array = new byte[SAMPLING_RATE*LENGTH_OF_TONE*SAMPLE_SIZE];
		array = osc.get_sound(SAMPLING_RATE*LENGTH_OF_TONE, SAMPLE_SIZE);
		line.write(array, 0, SAMPLING_RATE*LENGTH_OF_TONE);
		array_write(array, "output");// DEBUGTOOL
		System.out.println("Done");

		//Done playing the whole waveform, now wait until the queued samples finish playing, then clean up and exit
		line.drain();
		line.close();
	}
}
