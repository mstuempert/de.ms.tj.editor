package de.ms.tj.editor.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.osgi.service.prefs.BackingStoreException;

import de.ms.tj.editor.internal.Activator;
import de.ms.tj.model.ISyntaxElementLibrary;
import de.ms.tj.model.SyntaxBrowser;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public PreferenceInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		
		PreferenceManager pm = new PreferenceManager();
		SyntaxBrowser sb = SyntaxBrowser.getInstance();
		
		try {
			
			pm.setSyntaxElementPreference(
					sb.getElementById(ISyntaxElementLibrary.COMMENT),
					new SyntaxElementPreference(
							false,
							new RGB( 0x00, 0xA0, 0x00),
							null,
							SWT.ITALIC));
			
			pm.setSyntaxElementPreference(
					sb.getElementById(ISyntaxElementLibrary.COMMENT),
					new SyntaxElementPreference(
							false,
							new RGB( 0x00, 0x00, 0xA0),
							null,
							0));
			
		} catch (BackingStoreException e) {
			Activator.logException(e);
		}
		
	}

}
