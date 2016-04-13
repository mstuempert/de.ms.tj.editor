package de.ms.tj.editor;

import org.eclipse.ui.editors.text.TextEditor;

public class TJEditor extends TextEditor {

	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		setSourceViewerConfiguration(new TjSourceViewerConfiguration());
	}
	
}
