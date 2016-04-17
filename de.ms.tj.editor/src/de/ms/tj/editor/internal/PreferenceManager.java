package de.ms.tj.editor.internal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.osgi.service.prefs.BackingStoreException;

import de.ms.tj.editor.preferences.IPreferenceManager;
import de.ms.tj.editor.preferences.ISyntaxElementPreference;
import de.ms.tj.model.ISyntaxContainer;
import de.ms.tj.model.ISyntaxElement;
import de.ms.tj.model.SyntaxBrowser;

class PreferenceManager implements IPreferenceManager {
	
	private HashMap<ISyntaxElement, SyntaxElementPreference> syntaxElementPreferences
	= new HashMap<ISyntaxElement, SyntaxElementPreference>();
	
	PreferenceManager() {
		initializeDefaults();
		loadPreferences();
	}

	@Override
	public ISyntaxElementPreference getSyntaxElementPreference(ISyntaxElement e, boolean useInheritance) {
		ISyntaxElementPreference result = this.syntaxElementPreferences.get(e);
		while (((result == null) || result.isInherit()) && useInheritance && (e.getParent() != null)) {
			e = e.getParent();
			result = this.syntaxElementPreferences.get(e);
		}
		return result == null ? new SyntaxElementPreference() : result;
	}
	
	public void loadSyntaxElementPreferences() {
		
		IEclipsePreferences node = getPreferenceNode(PREFERENCE_NODE_SYNTAX_ELEMENT);
		
		Queue<ISyntaxElement> elements = new LinkedList<ISyntaxElement>();
		elements.add(SyntaxBrowser.INSTANCE.getSyntaxRoot());
		
		while (!elements.isEmpty()) {
			
			ISyntaxElement element = elements.poll();
			
			if (element instanceof ISyntaxContainer) {
				for (ISyntaxElement e : ((ISyntaxContainer) element).getChildren()) {
					elements.add(e);
				}
			}
			
			String s = node.get(element.getId(), null);
			if (s != null) {
				SyntaxElementPreference preference = SyntaxElementPreference.fromString(s);
				if (preference != null) {
					this.syntaxElementPreferences.put(element, preference);
				}
			}
			
		}
		
	}
	
	public void storeSyntaxElementPreferences() throws BackingStoreException {
		
		IEclipsePreferences node = getPreferenceNode(PREFERENCE_NODE_SYNTAX_ELEMENT);
		
		for (Entry<ISyntaxElement, SyntaxElementPreference> e : this.syntaxElementPreferences.entrySet()) {
			node.put(e.getKey().getId(), e.getValue().toPreferenceString());
		}
		
		node.flush();
		
	}

	@Override
	public void resetSytaxElementPreferences() {
		DefaultPreferences.INSTANCE.initializePreferenceManager(this);
	}

	@Override
	public void resetSytaxElementPreference(ISyntaxElement e) {
		DefaultPreferences.INSTANCE.resetSyntaxElementPreference(this, e);
	}

	@Override
	public void setSyntaxElementPreference(ISyntaxElement e, ISyntaxElementPreference p) {
		this.syntaxElementPreferences.put(e, SyntaxElementPreference.fromSyntaxElementPreference(p));		
	}

	protected void initializeDefaults() {
		DefaultPreferences.INSTANCE.initializePreferenceManager(this);
	}

	protected void loadPreferences() {
		loadSyntaxElementPreferences();
	}
	
	protected IEclipsePreferences getPreferenceNode(String node) {
		return ConfigurationScope.INSTANCE.getNode(node);
	}

	@Override
	public void addChangeListener(String node, IPreferenceChangeListener l) {
		getPreferenceNode(node).addPreferenceChangeListener(l);
	}

	@Override
	public void removeChangeListener(String node, IPreferenceChangeListener l) {
		getPreferenceNode(node).removePreferenceChangeListener(l);
	}
	
	/*
	PreferenceManager() {
		initDefaults();
	}
	
	public ISyntaxElementPreference getSyntaxElementPreference(ISyntaxElement e, boolean useInheritance) {
		ISyntaxElementPreference result = null;
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
	
	public void resetSytaxElementPreferences(ISyntaxElement e) {};
	
	public void setSyntaxElementPreference(ISyntaxElement e, ISyntaxElementPreference p) throws BackingStoreException {
		setSyntaxElementPreference(getPreferences(), e, p);
	}
	
	private void setSyntaxElementPreference(IEclipsePreferences pref, ISyntaxElement e, ISyntaxElementPreference p) throws BackingStoreException {
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
		
	}*/
	
}
