package pl.vrp.swing.slider;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pl.vrp.exception.SliderValueException;

public class FloatSlider extends JSlider{

	private static final long serialVersionUID = -186365735757638948L;

	private int panelPositionX , panelPositionY;
	private String sliderName;
	
	public FloatSlider(int x, int y, String sliderName){
		super();
		this.panelPositionX = x;
		this.panelPositionY = y;
		this.sliderName = sliderName;
		this.setToolTipText(sliderName);
		
		this.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent ce) {
	            JSlider slider = (JSlider)ce.getSource();
	            if (!slider.getValueIsAdjusting()) {
	                try {
						slider.setToolTipText(sliderName +  " = ["+String.valueOf(getFloatValue())+ "]");
	                } catch (SliderValueException e) {
	                	slider.setToolTipText(sliderName +  " = [0.0]");
					}
	            }
	        }
	    });
	}
	
	public float getFloatValue() throws SliderValueException{
		float value = (float) getValue() / 100;
		if(value <= 0.0){
			throw new SliderValueException(sliderName);
		}
		
		return value;
	}

	public int getPosX() {
		return panelPositionX;
	}

	public void setPanelPositionX(int panelPositionX) {
		this.panelPositionX = panelPositionX;
	}

	public int getPosY() {
		return panelPositionY;
	}

	public void setPanelPositionY(int panelPositionY) {
		this.panelPositionY = panelPositionY;
	}
	
}
