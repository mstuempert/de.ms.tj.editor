package de.ms.tj.editor.preferences;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class SyntaxElementPreference {

	private boolean isInherit;

	private RGB foreground;

	private RGB background;

	private int style;

	public SyntaxElementPreference() {
		this(false, null, null, SWT.NONE);
	}

	public SyntaxElementPreference(boolean inherit, RGB foreground, RGB background, int style) {
		this.isInherit = inherit;
		this.foreground = foreground;
		this.background = background;
		this.style = style;
	}

	public boolean isInherit() {
		return this.isInherit;
	}

	public void setInherit(boolean b) {
		this.isInherit = b;
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

	public TextAttribute toTextAttributes(Display display) {
		return new TextAttribute(
				this.foreground == null ? null : new Color(display, this.foreground),
						this.background == null ? null : new Color(display, this.background),
								this.style);
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
