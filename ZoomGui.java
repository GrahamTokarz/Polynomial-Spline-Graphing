package combination;
 
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFrame;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Formatter;

//Creates a GUI with an action listener and buttons that allows the control of the zoom scale of the main graph GUI
public class ZoomGui extends JPanel implements ActionListener {
    protected JButton b1;
    Semsod semsod;
    SpinnerModel model = new SpinnerNumberModel(1, -300, 30, 1);
    JSpinner spinner = new JSpinner(model);
    
    //Creates the GUI
    public ZoomGui(Semsod semsod) {
    	this.semsod = semsod;
        b1 = new JButton("Submit zoom value (Value of the spinner)");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING);
        b1.setActionCommand("zoom");
        JButton b2 = new JButton("Save As");
        b2.setVerticalTextPosition(AbstractButton.CENTER);
        b2.setHorizontalTextPosition(AbstractButton.LEADING);
        b2.setActionCommand("save");
        //b3 = new JButton("Zoom in - Larger points");
        //b3.setActionCommand("anti-zoom");
 
        b1.addActionListener(this);
        b2.addActionListener(this);
      //b3.addActionListener(this);
 
        add(b2);
        add(b1);
      //add(b3);
        add(spinner);
    }
    
  
    //Action listener, listens for either the push of the save button, or the changing and submission of the zoom spinner
    public void actionPerformed(ActionEvent e) {
    	
    	//Sets the zoom scale of the main graph to the value of the spinner
        if ("zoom".equals(e.getActionCommand())) {
        	this.semsod.zoomScale((int) spinner.getValue(), semsod);
    	}
        
        //Allows the user to choose a location to save a .lmw file in a location of their choosing with a name of their choosing
        if("save".equals(e.getActionCommand())){
        	String path = "";
        	JButton init = new JButton();
        	JFileChooser fc = new JFileChooser();
    		fc.setCurrentDirectory(new File(System.getProperty("user.home")));
    		fc.setDialogTitle("Please name your file");
    		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    		fc.setApproveButtonText("Save");
    		fc.addChoosableFileFilter(new FileNameExtensionFilter("Lawnmower Files (*lwm)", "lwm"));
    		fc.setAcceptAllFileFilterUsed(false);
    		if (fc.showOpenDialog(init) == JFileChooser.APPROVE_OPTION) {}
    		if (fc.getSelectedFile() != null) path = fc.getSelectedFile().getAbsolutePath().replace("\\", "\\\\");
    		if (!path.endsWith(".lwm")) path = path + ".lwm";
    		//Saves the project data
    		try {
        		final Formatter lwm;
    			lwm = new Formatter(path);
    			lwm.format("%s%s\n%s%s\n%s%s", "pnts = ", semsod.inputsToString(), "xfunc = ", semsod.xRes, "yfunc = ", semsod.yRes);
    			lwm.close();
    		}
    		catch (Exception ex) {
    			System.out.println("Error");
    		}
    		
        }
    }
 
    //Displays the GUI
    public static void createAndShowGUI(Semsod semsod) {
        JFrame frame = new JFrame("Controls");
        Image icon = Toolkit.getDefaultToolkit().getImage(Gui.class.getResource("/images/Points.png"));
    	frame.setIconImage(icon);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        ZoomGui newContentPane = new ZoomGui(semsod);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
 
        frame.pack();
        frame.setVisible(true);
    }
}