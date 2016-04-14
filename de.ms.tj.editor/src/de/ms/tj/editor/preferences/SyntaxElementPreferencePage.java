package de.ms.tj.editor.preferences;

import java.util.HashMap;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.osgi.service.prefs.BackingStoreException;

import de.ms.tj.editor.TjDocumentSetupParticipant;
import de.ms.tj.editor.TjSourceViewerConfiguration;
import de.ms.tj.model.ISyntaxElement;
import de.ms.tj.model.SyntaxBrowser;
import de.ms.tj.model.SyntaxLabelProvider;
import de.ms.tj.model.SyntaxTreeContentProvider;

public class SyntaxElementPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private Button inheritButton;
	private ColorSelector fgSelector;
	private ColorSelector bgSelector;
	private Button boldButton;
	private Button italicButton;
	private Button underlineButton;
	private Button strikethroughButton;

	private PreferenceManager pManager;
	private TreeViewer treeViewer;
	
	private HashMap<ISyntaxElement, SyntaxElementPreference> preferences;
	private SourceViewer sourceViewer;

	public SyntaxElementPreferencePage() {
		this.pManager = new PreferenceManager();
		this.preferences = new HashMap<ISyntaxElement, SyntaxElementPreference>();
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	@Override
	protected Control createContents(Composite parent) {

		Composite mainComp = new Composite(parent, SWT.NONE);

		GridLayout mainLayout = new GridLayout(1, true);
		mainComp.setLayout(mainLayout);

		Composite controlComp = new Composite(mainComp, SWT.BORDER);
		controlComp.setLayoutData(new GridData(GridData.BEGINNING, GridData.FILL, true, true));
		controlComp.setLayout(new GridLayout(2, false));

		Label controlLabel = new Label(controlComp, SWT.NONE);
		controlLabel.setText("Select the element you would like to configure");
		controlLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 2, 1));

		this.treeViewer = createPreferenceTree(controlComp);
		this.treeViewer.getControl().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		Composite buttonComp = new Composite(controlComp, SWT.BORDER);
		buttonComp.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false));
		buttonComp.setLayout(new GridLayout(2, false));

		this.inheritButton = new Button(buttonComp, SWT.CHECK);
		this.inheritButton.setText("Inherit");
		this.inheritButton.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 2, 1));

		Label fgLabel = new Label(buttonComp, SWT.NONE);
		fgLabel.setText("Foreground Color");
		fgLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));

		this.fgSelector = new ColorSelector(buttonComp);
		this.fgSelector.getButton().setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));

		Label bgLabel = new Label(buttonComp, SWT.NONE);
		bgLabel.setText("Background Color");
		bgLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));

		this.bgSelector = new ColorSelector(buttonComp);
		this.bgSelector.getButton().setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));

		this.boldButton = new Button(buttonComp, SWT.CHECK);
		this.boldButton.setText("Bold");
		this.boldButton.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 2, 1));

		this.italicButton = new Button(buttonComp, SWT.CHECK);
		this.italicButton.setText("Italic");
		this.italicButton.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 2, 1));

		this.underlineButton = new Button(buttonComp, SWT.CHECK);
		this.underlineButton.setText("Underline");
		this.underlineButton.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 2, 1));

		this.strikethroughButton = new Button(buttonComp, SWT.CHECK);
		this.strikethroughButton.setText("Strikethrough");
		this.strikethroughButton.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 2, 1));

		this.sourceViewer = createSourceViewer(mainComp);
		GridData gData = new GridData(GridData.FILL_BOTH);
		gData.widthHint = 250;
		gData.heightHint = 250;
		this.sourceViewer.getControl().setLayoutData(gData);
		
		this.inheritButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setControlButtonsEnabled(!inheritButton.getSelection());
			}
		});
		SelectionListener sUpdater = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				syncToPreferences(getSelectedElement());
			}
		};
		IPropertyChangeListener pcListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				syncToPreferences(getSelectedElement());
			}
		};
		this.inheritButton.addSelectionListener(sUpdater);
		this.fgSelector.addListener(pcListener);
		this.bgSelector.addListener(pcListener);
		this.boldButton.addSelectionListener(sUpdater);
		this.italicButton.addSelectionListener(sUpdater);
		this.underlineButton.addSelectionListener(sUpdater);
		this.strikethroughButton.addSelectionListener(sUpdater);
		
		clearButtons();
		this.inheritButton.setEnabled(false);
		setControlButtonsEnabled(false);

		return mainComp;

	}

	@Override
	protected void performDefaults() {
		this.preferences.clear();
		setSelectedElement(((IStructuredSelection) this.treeViewer.getSelection()).getFirstElement()); 
		this.sourceViewer.refresh();
		super.performDefaults();
	}
	
	@Override
	public boolean performOk() {
		for (ISyntaxElement e : this.preferences.keySet()) {
			try {
				this.pManager.setSyntaxElementPreference(e, this.preferences.get(e));
			} catch (BackingStoreException exc) {
				setErrorMessage(exc.getMessage());
				return false;
			}
		}
		return super.performOk();
	}

	protected void setSelectedElement(Object element) {
		if (element instanceof ISyntaxElement) {
			this.inheritButton.setEnabled(true);
			syncFromPreferences((ISyntaxElement) element);
		}
		setControlButtonsEnabled(!this.inheritButton.getSelection());
	}

	protected ISyntaxElement getSelectedElement() {
		IStructuredSelection s = (IStructuredSelection) this.treeViewer.getSelection();
		Object e = s.getFirstElement();
		return e instanceof ISyntaxElement ? (ISyntaxElement) e : null;
	}

	private TreeViewer createPreferenceTree(Composite parent) {
		TreeViewer tViewer = new TreeViewer(parent, SWT.BORDER);
		tViewer.setContentProvider(new SyntaxTreeContentProvider());
		tViewer.setLabelProvider(new SyntaxLabelProvider());
		tViewer.setInput(SyntaxBrowser.getInstance().getSyntaxRoot());
		tViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				Object e = selection.getFirstElement();
				if (e != null) {
					boolean state = tViewer.getExpandedState(e);
					tViewer.setExpandedState(e, !state);
				}
			}
		});
		tViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				Object element = ((IStructuredSelection) event.getSelection()).getFirstElement();
				setSelectedElement(element);
			}
		});
		return tViewer;
	}
	
	protected SourceViewer createSourceViewer(Composite parent) {
		SourceViewer sViewer = new SourceViewer(parent, null, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sViewer.configure(new TjSourceViewerConfiguration(new IPreferenceManager() {
			@Override
			public SyntaxElementPreference getSyntaxElementPreference(ISyntaxElement e, boolean useInheritance) {
				return getPreferences(e);
			}
		}));
		String ls = System.getProperty("line.separator");
		Document doc = new Document(
				"/* A multi" + ls
				+ "   line comment" + ls
				+ "*/" + ls + ls
				+ "project test \"Just a test\" {" + ls
				+ "  # We have two scenarios." + ls
				+ "  scenario plan \"Plan\" {" + ls
				+ "    scenario delayed \"Delayed\"" + ls
				+ "  }" + ls
				+ "}" + ls + ls
				+ "resource jim \"Jim Kirk\" {" + ls
				+ "  efficiency 1.0" + ls
				+ "  vacation 1970-01-01 - 2000-01-01" + ls
				+ "}" + ls + ls
				+ "task t1 \"We have something to do\" {" + ls
				+ "  priority 100" + ls
				+ "  effort 10d" + ls
				+ "  allocate jim" + ls
				+ "}" + ls + ls
				+ "taskreport areport \"Show me details\" {" + ls
				+ "  header -8<-" + ls
				+ "    One Ring to rule them all, One Ring to find them," + ls
				+ "    One Ring to bring them all, and in the darkness bind them," + ls
				+ "  ->8<" + ls
				+ "}"
				);
		new TjDocumentSetupParticipant().setup(doc);
		sViewer.setDocument(doc);
		return sViewer;
	}

	private void clearButtons() {
		this.inheritButton.setSelection(false);
		this.fgSelector.setColorValue(this.fgSelector.getButton().getBackground().getRGB());
		this.bgSelector.setColorValue(this.bgSelector.getButton().getBackground().getRGB());
		this.boldButton.setSelection(false);
		this.italicButton.setSelection(false);
		this.underlineButton.setSelection(false);
		this.strikethroughButton.setSelection(false);
	}

	private void syncFromPreferences(ISyntaxElement e) {
		SyntaxElementPreference p = getPreferences(e);
		if (p != null) {
			this.inheritButton.setSelection(p.isInherit());
			this.fgSelector.setColorValue(p.getForeground() != null ? p.getForeground() : this.fgSelector.getButton().getBackground().getRGB());
			this.bgSelector.setColorValue(p.getBackground() != null ? p.getBackground() : this.bgSelector.getButton().getBackground().getRGB());
			this.boldButton.setSelection(p.isBold());
			this.italicButton.setSelection(p.isItalic());
			this.underlineButton.setSelection(p.isUnderline());
			this.strikethroughButton.setSelection(p.isStrikethrough());
		}
	}

	private void syncToPreferences(ISyntaxElement e) {
		if (e != null) {
			SyntaxElementPreference p = new SyntaxElementPreference();
			p.setInherit(this.inheritButton.getSelection());
			p.setForeground(this.fgSelector.getColorValue());
			p.setBackground(this.bgSelector.getColorValue());
			p.setBold(this.boldButton.getSelection());
			p.setItalic(this.italicButton.getSelection());
			p.setUnderline(this.underlineButton.getSelection());
			p.setStrikethrough(this.strikethroughButton.getSelection());
			this.preferences.put(e, p);
			this.sourceViewer.refresh();
		}
	}
	
	private SyntaxElementPreference getPreferences(ISyntaxElement e) {
		SyntaxElementPreference p = null;
		if (e != null) {
			p = this.preferences.get(e);
			if (p == null) {
				p = this.pManager.getSyntaxElementPreference(e, false);
				this.preferences.put(e, p);
			}
		}
		return p;
	}
	
	private void setControlButtonsEnabled(boolean b) {
		fgSelector.setEnabled(b);
		bgSelector.setEnabled(b);
		boldButton.setEnabled(b);
		italicButton.setEnabled(b);
		underlineButton.setEnabled(b);
		strikethroughButton.setEnabled(b);
	}

}
