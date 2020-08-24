package combination;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

//An experimental GUI class that is used to alter and create more complex GUIs
//Used for personal reference
public class Gui {   
    public static void run(String text) {
    	JFrame frame = new JFrame("Result");  
    	Image icon = Toolkit.getDefaultToolkit().getImage(Gui.class.getResource("/images/Points.png"));
    	frame.setIconImage(icon);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        Container cp = frame.getContentPane();  
        JTextPane pane = new JTextPane();  
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();  
        StyleConstants.setBold(attributeSet, true);  
  
        pane.setCharacterAttributes(attributeSet, true);  
        pane.setText(text);  

        JScrollPane scrollPane = new JScrollPane(pane);  
        cp.add(scrollPane, BorderLayout.CENTER);  
  
        frame.setSize(400, 300);  
        frame.setVisible(true);  
    }
 
}