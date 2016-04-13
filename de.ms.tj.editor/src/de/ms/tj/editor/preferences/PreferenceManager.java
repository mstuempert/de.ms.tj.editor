package de.ms.tj.editor.preferences;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.osgi.service.prefs.BackingStoreException;

import de.ms.tj.model.ISyntaxElement;

public class PreferenceManager {

	private static final String PREFERENCE_NODE = "tj";
	
	private static final String INHERIT_KEY = "_inherit";
	
	private static final String FG_KEY = "_fg";

	private static final String BG_KEY = "_bg";
	
	private static final String STYLE_KEY = "_style";
	
	public SyntaxElementPreference getSyntaxElementPreference(ISyntaxElement e, boolean useInheritance) {
		SyntaxElementPreference result = null;
		String id = e.getId();
		IEclipsePreferences p = getPreferences();
		boolean inherit = p.getBoolean(id+INHERIT_KEY, true);
		if (inherit && useInheritance && e.getParent() != null) {
			result = getSyntaxElementPreference(e.getParent(), useInheritance);
		} else {
			String s = p.get(id+FG_KEY, null);
			RGB fgColor = s == null ? null : StringConverter.asRGB(s);
			s = p.get(id+BG_KEY, null);
			RGB bgColor = s == null ? null : StringConverter.asRGB(s);
			int style = p.getInt(id+STYLE_KEY, SWT.NONE);
			result = new SyntaxElementPreference(inherit, fgColor, bgColor,style);
		}
		return result;
	}
	
	public void setSyntaxElementPreference(ISyntaxElement e, SyntaxElementPreference p) throws BackingStoreException {
		String id = e.getId();
		IEclipsePreferences pref = getPreferences();
		pref.putBoolean(id+INHERIT_KEY, p.isInherit());
		if (p.getForeground() != null) {
			pref.put(id+FG_KEY, StringConverter.asString(p.getForeground()));
		} else {
			pref.remove(id+FG_KEY);
		}
		if (p.getBackground() != null) {
			pref.put(id+BG_KEY, StringConverter.asString(p.getBackground()));
		} else {
			pref.remove(id+BG_KEY);
		}
		pref.putInt(id+STYLE_KEY, p.getStyle());
		pref.flush();
	}
	
	protected IEclipsePreferences getPreferences() {
		return ConfigurationScope.INSTANCE.getNode(PREFERENCE_NODE);
	}
	
}
