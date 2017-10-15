package pl.vrp.swing.panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class TitlePanel
    extends JPanel
{
    private static final long serialVersionUID = 2099827717738769147L;
    
    public TitlePanel(String title){
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setBorder(new CompoundBorder(new EmptyBorder(4, 4, 4, 4), new MatteBorder(0, 0, 1, 0, Color.BLACK)));
        JLabel label = new JLabel(title);
        label.setFont(label.getFont().deriveFont(1));
        add(label);
    }
}
