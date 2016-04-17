package de.ms.tj.editor.preferences;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public interface ISyntaxElementPreference {

	boolean isInherit();

	void setInherit(boolean b);

	RGB getForeground();

	void setForeground(RGB foreground);

	RGB getBackground();

	void setBackground(RGB background);

	int getStyle();

	void setStyle(int style);

	boolean isBold();

	void setBold(boolean on);

	boolean isItalic();

	void setItalic(boolean on);

	boolean isUnderline();

	void setUnderline(boolean on);

	boolean isStrikethrough();

	void setStrikethrough(boolean on);

	TextAttribute toTextAttributes(Display display);
	
}
