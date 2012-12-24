package GUI;

// Generated with Google's WindowBuilder

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class MainWindow
{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public void createWindow(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnMakeSound = new JButton("Make Sound");
		btnMakeSound.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clicked on button");
			}
		});
		frame.getContentPane().add(btnMakeSound, BorderLayout.SOUTH);
		
		JSlider FrequencySlider = new JSlider();
		FrequencySlider.setToolTipText("Frequency");
		FrequencySlider.setValue(440);
		FrequencySlider.setMinimum(1);
		FrequencySlider.setMaximum(1000);
		frame.getContentPane().add(FrequencySlider, BorderLayout.CENTER);
	}

}
