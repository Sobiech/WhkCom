package pl.vrp.swing.panel;

import java.awt.Component;

import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class FormPanel extends JPanel {
    
	private static final long serialVersionUID = -8641067756800717542L;
    
	private String centerPanelLayoutColumns;
    private String centerPanelLayoutRows;
    
    private CellConstraints cc;
    private CellConstraints.Alignment cellAlignment;
    
    public FormPanel(String columns, String rows){
        this.centerPanelLayoutColumns = columns;
        this.centerPanelLayoutRows = rows;
        initalizePanel();
    }
    
    private void initalizePanel(){
        FormLayout formLayout = new FormLayout(this.centerPanelLayoutColumns, this.centerPanelLayoutRows);
        setLayout(formLayout);
        
        this.cc = new CellConstraints();
        this.cellAlignment = CellConstraints.FILL;
    }
    
    protected boolean addComponent(Component component, int posX, int posY, Integer width, Integer height){
        add(component, this.cc.xywh(posX, posY, width.intValue(), height.intValue(), this.cellAlignment, this.cellAlignment));
        return true;
    }
    
    protected boolean addComponent(Component component, int posX, int posY, Integer width){
        add(component, this.cc.xyw(posX, posY, width.intValue(), this.cellAlignment, this.cellAlignment));
        return true;
    }
}
