package de.ms.tj.editor.internal;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.osgi.service.prefs.BackingStoreException;

import de.ms.tj.editor.preferences.IPreferenceManager;
import de.ms.tj.editor.preferences.SyntaxElementPreference;
import de.ms.tj.model.ISyntaxElement;
import de.ms.tj.model.ISyntaxElementLibrary;
import de.ms.tj.model.SyntaxBrowser;

class PreferenceManager implements IPreferenceManager {

	private static final String PREFERENCE_NODE = Activator.PLUGIN_ID;
	
	private static final String INHERIT_KEY = "_inherit";
	
	private static final String FG_KEY = "_fg";

	private static final String BG_KEY = "_bg";
	
	private static final String STYLE_KEY = "_style";
	
	PreferenceManager() {
		initDefaults();
	}
	
	public SyntaxElementPreference getSyntaxElementPreference(ISyntaxElement e, boolean useInheritance) {
		SyntaxElementPreference result = null;
		IEclipsePreferences pref = getPreferences();
		IEclipsePreferences dPref = getDefaultPreferences();
		String id = e.getId();
		boolean inherit = pref.getBoolean(id+INHERIT_KEY, dPref.getBoolean(id+INHERIT_KEY, true));
		if (inherit && useInheritance && e.getParent() != null) {
			result = getSyntaxElementPreference(e.getParent(), useInheritance);
		} else {
			String s = pref.get(id+FG_KEY, dPref.get(id+FG_KEY, null));
			RGB fgColor = s == null ? null : StringConverter.asRGB(s);
			s = pref.get(id+BG_KEY, dPref.get(id+BG_KEY, null));
			RGB bgColor = s == null ? null : StringConverter.asRGB(s);
			int style = pref.getInt(id+STYLE_KEY, dPref.getInt(id+STYLE_KEY, SWT.NONE));
			result = new SyntaxElementPreference(inherit, fgColor, bgColor,style);
		}
		return result;
	}
	
	public void resetDefaults() throws BackingStoreException {
		getPreferences().clear();
	}
	
	public void setSyntaxElementPreference(ISyntaxElement e, SyntaxElementPreference p) throws BackingStoreException {
		setSyntaxElementPreference(getPreferences(), e, p);
	}
	
	private void setSyntaxElementPreference(IEclipsePreferences pref, ISyntaxElement e, SyntaxElementPreference p) throws BackingStoreException {
		String id = e.getId();
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
	
	public IEclipsePreferences getPreferences() {
		return ConfigurationScope.INSTANCE.getNode(PREFERENCE_NODE);
	}
	
	protected IEclipsePreferences getDefaultPreferences() {
		return DefaultScope.INSTANCE.getNode(PREFERENCE_NODE);
	}
	
	protected void initDefaults() {
		
		IEclipsePreferences p = getDefaultPreferences();
		SyntaxBrowser browser = SyntaxBrowser.getInstance();
		
		try {
			
			setSyntaxElementPreference(p,
					browser.getElementById(ISyntaxElementLibrary.COMMENT),
					new SyntaxElementPreference(
							false,
							new RGB(0x00, 0xA0, 0x00),
							null,
							SWT.ITALIC));
			
			setSyntaxElementPreference(p,
					browser.getElementById(ISyntaxElementLibrary.STRING),
					new SyntaxElementPreference(
							false,
							new RGB(0x00, 0x00, 0xA0),
							null,
							SWT.BOLD));
			
		} catch (BackingStoreException e) {
			Activator.logException(e);
		}
		
	}
	
}
