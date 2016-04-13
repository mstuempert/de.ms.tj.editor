package de.ms.tj.editor.preferences;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
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

import de.ms.tj.model.ISyntaxElement;
import de.ms.tj.model.SyntaxBrowser;
import de.ms.tj.model.SyntaxLabelProvider;
import de.ms.tj.model.SyntaxTreeContentProvider;

public class SyntaxHighlightingPage extends PreferencePage implements IWorkbenchPreferencePage {

	private Button enabledButton;
	private ColorSelector fgSelector;
	private ColorSelector bgSelector;
	private Button boldButton;
	private Button italicButton;
	private Button underlineButton;
	private Button strikethroughButton;

	private PreferenceManager pManager;
	private TreeViewer treeViewer;

	public SyntaxHighlightingPage() {
		this.pManager = new PreferenceManager();
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	@Override
	protected Control createContents(Composite parent) {

		Composite mainComp = new Composite(parent, SWT.NONE);

		GridLayout mainLayout = new GridLayout(1, true);
		mainComp.setLayout(mainLayout);

		Label mainLabel = new Label(mainComp, SWT.NONE);
		mainLabel.setText("Specify the color and style for the different elements of the TaskJuggler Syntax");

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

		this.enabledButton = new Button(buttonComp, SWT.CHECK);
		this.enabledButton.setText("Enabled");
		this.enabledButton.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 2, 1));

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

		this.treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				Object element = ((IStructuredSelection) event.getSelection()).getFirstElement();
				setSelectedElement(element);
			}
		});
		this.enabledButton.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				fgSelector.setEnabled(enabledButton.getSelection());
				bgSelector.setEnabled(enabledButton.getSelection());
				boldButton.setEnabled(enabledButton.getSelection());
				italicButton.setEnabled(enabledButton.getSelection());
				underlineButton.setEnabled(enabledButton.getSelection());
				strikethroughButton.setEnabled(enabledButton.getSelection());
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
		this.enabledButton.addSelectionListener(sUpdater);
		this.fgSelector.addListener(pcListener);
		this.bgSelector.addListener(pcListener);
		this.boldButton.addSelectionListener(sUpdater);
		this.italicButton.addSelectionListener(sUpdater);
		this.underlineButton.addSelectionListener(sUpdater);
		this.strikethroughButton.addSelectionListener(sUpdater);

		return mainComp;

	}

	protected void setSelectedElement(Object element) {
		if ((element == null) || !(element instanceof ISyntaxElement) || !((ISyntaxElement) element).isLeaf()) {
			clearButtons();
		} else {
			syncFromPreferences((ISyntaxElement) element);
		}
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
		return tViewer;
	}

	private void clearButtons() {
		this.enabledButton.setSelection(false);
		this.fgSelector.setColorValue(this.fgSelector.getButton().getBackground().getRGB());
		this.bgSelector.setColorValue(this.bgSelector.getButton().getBackground().getRGB());
		this.boldButton.setSelection(false);
		this.italicButton.setSelection(false);
		this.underlineButton.setSelection(false);
		this.strikethroughButton.setSelection(false);
	}

	private void syncFromPreferences(ISyntaxElement e) {
		if ((e != null) && (e.isLeaf())) {
			SyntaxElementPreference p = this.pManager.getSyntaxElementPreference(e);
			this.enabledButton.setSelection(p.isEnabled());
			this.fgSelector.setColorValue(p.getForeground() != null ? p.getForeground() : this.fgSelector.getButton().getBackground().getRGB());
			this.bgSelector.setColorValue(p.getBackground() != null ? p.getBackground() : this.bgSelector.getButton().getBackground().getRGB());
			this.boldButton.setSelection(p.isBold());
			this.italicButton.setSelection(p.isItalic());
			this.underlineButton.setSelection(p.isUnderline());
			this.strikethroughButton.setSelection(p.isStrikethrough());
		}
	}

	private void syncToPreferences(ISyntaxElement e) {
		if ((e != null) && (e.isLeaf())) {
			SyntaxElementPreference p = new SyntaxElementPreference();
			p.setEnabled(this.enabledButton.getSelection());
			p.setForeground(this.fgSelector.getColorValue());
			p.setBackground(this.bgSelector.getColorValue());
			p.setBold(this.boldButton.getSelection());
			p.setItalic(this.italicButton.getSelection());
			p.setUnderline(this.underlineButton.getSelection());
			p.setStrikethrough(this.strikethroughButton.getSelection());
			this.pManager.setSyntaxElementPreference(e, p);
		}
	}

}
