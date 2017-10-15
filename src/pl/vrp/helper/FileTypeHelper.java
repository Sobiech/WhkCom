package pl.vrp.helper;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class FileTypeHelper {
    private JButton fileButton;
    private JLabel fileNameLabel;
    private String fileAbsolutePath;
    private String[] fileAbsolutePaths;
    private boolean fileSelected;
    
    public FileTypeHelper(Component that) {
        createJButton(that);
    }
    
    private void createJButton(final Component that) {
        this.fileNameLabel = new JLabel("Nie dodano pliku.");
        
        this.fileButton = new JButton(new AbstractAction("Dodaj dane") {
          private static final long serialVersionUID = 8455842882121464411L;
          
          public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int resultValue = fileChooser.showOpenDialog(that);
            if (resultValue == 0) {
                FileTypeHelper.this.fileAbsolutePath = fileChooser.getSelectedFile().getAbsolutePath();
                File[] files = fileChooser.getCurrentDirectory().listFiles();
                FileTypeHelper.this.fileSelected = true;
                
                FileTypeHelper.this.fileAbsolutePaths = new String[files.length];
                for (int i = 0; i < files.length; i++) {
                  FileTypeHelper.this.fileAbsolutePaths[i] = files[i].getAbsolutePath();
                }
                FileTypeHelper.this.fileNameLabel.setText(FileTypeHelper.this.fileAbsolutePath);
            }
          }
        });
    }
    
    public JButton getFileButton()
    {
        return this.fileButton;
    }
    
    public void setFileButton(JButton fileButton)
    {
        this.fileButton = fileButton;
    }
    
    public JLabel getFileNameLabel()
    {
        return this.fileNameLabel;
    }
    
    public void setFileNameLabel(JLabel fileNameLabel)
    {
        this.fileNameLabel = fileNameLabel;
    }
    
    public String getFileAbsolutePath()
    {
        return this.fileAbsolutePath;
    }
    
    public void setFileAbsolutePath(String fileAbsolutePath)
    {
        this.fileAbsolutePath = fileAbsolutePath;
    }
    
    public String[] getFileAbsolutePaths()
    {
        return this.fileAbsolutePaths;
    }
    
    public void setFileAbsolutePaths(String[] fileAbsolutePaths)
    {
        this.fileAbsolutePaths = fileAbsolutePaths;
    }
    
    public boolean isFileSelected()
    {
        return this.fileSelected;
    }
    
    public void setFileSelected(boolean fileSelected)
    {
        this.fileSelected = fileSelected;
    }
}
