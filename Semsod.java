package combination;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Semsod extends Canvas{
	//Initialize Variables
	static String xRes = "";
	static String yRes = "";
	static int numPoints = 0;
	static BigDecimal[][] inputs = new BigDecimal[numPoints][2];
	Point origin = new Point(0,0);
	static float zoom = 1;
	int boxScale = 12;
	int scale = boxScale;
	List<Point> points = new ArrayList<>(Arrays.asList(origin));
	Rectangle r = Window.frame.getBounds();
	int height = r.height;
	int width = r.width;
	boolean start = false;
	int runCondition = 0;
	
	//Creates graph and zoom GUIs, and creates a runs the parametric function with predetermined inputs
	public Semsod() {
		this.parametric(0, numPoints - 1, .00001);
		//points.add(new Point(-2, 2));
		new Window((12*74)-7, (12*60), "SEMSOD: The Sequel", this);
		ZoomGui.createAndShowGUI(this);
	}
	

	public void paint(Graphics g) {
		//Draws the grid for the graph based on the size of the screen
		r = Window.frame.getBounds();
		height = r.height;
		width = r.width;
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.gray);
		for (int i = 0; i < width; i = i + boxScale) {
			g.drawLine(i, 0, i, height);
		}
		for (int j = 0; j < height; j = j + boxScale) {
			g.drawLine(0, j, width, j);
		}
		
		//Draws blue lines to indicate the X and Y axis
		int yAx = width/2;
		int xAx = height/2;
		g.setColor(Color.blue);
		int xCen = yAx;
		int yCen = xAx;
		xCen = xCen - (int) Math.IEEEremainder(xCen-1, boxScale);
		yCen = yCen - (int) Math.IEEEremainder(yCen-1, boxScale);
		g.drawLine(0, yCen, width, yCen);
		g.drawLine(xCen, height, xCen, 0);
		
		//Draws the points in the points list
		g.setColor(Color.red);
		for (int k = 0; k < points.size(); k++) {
			int xSign = -1;
			int ySign = 1;
			if (Math.abs(points.get(k).x) == points.get(k).x) {
				xSign = 1;
			}
			if (Math.abs(points.get(k).y) == points.get(k).y) {
				ySign = -1;
			}
			int len = (int) zoom;
			if (len > 4) {
				len = 4;
			}
			if (len < 1) {
				len = 1;
			}
			len = 1;
			if (zoom < 0) {
				scale = (int) (boxScale/Math.abs(zoom));
				zoom = 1/Math.abs(zoom);
			}
			if (zoom > 0) {
				scale = (int) (boxScale*zoom);
			}
			System.out.println(scale + " " + points.get(k).mainX + " " + points.get(k).mainY);
			g2.draw(new Ellipse2D.Double(xCen + scale*points.get(k).x - (int) Math.floorDiv(len, 2), yCen - scale*points.get(k).y - (int) Math.floorDiv(len, 2), len, len));
		}
		
		//Draws the input points given by the user
		g.setColor(Color.blue);
		for (int k = 0; k < inputs.length; k++) {
			int len = (int) zoom;
			if (len > 4) {
				len = 4;
			}
			if (len < 1) {
				len = 1;
			}
			len = 1;
			if (zoom < 0) {
				scale = (int) (boxScale/Math.abs(zoom));
				zoom = 1/Math.abs(zoom);
			}
			if (zoom > 0) {
				scale = (int) (boxScale*zoom);
			}
			System.out.println(scale + " " + points.get(k).mainX + " " + points.get(k).mainY);
			g2.fill(new Ellipse2D.Double(inputs[k][0].multiply(BigDecimal.valueOf(scale)).add(BigDecimal.valueOf((xCen - (int) Math.floorDiv(len, 2)))).doubleValue() - 3, BigDecimal.valueOf(yCen).subtract(inputs[k][1].multiply(BigDecimal.valueOf(scale))).subtract(BigDecimal.valueOf((int) Math.floorDiv(len, 2))).doubleValue() - 3, 6*len, 6*len));
		}
		
		//Prints the scale of zoom of the graph
		g.setColor(Color.red);
		g.drawString("Zoom scale: " + zoom, 2, 23);
	}
	
	
	public int xval(double t) {
		return (int) Math.pow(t-2, 2);
	}
	
	public int yval(double t) {
		return (int) t-4;
	}
	
	//Redraws the graph based on a new zoom value
	public void zoomScale(int i, Semsod semsod) {
		zoom = i;
		semsod.repaint();
	}

	//Adds points of a parametric function from tmin to max with a step of tstep
	public void parametric(int tmin, int tmax, double tstep) {
		for (double t = tmin; t < tmax; t = t + tstep) {
			this.newPoint(this.parX(t), this.parY(t));
		}
	}
	
	//Gives values for the x value of the parametric based on the calculations of the JavaManipulation class
	public double parX(double t) {
		String parXString = xRes;
		double parXVal = 0;
		int nu = 0;
		while (parXString.indexOf(" ") != -1){
			parXVal = parXVal + (Double.valueOf(parXString.substring(0, parXString.indexOf(" "))))*Math.pow(t, nu);
    	    parXString = parXString.substring(parXString.indexOf(" ") +1);
    	    nu++;
    	}
		parXVal = parXVal + (Double.valueOf(parXString))*Math.pow(t, nu);
		return parXVal;
	}
	
	//Gives values for the y value of the parametric based on the calculations of the JavaManipulation class
	public double parY(double t) {
		String parYString = yRes;
		double parYVal = 0;
		int nu = 0;
		while (parYString.indexOf(" ") != -1){
			parYVal = parYVal + (Double.valueOf(parYString.substring(0, parYString.indexOf(" "))))*Math.pow(t, nu);
    	    parYString = parYString.substring(parYString.indexOf(" ") + 1);
    	    nu++;
    	}
		parYVal = parYVal + (Double.valueOf(parYString))*Math.pow(t, nu);
		return parYVal;
	}
	
	
//	public double parX(double t) {
//		return ((0)*Math.pow(t,0) + (1.00000000000000000000)*Math.pow(t,1) + (-7.00000000000000000000)*Math.pow(t,2) + (4.00000000000000000000)*Math.pow(t,3)); 
//	}
	
//	public double parY(double t) {
//		return ((0)*Math.pow(t, 0) + (1.00000000000000000000)*Math.pow(t, 1) + (5.00000000000000000000)*Math.pow(t, 2) + (-4.00000000000000000000)*Math.pow(t, 3));
//	}

	//Adds a point to the points list
	public void newPoint(double x, double y) {
		points.add(new Point(x, y));
	}
	
	//Creates a splash screen for the application
	public static void splashScreen() {
		JWindow splash = new JWindow();
		URL url = Semsod.class.getResource("/images/Points.gif");
		ImageIcon imageIcon = new ImageIcon(url);
		JLabel label = new JLabel(imageIcon);
		splash.setContentPane(label);
		splash.setSize(256, 256);
		splash.setLocationRelativeTo(null);
		splash.setVisible(true);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		splash.setVisible(false);
		splash.dispose();
	}
	
	//The main function of the program, it starts the splash screen, then the selection GUI for the user to choose a process
	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		splashScreen();
		SelectionGui.createAndShowGUI(args);
//		while(!start);
//		if (this.runCondition == 1) JavaManipulation.main(args);
//		new Semsod();
	}
	
	//Returns the user input points to a string
	public String inputsToString() {
		String res = "";
		for (int i = 0; i < numPoints; i++) {
			res = res + "[" + inputs[i][0].toString() + "," + inputs[i][1].toString() + "]";
		}
		return res;
	}

}
