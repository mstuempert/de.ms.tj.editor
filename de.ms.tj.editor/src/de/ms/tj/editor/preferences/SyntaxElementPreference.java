package de.ms.tj.editor.preferences;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;

public class SyntaxElementPreference {
	
	private boolean isEnabled;
	
	private RGB foreground;
	
	private RGB background;
	
	private int style;
	
	public SyntaxElementPreference() {
		this(false, null, null, SWT.NONE);
	}
	
	public SyntaxElementPreference(boolean enabled, RGB foreground, RGB background, int style) {
		this.foreground = foreground;
		this.background = background;
		this.style = style;
	}
	
	public boolean isEnabled() {
		return this.isEnabled;
	}
	
	public void setEnabled(boolean b) {
		this.isEnabled = b;
	}

	public RGB getForeground() {
		return foreground;
	}

	public void setForeground(RGB foreground) {
		this.foreground = foreground;
	}

	public RGB getBackground() {
		return background;
	}

	public void setBackground(RGB background) {
		this.background = background;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}
	
	public boolean isBold() {
		return isStyleSet(SWT.BOLD);
	}
	
	public void setBold(boolean on) {
		setStyle(SWT.BOLD, on);
	}
	
	public boolean isItalic() {
		return isStyleSet(SWT.ITALIC);
	}
	
	public void setItalic(boolean on) {
		setStyle(SWT.ITALIC, on);
	}
	
	public boolean isUnderline() {
		return isStyleSet(TextAttribute.UNDERLINE);
	}
	
	public void setUnderline(boolean on) {
		setStyle(TextAttribute.UNDERLINE, on);
	}
	
	public boolean isStrikethrough() {
		return isStyleSet(TextAttribute.STRIKETHROUGH);
	}
	
	public void setStrikethrough(boolean on) {
		setStyle(TextAttribute.STRIKETHROUGH, on);
	}
	
	private boolean isStyleSet(int s) {
		return (this.style & s) != 0;
	}
	
	private void setStyle(int s, boolean on) {
		if (on) {
			this.style |= s;
		} else {
			this.style &= ~s;
		}
	}
	
	

}
