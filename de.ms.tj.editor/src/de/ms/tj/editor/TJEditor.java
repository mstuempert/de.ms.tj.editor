package de.ms.tj.editor;

import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.ui.editors.text.TextEditor;
import org.osgi.service.prefs.Preferences;

import de.ms.tj.editor.internal.Activator;
import de.ms.tj.editor.preferences.IPreferenceManager;

public class TJEditor extends TextEditor {

	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		IPreferenceManager pManager = Activator.getDefault().getPreferenceManager();
		setSourceViewerConfiguration(new TjSourceViewerConfiguration(pManager));
		pManager.getPreferences().addPreferenceChangeListener(new IPreferenceChangeListener() {
			@Override
			public void preferenceChange(PreferenceChangeEvent event) {
				Preferences node = event.getNode();
				String key = event.getKey();
			}
		});
	}
	
}
