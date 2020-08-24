package combination;

import java.awt.event.KeyListener;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;

//Settings for the main graph GUI
public class Window extends Canvas {
	public static JFrame frame = new JFrame();
	private static final long serialVersionUID = -5655431895904992825L;
	
	
	public Window(int width, int height, String title, Semsod semsod) {
	
		frame = new JFrame(title);
		Image icon = Toolkit.getDefaultToolkit().getImage(Gui.class.getResource("/images/Points.png"));
    	frame.setIconImage(icon);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.getContentPane().add(semsod);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.add(semsod);
		frame.setVisible(true);
	}
}
