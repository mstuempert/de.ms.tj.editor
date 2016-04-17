package de.ms.tj.editor;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.swt.widgets.Display;

import de.ms.tj.editor.preferences.IPreferenceManager;
import de.ms.tj.editor.preferences.ISyntaxElementPreference;
import de.ms.tj.model.ISyntaxElement;

class SyntaxConfigurationToken implements IToken {
	
	private IPreferenceManager pManager;
	
	private ISyntaxElement sElement;
	
	SyntaxConfigurationToken(IPreferenceManager pManager, ISyntaxElement sElement) {
		this.pManager = pManager;
		this.sElement = sElement;
	}

	@Override
	public boolean isUndefined() {
		return false;
	}

	@Override
	public boolean isWhitespace() {
		return false;
	}

	@Override
	public boolean isEOF() {
		return false;
	}

	@Override
	public boolean isOther() {
		return false;
	}

	@Override
	public Object getData() {
		ISyntaxElementPreference p = this.pManager.getSyntaxElementPreference(this.sElement, true);
		return p != null ? p.toTextAttributes(Display.getDefault()) : null;
	}

}
