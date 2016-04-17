package de.ms.tj.editor.internal;

import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import de.ms.tj.editor.preferences.ISyntaxElementPreference;

public class SyntaxElementPreference implements ISyntaxElementPreference {

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
	
	@Override
	public String toString() {
		return "SyntaxElementPreference[isInherit=" + isInherit
				+ "; foreground=" + (this.foreground == null ? "null" : StringConverter.asString(this.foreground))
				+ "; background=" + (this.background == null ? "null" : StringConverter.asString(this.background))
				+ "; style=" + this.style
				+ "]";
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
	
	static SyntaxElementPreference fromSyntaxElementPreference(ISyntaxElementPreference p) {
		return (p instanceof SyntaxElementPreference)
				? (SyntaxElementPreference) p
						: new SyntaxElementPreference(p.isInherit(), p.getForeground(), p.getBackground(), p.getStyle());
	}
	
	static SyntaxElementPreference fromString(String s) {
		SyntaxElementPreference result = null;
		String[] parts = s.split(";");
		if (parts.length == 4) {
			boolean isInherit = Boolean.parseBoolean(parts[0]);
			RGB foreground = "null".equals(parts[1]) ? null : StringConverter.asRGB(parts[1]);
			RGB background = "null".equals(parts[2]) ? null : StringConverter.asRGB(parts[2]);
			int style = Integer.parseInt(parts[3]);
			result = new SyntaxElementPreference(isInherit, foreground, background, style);
		}
		return result;
	}

	String toPreferenceString() {
		return Boolean.toString(this.isInherit) + ";"
				+ (this.foreground == null ? "null" : StringConverter.asString(this.foreground)) + ";"
				+ (this.background == null ? "null" : StringConverter.asString(this.background)) + ";"
				+ Integer.toString(this.style);
	}

}
