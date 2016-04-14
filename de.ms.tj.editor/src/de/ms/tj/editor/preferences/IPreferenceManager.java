package de.ms.tj.editor.preferences;

import de.ms.tj.model.ISyntaxElement;

public interface IPreferenceManager {

	SyntaxElementPreference getSyntaxElementPreference(ISyntaxElement e, boolean useInheritance);

}
