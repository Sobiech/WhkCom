package pl.vrp.swing.textfield;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTextField;
import pl.vrp.exception.NoDataException;

public class TextField
    extends JTextField
{
    private static final long serialVersionUID = 2120899710833213792L;
    private int posX;
    private int posY;
    private String fieldLabel;
    private Component popupMessageCmp;
    
    public TextField(int posX, int posY, String label, String toolTip, Component component){
        this.posX = posX;
        this.posY = posY;
        this.fieldLabel = label;
        this.popupMessageCmp = component;
        
        createTextPrompt(label);
        setToolTipText(toolTip);
        setMinimumSize(new Dimension(160, 40));
    }
    
    private void createTextPrompt(String label){
        TextPrompt textPrompt = new TextPrompt(label, this);
        textPrompt.setForeground(Color.darkGray);
        textPrompt.changeAlpha(0.5F);
    }
    
    public Integer getInteger()
        throws NoDataException
    {
        try
        {
          return Integer.valueOf(Integer.parseInt(getText()));
        }
        catch (NumberFormatException e)
        {
          throw new NoDataException(getContentMessage("Integer np(1)."));
        }
    }
    
    public Float getFloat()
        throws NoDataException
    {
        try
        {
          return Float.valueOf(Float.parseFloat(getText()));
        }
        catch (NumberFormatException e)
        {
          throw new NoDataException(getContentMessage("Float np(1.1f)."));
        }
    }
    
    public Double getDouble()
        throws NoDataException
    {
        try
        {
          return Double.valueOf(Double.parseDouble(getText()));
        }
        catch (NumberFormatException e)
        {
          throw new NoDataException(getContentMessage("Double np (1.1)."));
        }
    }
    
    private String getContentMessage(String type){
        return "Pole:" + this.fieldLabel + ".\nPodana wartość nie jest prawidłowa. Spodziewano się typu " + type;
    }
    
    public int getPosX(){
        return this.posX;
    }
    
    public int getPosY(){
        return this.posY;
    }
    
    public void setPosX(int posX){
        this.posX = posX;
    }
    
    public void setPosY(int posY){
        this.posY = posY;
    }
    
    public Component getPopupMessageCmp(){
        return this.popupMessageCmp;
    }
    
    public void setPopupMessageCmp(Component popupMessageCmp){
        this.popupMessageCmp = popupMessageCmp;
    }
}
