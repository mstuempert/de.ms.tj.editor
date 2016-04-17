package de.ms.tj.editor.preferences;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;

import de.ms.tj.editor.internal.Activator;
import de.ms.tj.model.ISyntaxElement;

public interface IPreferenceManager {
	
	static final String PREFERENCE_NODE = "/" + Activator.PLUGIN_ID;

	static final String PREFERENCE_NODE_SYNTAX_ELEMENT = PREFERENCE_NODE + "/syntax_element";

	ISyntaxElementPreference getSyntaxElementPreference(ISyntaxElement e, boolean useInheritance);
	
	void loadSyntaxElementPreferences();
	
	void resetSytaxElementPreferences();
	
	void resetSytaxElementPreference(ISyntaxElement e);

	void setSyntaxElementPreference(ISyntaxElement e, ISyntaxElementPreference p);
	
	void storeSyntaxElementPreferences() throws BackingStoreException;
	
	void addChangeListener(String node, IEclipsePreferences.IPreferenceChangeListener l);
	
	void removeChangeListener(String node, IEclipsePreferences.IPreferenceChangeListener l);
	
}