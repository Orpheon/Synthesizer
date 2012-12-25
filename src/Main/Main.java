package Main;

import javax.sound.sampled.LineUnavailableException;


/**
 * Basic class that calls both the engine and the gui
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
	 */
	public static void main(String[] args) throws LineUnavailableException, InterruptedException
	{
		Engine.EngineMaster engine = new Engine.EngineMaster();
		GUI.MainWindow window = new GUI.MainWindow();
		window.createWindow(null);
		engine.add_oscillator(440, 0, Engine.Oscillator.SINE_WAVE);
		engine.start_playing(440);
		while (true)
		{
			engine.update();
		}
	}
}