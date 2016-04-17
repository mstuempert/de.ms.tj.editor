package de.ms.tj.editor.preferences;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;

public class NullableColorSelector extends ColorSelector {

	private boolean isNull;
	
	public NullableColorSelector(Composite parent) {
		super(parent);
		this.isNull = false;
		addListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getNewValue() != null) {
					isNull = false;
				}
			}
		});
	}
	
	public boolean isNull() {
		return this.isNull;
	}
	
    public RGB getColorValue() {
        return this.isNull ? null : super.getColorValue();
    }
	
    public void setColorValue(RGB rgb) {
    	this.isNull = rgb == null;
        super.setColorValue(this.isNull ? getButton().getBackground().getRGB() : rgb);
    }

}
