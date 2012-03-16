/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 
 
 * Moustafa Ismael*Ayoub Benguedouar* Mohammad Abdussalaam*
 
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        AppFrame frame = new AppFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());
        //Display the window.
        frame.pack();
        frame.setVisible(true);
            
    }

}
