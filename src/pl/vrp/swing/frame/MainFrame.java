package pl.vrp.swing.frame;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import pl.vrp.swing.menu.MenuBar;
import pl.vrp.swing.panel.CenterPanel;

public class MainFrame extends Frame {
    private static final long serialVersionUID = 1L;
    private CenterPanel centerPanel;
    
    public MainFrame() {
        super(1100, 640, "Genetyczna optymalizacja MDVRP", true);
        this.setResizable(false);
        initalizeFrame();
    }
    
    private void initalizeFrame(){
        this.centerPanel = new CenterPanel(this);
        this.contentPane.setLayout(new BorderLayout());
        setJMenuBar(new MenuBar(this.centerPanel.getStartActionListener()));
        this.contentPane.add(this.centerPanel);
    }
    
    public JPanel getMainPane(){
    	return this.contentPane;
    }
}
