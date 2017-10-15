package pl.vrp.swing.combo;

import javax.swing.JComboBox;
import pl.vrp.exception.NoDataException;

public class VrpComboBox<E> extends JComboBox<E>{
	
	private static final long serialVersionUID = -1387318832833660586L;

	public VrpComboBox(E[] values, String name){
		super(values);
		super.setName(name);
	}
	
	@SuppressWarnings("unchecked")
	public E getSelectedNode() throws NoDataException{
		if(getSelectedIndex() == 0){
			throw new NoDataException(getName(), true);
		}
		return (E)getSelectedItem();
	}
}
