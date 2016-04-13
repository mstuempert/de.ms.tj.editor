package de.ms.tj.model;

import org.eclipse.jface.viewers.LabelProvider;

public class SyntaxLabelProvider extends LabelProvider {
	
	public static enum LabelType {
		ID, DISPLAY_NAME
	};
	
	private LabelType type;
	
	public SyntaxLabelProvider() {
		this(LabelType.DISPLAY_NAME);
	}
	
	public SyntaxLabelProvider(LabelType type) {
		this.type = type;
	}
	
	@Override
	public String getText(Object element) {
		return element instanceof ISyntaxElement
				? type == LabelType.ID
				? ((ISyntaxElement) element).getId()
						: ((ISyntaxElement) element).getDisplayName()
						: super.getText(element);
	}

}
