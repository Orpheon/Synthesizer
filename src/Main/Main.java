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
		engine.play_sound(5, 110, 0.0);
		while (true)
		{
			engine.update();
		}
	}
}