package pl.vrp.swing.textfield;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

public class TextPrompt
    extends JLabel
    implements FocusListener, DocumentListener
{
    private static final long serialVersionUID = -2708589907549236731L;
    private JTextComponent component;
    private Document document;
    private ShowPrompt show;
    private boolean showPromptOnce;
    private int focusLost;
    
    public static enum ShowPrompt
    {
        ALWAYS,  FOCUS_GAINED,  FOCUS_LOST;
    }
    
    public TextPrompt(String text, JTextComponent component)
    {
        this(text, component, ShowPrompt.ALWAYS);
    }
    
    public TextPrompt(String text, JTextComponent component, ShowPrompt show)
    {
        this.component = component;
        setShow(show);
        this.document = component.getDocument();
        
        setText(text);
        setFont(component.getFont());
        setForeground(component.getForeground());
        setBorder(new EmptyBorder(component.getInsets()));
        setHorizontalAlignment(10);
        
        component.addFocusListener(this);
        this.document.addDocumentListener(this);
        
        component.setLayout(new BorderLayout());
        component.add(this);
        checkForPrompt();
    }
    
    public void changeAlpha(float alpha)
    {
        changeAlpha((int)(alpha * 255.0F));
    }
    
    public void changeAlpha(int alpha)
    {
        alpha = alpha < 0 ? 0 : alpha > 255 ? 255 : alpha;
        
        Color foreground = getForeground();
        int red = foreground.getRed();
        int green = foreground.getGreen();
        int blue = foreground.getBlue();
        
        Color withAlpha = new Color(red, green, blue, alpha);
        super.setForeground(withAlpha);
    }
    
    public void changeStyle(int style)
    {
        setFont(getFont().deriveFont(style));
    }
    
    public ShowPrompt getShow()
    {
        return this.show;
    }
    
    public void setShow(ShowPrompt show)
    {
        this.show = show;
    }
    
    public boolean getShowPromptOnce()
    {
        return this.showPromptOnce;
    }
    
    public void setShowPromptOnce(boolean showPromptOnce)
    {
        this.showPromptOnce = showPromptOnce;
    }
    
    private void checkForPrompt()
    {
        if (this.document.getLength() > 0)
        {
          setVisible(false);
          return;
        }
        if ((this.showPromptOnce) && (this.focusLost > 0))
        {
          setVisible(false);
          return;
        }
        if (this.component.hasFocus())
        {
          if ((this.show == ShowPrompt.ALWAYS) || 
            (this.show == ShowPrompt.FOCUS_GAINED)) {
            setVisible(true);
          } else {
            setVisible(false);
          }
        }
        else if ((this.show == ShowPrompt.ALWAYS) || 
          (this.show == ShowPrompt.FOCUS_LOST)) {
          setVisible(true);
        } else {
          setVisible(false);
        }
    }
    
    public void focusGained(FocusEvent e)
    {
        checkForPrompt();
    }
    
    public void focusLost(FocusEvent e)
    {
        this.focusLost += 1;
        checkForPrompt();
    }
    
    public void insertUpdate(DocumentEvent e)
    {
        checkForPrompt();
    }
    
    public void removeUpdate(DocumentEvent e)
    {
        checkForPrompt();
    }
    
    public void changedUpdate(DocumentEvent e) {}
}
