package de.ms.tj.editor.preferences;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;

import de.ms.tj.model.ISyntaxElement;

public interface IPreferenceManager {

	SyntaxElementPreference getSyntaxElementPreference(ISyntaxElement e, boolean useInheritance);
	
	void setSyntaxElementPreference(ISyntaxElement e, SyntaxElementPreference p) throws BackingStoreException;
	
	public IEclipsePreferences getPreferences();
	
}
