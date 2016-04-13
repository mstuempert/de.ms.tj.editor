package de.ms.tj.editor.preferences;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;

import de.ms.tj.model.ISyntaxElement;

public class PreferenceManager {

	private static final String PREFERENCE_NODE = "tj";
	
	private static final String ENABLED_KEY = "_enabled";
	
	private static final String FG_KEY = "_fg";

	private static final String BG_KEY = "_bg";
	
	private static final String STYLE_KEY = "_style";
	
	public SyntaxElementPreference getSyntaxElementPreference(ISyntaxElement e) {
		return getSyntaxElementPreference(e.getId());
	}
	
	public void setSyntaxElementPreference(ISyntaxElement e, SyntaxElementPreference p) {
		setSyntaxElementPreference(e.getId(), p);
	}
	
	protected SyntaxElementPreference getSyntaxElementPreference(String key) {
		IEclipsePreferences p = getPreferences();
		boolean enabled = p.getBoolean(key+ENABLED_KEY, true);
		String s = p.get(key+FG_KEY, null);
		RGB fgColor = s == null ? null : StringConverter.asRGB(s);
		s = p.get(key+BG_KEY, null);
		RGB bgColor = s == null ? null : StringConverter.asRGB(s);
		int style = p.getInt(key+STYLE_KEY, SWT.NONE);
		return new SyntaxElementPreference(enabled, fgColor, bgColor,style);
	}
	
	protected void setSyntaxElementPreference(String key, SyntaxElementPreference p) {
		IEclipsePreferences pref = getPreferences();
		pref.putBoolean(key+ENABLED_KEY, p.isEnabled());
		pref.put(key+FG_KEY, StringConverter.asString(p.getForeground()));
		pref.put(key+BG_KEY, StringConverter.asString(p.getBackground()));
		pref.putInt(key+STYLE_KEY, p.getStyle());
	}
	
	protected IEclipsePreferences getPreferences() {
		return ConfigurationScope.INSTANCE.getNode(PREFERENCE_NODE);
	}
	
}
