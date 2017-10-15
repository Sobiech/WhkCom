package pl.vrp.swing.frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private int WIDTH;
    private int HEIGHT;
    
    private boolean isMainFrame;
    protected JPanel contentPane;
    
    public Frame(int width, int height, String title, boolean mainFrame) {
        super(title);
        
        this.WIDTH = width;
        this.HEIGHT = height;
        this.isMainFrame = mainFrame;
        initalizeFrame();
    }
    
    private void initalizeFrame(){
        addWindowListener(new WindowAdapter()
        {
          public void windowClosing(WindowEvent e)
          {
            Frame.this.dispose();
            if (Frame.this.isMainFrame) {
                System.exit(0);
            }
          }
        });
        Dimension frameSize = new Dimension(this.WIDTH, this.HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        setMinimumSize(frameSize);
        if (frameSize.width > screenSize.width) {
          frameSize.width = screenSize.width;
        }
        if (frameSize.height > screenSize.height) {
          frameSize.height = screenSize.height;
        }
        setSize(frameSize);
        setLocation(
          (screenSize.width - frameSize.width) / 2, 
          (screenSize.height - frameSize.height) / 2);
        
        this.contentPane = ((JPanel)getContentPane());
    }
}
