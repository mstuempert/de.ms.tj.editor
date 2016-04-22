package de.ms.tj.editor.internal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.osgi.service.prefs.BackingStoreException;

import de.ms.tj.editor.preferences.IPreferenceManager;
import de.ms.tj.editor.preferences.ISyntaxElementPreference;
import de.ms.tj.model.ISyntaxContainer;
import de.ms.tj.model.ISyntaxElement;
import de.ms.tj.model.Syntax;

class PreferenceManager implements IPreferenceManager {
	
	private HashMap<ISyntaxElement, SyntaxElementPreference> syntaxElementPreferences
	= new HashMap<ISyntaxElement, SyntaxElementPreference>();
	
	PreferenceManager() {
		initializeDefaults();
		loadPreferences();
	}

	@Override
	public ISyntaxElementPreference getSyntaxElementPreference(ISyntaxElement e, boolean useInheritance) {
		ISyntaxElementPreference result = this.syntaxElementPreferences.get(e);
		while (((result == null) || result.isInherit()) && useInheritance && (e.getParent() != null)) {
			e = e.getParent();
			result = this.syntaxElementPreferences.get(e);
		}
		return result == null ? new SyntaxElementPreference() : result;
	}
	
	public void loadSyntaxElementPreferences() {
		
		IEclipsePreferences node = getPreferenceNode(PREFERENCE_NODE_SYNTAX_ELEMENT);
		
		Queue<ISyntaxElement> elements = new LinkedList<ISyntaxElement>();
		elements.add(Syntax.BROWSER.getSyntaxRoot());
		
		while (!elements.isEmpty()) {
			
			ISyntaxElement element = elements.poll();
			
			if (element instanceof ISyntaxContainer) {
				for (ISyntaxElement e : ((ISyntaxContainer) element).getChildren()) {
					elements.add(e);
				}
			}
			
			String s = node.get(element.getId(), null);
			if (s != null) {
				SyntaxElementPreference preference = SyntaxElementPreference.fromString(s);
				if (preference != null) {
					this.syntaxElementPreferences.put(element, preference);
				}
			}
			
		}
		
	}
	
	public void storeSyntaxElementPreferences() throws BackingStoreException {
		
		IEclipsePreferences node = getPreferenceNode(PREFERENCE_NODE_SYNTAX_ELEMENT);
		
		for (Entry<ISyntaxElement, SyntaxElementPreference> e : this.syntaxElementPreferences.entrySet()) {
			node.put(e.getKey().getId(), e.getValue().toPreferenceString());
		}
		
		node.flush();
		
	}

	@Override
	public void resetSytaxElementPreferences() {
		DefaultPreferences.INSTANCE.initializePreferenceManager(this);
	}

	@Override
	public void resetSytaxElementPreference(ISyntaxElement e) {
		DefaultPreferences.INSTANCE.resetSyntaxElementPreference(this, e);
	}

	@Override
	public void setSyntaxElementPreference(ISyntaxElement e, ISyntaxElementPreference p) {
		this.syntaxElementPreferences.put(e, SyntaxElementPreference.fromSyntaxElementPreference(p));		
	}

	protected void initializeDefaults() {
		DefaultPreferences.INSTANCE.initializePreferenceManager(this);
	}

	protected void loadPreferences() {
		loadSyntaxElementPreferences();
	}
	
	protected IEclipsePreferences getPreferenceNode(String node) {
		return ConfigurationScope.INSTANCE.getNode(node);
	}

	@Override
	public void addChangeListener(String node, IPreferenceChangeListener l) {
		getPreferenceNode(node).addPreferenceChangeListener(l);
	}

	@Override
	public void removeChangeListener(String node, IPreferenceChangeListener l) {
		getPreferenceNode(node).removePreferenceChangeListener(l);
	}
	
}
