package de.ms.tj.editor;

import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.ui.editors.text.TextEditor;

import de.ms.tj.editor.internal.Activator;
import de.ms.tj.editor.preferences.IPreferenceManager;

public class TJEditor extends TextEditor {

	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		IPreferenceManager pManager = Activator.getDefault().getPreferenceManager();
		setSourceViewerConfiguration(new TjSourceViewerConfiguration(pManager));
		pManager.addChangeListener(IPreferenceManager.PREFERENCE_NODE_SYNTAX_ELEMENT, new IPreferenceChangeListener() {
			@Override
			public void preferenceChange(PreferenceChangeEvent event) {
				getSourceViewer().setDocument(getSourceViewer().getDocument());
			}
		});
	}
	
}
