package de.ms.tj.editor.internal;

import java.util.HashMap;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;

import de.ms.tj.model.ISyntaxElement;
import de.ms.tj.model.ISyntaxElementLibrary;
import de.ms.tj.model.SyntaxBrowser;

public class DefaultPreferences {

	private static final SyntaxElementPreference DEFAULT_COMMENT_PREFERENCE
	= new SyntaxElementPreference(
			false,
			new RGB(0x00, 0xA0, 0x00),
			null,
			SWT.ITALIC);
	
	private static final SyntaxElementPreference DEFAULT_STRING_PREFERENCE
	= new SyntaxElementPreference(
			false,
			new RGB(0x00, 0xA0, 0xA0),
			null,
			SWT.NONE);
	
	private static final SyntaxElementPreference DEFAULT_COMMAND_PREFERENCE
	= new SyntaxElementPreference(
			false,
			new RGB(0x00, 0x00, 0xA0),
			null,
			SWT.BOLD);
	
	private HashMap<ISyntaxElement, SyntaxElementPreference> defaultSyntaxElementPreferences
	= new HashMap<ISyntaxElement, SyntaxElementPreference>();
	
	static DefaultPreferences INSTANCE = new DefaultPreferences();
	
	private DefaultPreferences() {
		initialize();
	}
	
	void resetSyntaxElementPreference(PreferenceManager m, ISyntaxElement e) {
		m.setSyntaxElementPreference(e, this.defaultSyntaxElementPreferences.get(e));
	}

	void initializePreferenceManager(PreferenceManager m) {
		for (Entry<ISyntaxElement, SyntaxElementPreference> entry : this.defaultSyntaxElementPreferences.entrySet()) {
			m.setSyntaxElementPreference(entry.getKey(), entry.getValue());
		}
	}
	
	private void initialize() {
		
		this.defaultSyntaxElementPreferences.put(
				SyntaxBrowser.INSTANCE.getElementById(ISyntaxElementLibrary.COMMENT),
				DEFAULT_COMMENT_PREFERENCE);
		
		this.defaultSyntaxElementPreferences.put(
				SyntaxBrowser.INSTANCE.getElementById(ISyntaxElementLibrary.STRING),
				DEFAULT_STRING_PREFERENCE);
		
		this.defaultSyntaxElementPreferences.put(
				SyntaxBrowser.INSTANCE.getElementById(ISyntaxElementLibrary.COMMAND),
				DEFAULT_COMMAND_PREFERENCE);

	}

}
