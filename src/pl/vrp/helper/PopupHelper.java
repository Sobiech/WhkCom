package pl.vrp.helper;

import java.awt.Component;
import javax.swing.JOptionPane;

public class PopupHelper
{
    public static void showWarn(Component component, String content)
    {
        JOptionPane.showMessageDialog(component, 
          content, 
          "Uwaga!", 
          2);
    }
    
    public static void showError(Component component, String content)
    {
        JOptionPane.showMessageDialog(component, 
          content, 
          "Błąd!", 
          0);
    }
    
    public static void showInfo(Component component, String content)
    {
        JOptionPane.showMessageDialog(component, 
          content, 
          "Informacja", 
          1);
    }
}
