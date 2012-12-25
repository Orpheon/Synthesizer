package Main;

import javax.sound.sampled.LineUnavailableException;

import Modules.Oscillator;


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
		//window.createWindow(null);
		
		// Hardcoded situation
		Modules.Splitter source = (Modules.Splitter) engine.add_module(Engine.Constants.MODULE_SPLITTER);
		source.set_num_outputs(3);
		// Yes, engine.input is the source pipe, that was a naming mistake I'll fix later
		source.connect_input(engine.input, Modules.Splitter.INPUT_PIPE);
		
		Modules.Oscillator osc1, osc2, osc3;
		osc1 = (Modules.Oscillator) engine.add_module(Engine.Constants.MODULE_OSCILLATOR);
		osc2 = (Modules.Oscillator) engine.add_module(Engine.Constants.MODULE_OSCILLATOR);
		osc3 = (Modules.Oscillator) engine.add_module(Engine.Constants.MODULE_OSCILLATOR);
		
		osc1.set_detune(3);
		osc2.set_detune(-3);
		
		osc1.set_osctype(Modules.Oscillator.SAW_WAVE);
		osc2.set_osctype(Modules.Oscillator.SAW_WAVE);
		osc3.set_osctype(Modules.Oscillator.SAW_WAVE);
		
		engine.connect_modules(source, 0, osc1, Modules.Oscillator.FREQUENCY_PIPE);
		engine.connect_modules(source, 1, osc2, Modules.Oscillator.FREQUENCY_PIPE);
		engine.connect_modules(source, 2, osc3, Modules.Oscillator.FREQUENCY_PIPE);
		
		Modules.Merger adder = (Modules.Merger) engine.add_module(Engine.Constants.MODULE_MERGER);
		adder.set_num_inputs(3);
		adder.set_operation(Modules.Merger.ADDITION);
		
		engine.connect_modules(osc1, Modules.Oscillator.OUTPUT_PIPE, adder, 0);
		engine.connect_modules(osc2, Modules.Oscillator.OUTPUT_PIPE, adder, 1);
		engine.connect_modules(osc3, Modules.Oscillator.OUTPUT_PIPE, adder, 2);
		
		adder.connect_output(engine.output, Modules.Merger.OUTPUT_PIPE);

		engine.start_playing(440);
		while (true)
		{
			engine.update();
		}
	}
}