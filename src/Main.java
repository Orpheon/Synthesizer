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

		Oscillator osc = new Oscillator(440, 0.0, SAMPLING_RATE);

//		double time = 0.0;
//		while (time < 5.0)
//		{
//			int free_room = line.available()/SAMPLE_SIZE;
//			time += (double)free_room / SAMPLING_RATE;
//
//			//array_write(osc.get_sound(free_room), "output");
//			line.write(osc.get_sound(free_room), 0, line.available());
//
//			//Wait until the buffer is empty before we add more
//			while (line.available() <= 0)   
//				Thread.sleep(1);
//		}
		line.write(osc.get_sound(SAMPLING_RATE * 5), 0, SAMPLING_RATE * 5);
		System.out.println("Done");

		//Done playing the whole waveform, now wait until the queued samples finish playing, then clean up and exit
		line.drain();                                         
		line.close();
	}
}
