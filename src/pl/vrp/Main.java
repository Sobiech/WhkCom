package pl.vrp;


import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import pl.vrp.swing.frame.MainFrame;

public class Main {
	
    public static void main(String[] args) {
        try
        {
          //String NIMBUS_LOOK_AND_FEEL = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
          String WEB_LOOK_AND_FEEL = "com.alee.laf.WebLookAndFeel";
          UIManager.setLookAndFeel(WEB_LOOK_AND_FEEL);
          MainFrame mainFrame = new MainFrame();
          mainFrame.setVisible(true);
        }
        catch (ClassNotFoundException e)
        {
          e.printStackTrace();
        }
        catch (InstantiationException e)
        {
          e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
          e.printStackTrace();
        }
        catch (UnsupportedLookAndFeelException e)
        {
          e.printStackTrace();
        }
    }
    
}
