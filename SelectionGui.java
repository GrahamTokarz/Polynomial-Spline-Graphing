package combination;
 
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFrame;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.util.Formatter;
import java.util.Scanner;

//Launches a GUI with a selection to create a new project or open an existing project using buttons and action listeners
public class SelectionGui extends JPanel implements ActionListener {
	protected JButton b1;
    static JFrame frame = new JFrame("Please make a selection");
    static String[] ar;

    //Creates the GUI
    public SelectionGui() {
        b1 = new JButton("Create New Project");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING);
        b1.setActionCommand("new");
        JButton b2 = new JButton("Open Existing Project");
        b2.setVerticalTextPosition(AbstractButton.CENTER);
        b2.setHorizontalTextPosition(AbstractButton.LEADING);
        b2.setActionCommand("open");
        b1.addActionListener(this);
        b2.addActionListener(this);
        add(b2);
        add(b1);
    }
    
  
    //Event listener for the buttons
    public void actionPerformed(ActionEvent e) {
    	//Starts a new project
        if ("new".equals(e.getActionCommand())) {
        	frame.setVisible(false);
        	JavaManipulation.main(ar);
        	new Semsod();
    	}
        
        //Allows the user to choose any .lwm file
        if("open".equals(e.getActionCommand())){
        	String path = "";
        	boolean selected = false;
        	while (!selected) {
	        	JButton init = new JButton();
	        	JFileChooser fc = new JFileChooser();
	    		fc.setCurrentDirectory(new File(System.getProperty("user.home")));
	    		fc.setDialogTitle("Please choose your file");
	    		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    		fc.addChoosableFileFilter(new FileNameExtensionFilter("Lawnmower Files (*lwm)", "lwm"));
	    		fc.setAcceptAllFileFilterUsed(false);
	    		if (fc.showOpenDialog(init) == JFileChooser.APPROVE_OPTION) {}
	    		if (fc.getSelectedFile() != null) path = fc.getSelectedFile().getAbsolutePath().replace("\\", "\\\\");
	    		if (!path.endsWith(".lwm")) path = path + ".lwm";
	    		File file = new File(path);
	    		if (file.exists()) selected = true;
        	}
        	
        	//Uses the written file data to display a new graph with the same displayed graph
        	try {
    			Scanner scan = new Scanner(new File(path));
    			String points = scan.nextLine();
    			String xFunc = scan.nextLine();
    			String yFunc = scan.nextLine();
    			points = points.substring(points.indexOf("["));
    			Semsod.xRes = xFunc.substring(8);
    			Semsod.yRes = yFunc.substring(8);
    			Semsod.numPoints = points.length() - points.replace("[", "").length();
    			Semsod.inputs = new BigDecimal[Semsod.numPoints][2];
    			for (int i = 0; i < Semsod.numPoints; i++) {
    				System.out.println(Semsod.xRes);
    				System.out.println(Semsod.yRes);
    				Semsod.inputs[i][0] = new BigDecimal(points.substring(1, points.indexOf(",")));
    				Semsod.inputs[i][1] = new BigDecimal(points.substring(points.indexOf(",") + 1, points.indexOf("]")));
    				points = points.substring(points.indexOf("]") + 1);
    			}
    			new Semsod();
    			frame.setVisible(false);
    		}
    		catch (Exception er) {
    			er.printStackTrace();
    		}
        }
    }
 
    //Displays the GUI
    public static void createAndShowGUI(String[] args) {
    	ar = args;
        Image icon = Toolkit.getDefaultToolkit().getImage(Gui.class.getResource("/images/Points.png"));
    	frame.setIconImage(icon);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        SelectionGui newContentPane = new SelectionGui();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        
        frame.pack();
        frame.setSize(450, 80);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
