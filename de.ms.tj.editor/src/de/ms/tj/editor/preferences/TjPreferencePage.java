package de.ms.tj.editor.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class TjPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	public TjPreferencePage() {
	}

	public TjPreferencePage(String title) {
		super(title);
	}

	public TjPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite mainComp = new Composite(parent, SWT.NULL);
		Label label = new Label(mainComp, SWT.NULL);
		label.setText("TaskJuggler Preferences");
		return mainComp;
	}

}
