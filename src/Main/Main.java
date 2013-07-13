package Main;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

import Modules.Oscillator;
import GUI.ContainerWindow;


/*
 * Basic class that calls both the engine and the gui
 * This is a hack and will probably not exist in this form later
 */

/**
 * @author orpheon
 *
 */
public class Main
{
	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws LineUnavailableException 
	 * @throws IOException 
	 */
	
	public static void main(String[] args) throws LineUnavailableException, InterruptedException
	{
		Engine.EngineMaster engine = new Engine.EngineMaster();
		
		/* NOTE:
		 * If you cannot hear anything, try setting global_volume to 1 in line 22 of EngineMaster.
		 */
		
		// Hardcoded situation
		Modules.Oscillator osc;
		// Create an oscillator
		osc = (Modules.Oscillator) engine.add_module(Engine.Constants.MODULE_OSCILLATOR);
		
		// Set to sine wave
		osc.set_osctype(Modules.Oscillator.SINE_WAVE);
		
		// Connect it with the container inner input ports and the container inner output ports
		engine.connect_modules(engine.main_container, 0, osc, Modules.Oscillator.FREQUENCY_PIPE, false);
		engine.connect_modules(osc, Modules.Oscillator.OUTPUT_PIPE, engine.main_container, 0, false);

		// Attempt to play 3 notes (3 channels)
		engine.set_frequency(440, 523.25, 659.26); // A C E (Am chord)
//		engine.set_frequency(440);
		engine.start_playing();
		while (true)
		{
			// Actually calculate the sound frame and send to audio buffer
			engine.update();
		}
	}
	
	// Test to debug the GUI things
/*	public static void main(String[] args) throws LineUnavailableException, InterruptedException, IOException
	{
		Engine.EngineMaster engine = new Engine.EngineMaster();
		GUI.ContainerWindow main_window = new GUI.ContainerWindow(engine.main_container);
		main_window.setVisible(true);
		while (true)
		{
			engine.update();
		}
	}*/
}